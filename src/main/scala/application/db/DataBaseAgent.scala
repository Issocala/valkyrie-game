package application.db

import akka.actor.{Actor, Props}
import mobius.db.api._
import mobius.db.message._
import mobius.db.{DatabaseOperator, TableQuery}
import template.db.Tables.User
import util.Conf

/**
  * Created by RXL on 2016/9/27.
  */
object DataBaseAgent{
  def props():Props=Props(classOf[DataBaseAgent])
}


private [db] class DataBaseAgent extends Actor with DatabaseOperator{

  override lazy val gameDBName: String = Conf.Database.gameDbName

  def queryTest(): Unit = {
    log.debug("db test")
    val user=TableQuery[User]

    val q = user
    val action = q.result
    println(User.shaped)

    self ! action

  }

  def init(): Unit = {
    gameDB = createConnection
    log.debug(s"db connected to actor: ${gameDB.get}")
  }

  override def receive: Receive = {
    case DbInit => init()
    case Test => //queryTest
    case query @ DBAction(a,b,c) => gameDB.get ! query;
    case x => log.debug(s"Undefined db message from sender _:${sender()} message: $x")
  }


}


