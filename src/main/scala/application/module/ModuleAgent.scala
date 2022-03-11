package application.module

import akka.actor.SupervisorStrategy.{Decider, Restart, Resume, Stop}
import akka.actor.{ActorInitializationException, ActorKilledException, ActorRef, DeathPactException, OneForOneStrategy, Props, SupervisorStrategy}
import application.module.example.ExampleModuleHolder
import application.module.player.PlayerModuleHolder
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
  final case class ModuleAgentRef(agent:ActorRef)
  final case object RequestClientMessageHandler
  private[module] val modules = List[ModuleHolder](UserModuleHolder.getInstance(),ExampleModuleHolder.getInstance(), PlayerModuleHolder.getInstance())
}


/**
  * register modules with protocolID, and pass then to any connecting client in future
  *
  * Created by RXL on 2018/3/31.
  *
  */
class ModuleAgent extends LogActor {

  import ModuleAgent._

  var messageHandler: Map[Int, ActorRef] = Map() //must be immutable

  var dataAgent: Option[ActorRef] = None

  def initModules(dataAgent: ActorRef): Unit = {
    this.dataAgent = Option(dataAgent)
    messageHandler.foreach(context stop _._2)
    val v = for {
      m <- ModuleAgent.modules
      actor = context.actorOf(m.props)
      e = actor ! mobius.modular.module.Module.InitModule(dataAgent)
      pid <- m.Protocols
    } yield (pid, actor)


    messageHandler = v.toMap
    context.children.foreach(c => log.debug(s"children~~~~~~~~:${c.path}"))
    messageHandler.foreach(p => log.debug(s"protocols:${p._1} dealer:${p._2}"))
  }

  override def receive: Receive = {
    case Module.InitModule(d) => initModules(d)
    case RequestClientMessageHandler => sender() ! ClientMessageHandlers(messageHandler)
  }


  final val decider: Decider = {
    case _: ActorInitializationException => Stop
    case _: ActorKilledException => Stop
    case _: DeathPactException => Stop
    case _: NullPointerException => Resume
    case _: Exception => Restart
  }

  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy()(decider)
}
