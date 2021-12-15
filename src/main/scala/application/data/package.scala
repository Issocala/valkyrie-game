package application

import akka.actor.{ActorRef, Props}
import mobius.db.message.{DBActionResult, ResultContainer}
import mobius.db.{Effect, NoStream}
import mobius.core.ActorExtension.LogActor

/**
  * Created by RXL on 2018/3/28.
  */
package object data {



  trait DataSet {
    def props(dbAgent: ActorRef): Props
    def getInstance() : this.type
  }


  val TableQuery = slick.lifted.TableQuery
  val api = slick.jdbc.MySQLProfile.api
  val Tables = template.db.Tables
  type Tables = template.db.Tables.type

  trait Model extends LogActor {
    type DBIOAction[+R, +S <: NoStream, -E <: Effect] = mobius.db.DBIOAction[R, S, E]
    val DBIOAction = slick.dbio.DBIOAction
  }

  /**
    * operation on cache
    */
  trait DataOperation

  /**
    * custom data carried by DataOperation
    */
  trait OperationInfo
  object OperationInfo extends OperationInfo

  /**
    * result of operation on cache
    */
  trait DataOperationResult

  private[data] case class GenericResultContainer[T](override val getResult: T => DBActionResult) extends ResultContainer[T]

}
