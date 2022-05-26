package application.module

import akka.actor.SupervisorStrategy.{Escalate, Resume}
import akka.actor.{ActorRef, OneForOneStrategy, Props, SupervisorStrategy}
import application.module.common.CommonModuleHolder
import application.module.example.ExampleModuleHolder
import application.module.gm.GmModuleHolder
import application.module.player.PlayerModuleHolder
import application.module.scene.SceneModuleHolder
import application.module.user.UserModuleHolder
import mobius.core.ActorExtension.LogActor
import mobius.modular.module._

/**
 * ActorRef Factory for ModuleAgent
 *
 * Created by RXL on 2018/3/31.
 */
object ModuleAgent {
  private[application] def props(): Props = Props(classOf[ModuleAgent])

  final case class ClientMessageHandlers(h: Map[Int, ActorRef])

  final case class ModuleAgentRef(agent: ActorRef)

  final case object RequestClientMessageHandler

  private[module] val modules = List[ModuleHolder](UserModuleHolder.getInstance(), ExampleModuleHolder.getInstance(),
    PlayerModuleHolder.getInstance(), SceneModuleHolder.getInstance(), CommonModuleHolder.getInstance(), GmModuleHolder.getInstance())
}


/**
 * register modules with protocolID, and pass then to any connecting client in future
 *
 * Created by RXL on 2018/3/31.
 *
 */
class ModuleAgent extends LogActor {

  import ModuleAgent._

  import scala.concurrent.duration._
  import scala.language.postfixOps

  var messageHandler: Map[Int, ActorRef] = Map() //must be immutable

  var dataAgent: ActorRef = _

  def initModules(dataAgent: ActorRef): Unit = {
    messageHandler.foreach(context stop _._2)
    val v = for {
      m <- ModuleAgent.modules
      actor = context.actorOf(m.props)
      e = actor ! mobius.modular.module.Module.InitModule(dataAgent)
      pid <- m.Protocols
    } yield (pid, actor)
    this.dataAgent = dataAgent

    messageHandler = v.toMap
    context.children.foreach(c => log.debug(s"children~~~~~~~~:${c.path}"))
    messageHandler.foreach(p => log.debug(s"protocols:${p._1} dealer:${p._2}"))
  }

  //temporary SupervisorStrategy
  override val supervisorStrategy: SupervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: ArithmeticException => Resume
      case t => log.debug(s"exception caught on sender: $sender()")
        sender() ! initModules(dataAgent)
        super.supervisorStrategy.decider.applyOrElse(t, (_: Any) => Escalate)
    }


  override def receive: Receive = {
    case Module.InitModule(d) => initModules(d)
    case RequestClientMessageHandler => sender() ! ClientMessageHandlers(messageHandler)
  }
}
