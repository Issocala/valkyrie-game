package application

import akka.actor.{Actor, ActorRef, Props}
import application.ServerRoot.MainMessage
import application.client.ClientAgent
import application.condition.util.ConditionItemMgr
import application.data.{ActorDataDispatcher, DataAgent}
import application.db.DataBaseAgent
import application.module.ModuleAgent
import application.module.player.fight.attribute.provider.AttributeRegister
import application.module.scene.fight.buff.function.FightBuffFunctionContainer
import application.module.scene.fight.skill.base.function.FightSkillFunctionContainer
import application.util.StaticConfigLoad
import mobius.core.ActorExtension.LogActor
import mobius.db.message.{DbInit, Test}
import mobius.modular.module.Module

/**
 * Created by RXL on 2015/8/22.
 */
object ServerRoot {
  def props: Props = {
    Props(classOf[ServerRoot])
  }

  object MainMessage extends Enumeration {
    type MainMessage = Value
    val Start, Stop = Value
  }
}

class ServerRoot extends Actor with LogActor {

  var dbAgent: Option[ActorRef] = None
  var clientAgent: Option[ActorRef] = None
  var dataAgent: Option[ActorRef] = None
  var moduleAgent: Option[ActorRef] = None

  def onServerStart(): Unit = {
    //策划配置数据加载
    loadData()

    dbAgent = start(dbAgent, "dbAgent", DataBaseAgent.props(), DbInit)
    dbAgent.get ! Test
    dataAgent = start(dataAgent, "dataAgent", ActorDataDispatcher.create(), DataAgent.Init)

    moduleAgent = start(moduleAgent, "moduleAgent", ModuleAgent.props(), Module.InitModule(dataAgent.get))

    clientAgent = start(clientAgent, "clientAgent", ClientAgent.props(), ClientAgent.Init)
    clientAgent.get ! ClientAgent.ModuleAgentRef(moduleAgent.get)
  }

  def loadData(): Unit = {
    //策划配置数据加载
    StaticConfigLoad.init()
    ConditionItemMgr.getInstance().register()
    AttributeRegister.register()
    FightSkillFunctionContainer.registerSkillAndPassive()
    FightBuffFunctionContainer.register()
  }

  override def receive: Receive = {
    case MainMessage.Start => onServerStart()
  }

  def start(agent: Option[ActorRef], name: String, props: Props, message: Any): Option[ActorRef] = {
    val newAgent = agent match {
      case Some(x) => log.error(s"$name exist")
        agent
      case None => Option(context.actorOf(props, name))
    }
    newAgent.get ! message
    newAgent
  }

}


