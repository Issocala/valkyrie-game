package application.client

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef, Props}
import akka.io.Tcp.{Bind, Bound, Connected, Register}
import akka.io.{IO, Tcp}
import application.client.ClientAgent.{ActiveClient, RequestLogin}
import application.module.ModuleAgent
import mobius.core.ActorExtension.LogActor
import util.Conf

/**
  * Created by RXL on 2016/9/28.
  */
private[application] object ClientAgent {
  private[application] def props(): Props = Props(classOf[ClientAgent])

  final case object Init

  final case class ActiveClient(ip: String, port: Int, client: ActorRef, userId: Int)

  final case class RequestLogin(client: ActiveClient)

  final case class ClientPeerClosed(client: ActiveClient)

  val ModuleAgentRef: ModuleAgent.ModuleAgentRef.type = application.module.ModuleAgent.ModuleAgentRef
  val RequestClientMessageHandler: ModuleAgent.RequestClientMessageHandler.type = application.module.ModuleAgent.RequestClientMessageHandler
  val ClientMessageHandlers: ModuleAgent.ClientMessageHandlers.type =application.module.ModuleAgent.ClientMessageHandlers

}


class ClientAgent extends Actor with LogActor {

  import scala.collection.mutable.HashMap

  private val clients: HashMap[Int, ActiveClient] = HashMap[Int, ActiveClient]()
  var moduleAgent: ActorRef = _

  /**
  * 建立连接
  * 刚建立连接时，还没登录，不能确定是否重登,不加入客户端列表
  * */
  def onClientConnected(connection: ActorRef, remote: InetSocketAddress): Unit = {
    val rmt = remote.toString.replace("/", "")
    val client = context.actorOf(Client.props(connection), rmt)

//    log.debug(s"clients : ${context.children.toList}")
    client ! ClientAgent.ModuleAgentRef(moduleAgent)
    connection ! Register(client)
  }

  def onClientLogin(client: ActiveClient): Unit = {
    if (clients.contains(client.userId)) {
      val old = clients(client.userId)
      //      old !
    } else {

    }
  }


  override def receive: Receive = {
    case ClientAgent.Init => IO(Tcp)(context.system) ! Bind(self, new InetSocketAddress(Conf.tcpHost, Conf.tcpPort))
    case b@Bound(localAddress) => println("bound to:" + localAddress)
    case c@Connected(remote, local) => onClientConnected(sender(), remote)
    case RequestLogin(client) => onClientLogin(client)
    case ClientAgent.ModuleAgentRef(a) => moduleAgent = a
    case _ =>
  }
}
