package application.data


import akka.actor.{ActorRef, Props}
import application.data.DataAgent.{DataResult, RequestData}
import mobius.core.ActorExtension.LogActor

import scala.collection.mutable

/**
  * Created by RXL on 2018/3/27.
  */
private[application] object DataAgent {

  final case object Init

  final case class RequestData[T <: DataSet](t: T)

  final case class DataResult[T <: DataSet](ds: T, r: Option[ActorRef])

  private val data = List[DataSet](UserData,AccountData)

  def props(dbAgent: ActorRef): Props = Props(classOf[DataAgent], dbAgent)
}

/**
  * only proxy to model's ActorRef
  */
private[data] sealed class DataAgent(val db: ActorRef) extends LogActor {

  private val dataContainer:mutable.Map[DataSet, ActorRef] = mutable.Map.empty[DataSet, ActorRef]

  def InitModel(): Unit = {
    DataAgent.data.foreach { d => dataContainer += (d -> context.actorOf(d.props(db))); dataContainer(d) ! DataAgent.Init }
  }

  override def receive: Receive = {
    case DataAgent.Init => InitModel()
    case RequestData(d) => sender() ! DataResult(d, dataContainer.get(d))
    case _ =>
  }
}
