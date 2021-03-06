package application.client

import akka.actor.{ActorRef, Cancellable, Props, ReceiveTimeout, Stash}
import akka.io.Tcp._
import akka.util.ByteString
import application.client.Client._
import application.module.common.CommonProtocols
import application.module.player.PlayerProtocols
import application.util.ApplicationErrorCode
import mobius.core.ActorExtension.LogActor
import mobius.modular.client.Client.ReceivedFromClient
import mobius.net.FrameExtractor
import protocol.common.SC10080
import protocol.p1.sc3
import scalapb.GeneratedMessage

import java.nio.ByteOrder
import scala.concurrent.duration._
import scala.language.postfixOps

private[application] object Client {
  private[application] def props(connection: ActorRef): Props = Props(classOf[Client], connection)

  final case object Init

  final case object ReLogin

  final case class SendToClient(protocolId: Int, message: GeneratedMessage)

  final case class SendToClientJ(protocolId: Int, message: com.google.protobuf.GeneratedMessageV3)

  final case object ClientClose

  final case object Tick

  final case class LoginSuccess(id: Long)

  final case class UpdateMessage(messageSet: Set[Integer], playerActor: ActorRef)

  val UnknownUser: Long = -12
}

/**
 * Created by RXL on 2016/10/13.
 */
private[client] class Client(val connection: ActorRef) extends LogActor with Stash with FrameExtractor {

  import ClientAgent._
  import context._

  override implicit val byteOrder: ByteOrder = java.nio.ByteOrder.LITTLE_ENDIAN
  var buffer: ByteString = ByteString()
  private val bytesBuilder = ByteString.newBuilder
  private var userID: Long = Client.UnknownUser
  var messageHandler: Map[Int, ActorRef] = Map()
  var fixedTick: Long = 11000
  var tickFinalTimestamp: Long = System.currentTimeMillis()
  val c: Cancellable = context.system.scheduler.scheduleWithFixedDelay(java.time.Duration.ofMillis(this.fixedTick),
    java.time.Duration.ofMillis(this.fixedTick), self, Tick, context.dispatcher, self)


  def close(): Unit = {
    log.debug(s"try close client $userID ")
    //todo 1. save player info and other things, 2. check whether children also terminated or not
    val protocolId: Int = PlayerProtocols.LOGOUT
    val handler = messageHandler.get(protocolId)
    handler match {
      case Some(h) => h ! ReceivedFromClient(self, userID, protocolId, null)
      case None => log.error(s"protocol handler not found, id:${protocolId}")
    }
    c.cancel()
    connection ! Write(protocolToByteString(SendToClient(CommonProtocols.APPLICATION_ERROR,
      SC10080(ApplicationErrorCode.CLIENT_DISCONNECT))))
    context stop self
  }

  //  ReceivedFromClient
  def connectionReplaced(): Unit = {
    log.debug(s"connectionReplaced $userID ")
    //todo send msg to client
    close()
  }

  def resume(m: Map[Int, ActorRef]): Unit = {
    messageHandler = m
    become(receive)
    unstashAll()
    context.setReceiveTimeout(Duration.Undefined)
  }

  def timeout(): Unit = {
    become(receive)
    unstashAll()
    context.setReceiveTimeout(Duration.Undefined)
    self ! SendToClient(3, sc3(1))
    log.error(s"get message handler timeout,from $this")
  }


  def waitHandler: Receive = {
    case ClientMessageHandlers(m) => resume(m)
    case ReceiveTimeout => timeout()
    case _ => stash()
  }

  def pause(a: ActorRef): Unit = {
    a ! RequestClientMessageHandler
    context.setReceiveTimeout(20 seconds)
    become(waitHandler)
  }

  def peerClose(): Unit = {
    log.debug(s"peer closed by client ${}")

    close()
  }

  override def receive: Receive = {
    case Received(data) => onReceivedFromSocket(data)
    case p2client: SendToClient => connection ! Write(protocolToByteString(p2client))
    case p2clientJ: SendToClientJ => connection ! Write(protocolToByteString(p2clientJ))
      log.debug(s"send protocolId :${p2clientJ.protocolId}")
    case LoginSuccess(id) => userID = id
    case CommandFailed(b: Bind) => close()
      log.error(s"CommandFailed: $b")
    case PeerClosed => peerClose()
    case ClientClose => close()
    case ReLogin => connectionReplaced()
    case ModuleAgentRef(a) => pause(a)
    case Tick => tick()
    case UpdateMessage(messageSet, playerActor) => updateMessage(messageSet, playerActor)
    case x => log.debug("unhandled client msg:" + x)
  }

  def updateMessage(messageSet: Set[Integer], playerActor: ActorRef): Unit = {
    messageSet.foreach((id: Integer) => messageHandler += (id.toInt -> playerActor))

  }

  private def tick(): Unit = {
    val time = System.currentTimeMillis() - tickFinalTimestamp;
    if (time > fixedTick) {
//      close()
    }
  }

  private def onReceivedFromSocket(data: ByteString): Unit = {
    val bs = buffer ++ data
    //    log.debug("received:"+data)
    val (nb, frames) = extractFrame(bs, Nil)
    buffer = if (nb.isEmpty) ByteString() else nb.get
    frames.reverse.foreach(dealClientMessage)
  }

  private def dealClientMessage(frame: ByteString): Unit = {
    //    log.debug(s"frame length:${frame.length} :$frame")
    val protocolId = frame.iterator.getShort
    log.debug(s"receive protocolId :$protocolId")

    tickFinalTimestamp = System.currentTimeMillis()

    //TODO ?????????????????????????????????????????????????????????????????????????????????????????????
    val handler = messageHandler.get(protocolId)
    handler match {
      case Some(h) => h ! ReceivedFromClient(self, userID, protocolId, (frame drop 2).toArray)
      case None => log.error(s"protocol handler not found, id:${protocolId}")
    }
  }


  final def protocolToByteString(p: SendToClient): ByteString = {
    val bs = bytesBuilder.putShort(p.message.serializedSize + 2).putShort(p.protocolId).putBytes(p.message.toByteArray).result()
    bytesBuilder.clear()
    bs
  }

  final def protocolToByteString(p: SendToClientJ): ByteString = {
    val bs = bytesBuilder.putShort(p.message.getSerializedSize + 2).putShort(p.protocolId).putBytes(p.message.toByteArray).result()
    bytesBuilder.clear()
    bs
  }


}
