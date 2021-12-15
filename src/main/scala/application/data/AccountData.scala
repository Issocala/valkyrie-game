package application.data

import akka.actor.{ActorRef, Props}
import mobius.db.message._
import application.data.api._

/**
  * Created by RXL on 2018/6/9.
  */
object AccountData extends DataSet {
  override def props(dbAgent: ActorRef) = {
    Props(classOf[AccountData], dbAgent)
  }

  type AccountRow = Tables#AccountRow

  final  case class Query(userId: Long, info: OperationInfo, source: ActorRef) extends DBOperation

  final case class Result(q: Query, data: Seq[AccountRow]) extends DBResponse

  final case class Add(data: AccountRow, source: ActorRef, client: ActorRef) extends DBOperation

  final case class Update(data: AccountRow, source: ActorRef, client: ActorRef) extends DBOperation

  final case class Delete(id: Long, user: Long, source: ActorRef, client: ActorRef) extends DBOperation

  final case class GeneralOperationResult(success: Boolean, operation: DBOperation) extends DBResponse

  private[data] final case class RowResult(result: AccountRow, sender: ActorRef, operation: DBOperation) extends DBActionResult

  private[data] final case class SeqResult(result: Seq[AccountRow], sender: ActorRef, operation: DBOperation) extends DBActionResult

  private[data] final case class RowResultLift(result: Option[AccountRow], sender: ActorRef, operation: DBOperation) extends DBActionResult

  private[data] final case class IntResult(result: Int, sender: ActorRef, operation: DBOperation) extends DBActionResult

  override def getInstance(): AccountData.this.type = this
}

sealed class AccountData(val dbAgent: ActorRef) extends Model {

  import AccountData._

  var cache: scala.collection.mutable.Map[Long, Seq[AccountRow]] = scala.collection.mutable.Map.empty[Long, Seq[AccountRow]]
  val action = TableQuery[Tables.Account]

  def onDBRequest(request: DBOperation): Unit = {
    request match {
      case a: Add => dbAgent ! DBAction((action returning action.map(_.id) into ((account, id) => account.copy(id = id))) += a.data, GenericResultContainer[AccountRow](RowResult(_, self, a)), self)
      case u: Update => dbAgent ! DBAction((action returning action.map(_.id) into ((account, id) => account.copy(id = id))).insertOrUpdate(u.data), GenericResultContainer[Option[AccountRow]](RowResultLift(_, self, u)), self)
      case d: Delete => dbAgent ! DBAction(action.filter(p => p.id === d.id && p.user === d.user).delete, GenericResultContainer[Int](IntResult(_, self, d)), self)
      case q: Query => if (cache.contains(q.userId)) {
        q.source ! Result(q, cache(q.userId))
      } else {
        dbAgent ! DBAction(action.filter(p => p.user === q.userId).result, GenericResultContainer[Seq[AccountRow]](SeqResult(_, self, q)), self)
      }
    }
  }

  def add(a: Add, r: RowResult): Unit = {
    a.source ! GeneralOperationResult(success = true, a.copy(data = r.result))
    cache(r.result.user) :+= r.result
  }

  def delete(d: Delete, r: IntResult): Unit = {
    d.source ! GeneralOperationResult(success = true, d)
    cache(d.user) = cache(d.user).filterNot(p => p.id == d.id)
  }

  def query(q: Query, r: SeqResult): Unit = {
    q.source ! Result(q, r.result)
    cache(q.userId) = r.result
  }

  def update(u: Update, r: RowResultLift): Unit = {
    u.source ! GeneralOperationResult(success = true, u)
    r.result match {
//      case Some(m) => cache(m.user) = cache(m.user).map(v => if (v.id == m.id) m else v)
      case None => cache(u.data.user) = cache(u.data.user).map(v => if (v.id == u.data.id) u.data else v)
      case x => log.error(s"update result not defined: $x")
    }
  }

  def dealResult(r: DBActionResult): Unit = {
    //    log.debug(s"${this.getClass} unhandled :$r")
    r match {
      case r: RowResult => r.operation match {
        case a: Add => add(a, r)
        case x => log.error(s"RowResult not match:$x")
      }
      case r: SeqResult => r.operation match {
        case q: Query => query(q, r)
        case x => log.error(s"SeqResult not match:$x")
      }
      case r: RowResultLift => r.operation match {
        case u: Update => update(u, r)
        case x => log.error(s"RowResultLift not match:$x")
      }
      case r:IntResult => r.operation match {
        case d: Delete => delete(d, r)
      }
      case x => log.error(s"dealResult error:$x")
    }
  }

  def onDataRequest(request: DataOperation): Unit = {
    log.debug(s"unhandled data request:$request")
  }

  override def receive: Receive = {
    case request: DBOperation => onDBRequest(request)
    case r: DBActionResult => dealResult(r)
    case f@DBActionFailed(a, t, o) => o match {
      case u: DBOperation => u.source ! GeneralOperationResult(false, u)
      case _ => log.error(s"${this.getClass} error not match:$f")
    }
    case DataAgent.Init =>
    case request: DataOperation => onDataRequest(request)
    case x => log.error(s"${this.getClass} msg not defined:$x")
  }
}
