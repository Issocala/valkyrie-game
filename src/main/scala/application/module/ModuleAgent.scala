package application.module

import akka.actor.{ActorRef, Props}
import application.module.example.ExampleModuleHolder
import application.module.login.LoginModule
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
  private[module] val modules = List[ModuleHolder](LoginModule,ExampleModuleHolder.getInstance())
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

  def initModules(dataAgent: ActorRef): Unit = {
    messageHandler.foreach(context stop _._2)
    val v = for {
      m <- ModuleAgent.modules
      actor = context.actorOf(m.props)
      e = actor ! mobius.modular.module.Module.InitModule(dataAgent)
      pid <- m.Protocols
    } yield (pid, actor)


    messageHandler = v.toMap
    context.children.foreach(c => log.debug(s"children~~~~~~~~:${c.path}"))
    messageHandler.foreach(p=> log.debug(s"protocols:${p._1} dealer:${p._2}"))
  }

  override def receive: Receive = {
    case Module.InitModule(d) => initModules(d)
    case RequestClientMessageHandler => sender() ! ClientMessageHandlers(messageHandler)
  }
}
