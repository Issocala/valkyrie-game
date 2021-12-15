package application.data

import akka.actor.{ActorRef, Props}
import application.data.api._
import mobius.db.message._


/**
  * Created by RXL on 2018/3/28.
  */
object UserData extends DataSet {


  override def props(dbAgent: ActorRef): Props = {
    Props(classOf[UserData], dbAgent)
  }

  type UserRow = Tables#UserRow

  final case class Query(userId: Long, info: OperationInfo) extends DataOperation

  final case class QueryByAccount(account: String, info:OperationInfo) extends DataOperation

  final case class Result(q: Query, data: Option[UserRow]) extends DataOperationResult

  final case class AccountResult(q: QueryByAccount, data: Option[UserRow]) extends DataOperationResult

  final case class QueryList(userIds: List[Long]) extends DataOperation

  final case class ListResult(data: Seq[UserRow]) extends DataOperationResult

  final case class Add(data: UserRow, source: ActorRef, client: ActorRef) extends DBOperation

  final case class AddResult(success: Boolean, add: Add) extends DBResponse

  final case class Update(data: UserRow, source: ActorRef, client: ActorRef) extends DBOperation

  final case class UpdateResult(success: Boolean, update: Update) extends DBResponse

  private final case class QueryAll(source: ActorRef) extends DBOperation

  private[data] final case class SeqResult(result: Seq[UserRow], sender: ActorRef, operation: DBOperation) extends DBActionResult

  private[data] final case class SeqResultContainer(override val getResult: Seq[UserRow] => SeqResult) extends ResultContainer[Seq[UserRow]]

  private[data] final case class IntResult(result: Int, sender: ActorRef, operation: DBOperation) extends DBActionResult

  private[data] final case class SideEffectResult(result: Unit, sender: ActorRef, operation: DBOperation) extends DBActionResult

  private[data] final case class SideEffectResultContainer(override val getResult: Unit => SideEffectResult) extends ResultContainer[Unit]

  private[data] final case class RowResult(result: UserRow, sender: ActorRef, operation: DBOperation) extends DBActionResult

  //for updatable

  override def getInstance(): UserData.this.type = this
}

sealed class UserData(val dbAgent: ActorRef) extends Model {

  import UserData._

  val action = TableQuery[Tables.User]

  var cache: Map[Long,UserRow] = Map.empty[Long,UserRow]//mutable.Map.empty[DataSet, ActorRef]

  def AddFinish(result: AddResult): Unit = {
    log.debug(s"add finish ${result}")
    result.add.source ! result
  }

  def dealResult(st: DBActionResult): Unit = {
    st match {
      case IntResult(r, s, o) => log.debug(s" IntResult  $r")
      case SeqResult(r, s, o) => log.debug(s" SeqResult  $r"); cache = r.toList.map(e=>e.id -> e).toMap
      //      case SideEffectResult(r,s,o) => o match {
      //        case a @ Add(d,s) => AddFinish(AddResult(true,a))
      //        case Update(d,s) =>
      //      }
      case RowResult(r, s, o) => o match {
        case a@Add(d, s, c) => AddFinish(AddResult(true, a)); cache += (r.id -> r)
        case Update(d, s, c) =>
      }
      case x => log.debug(s"result without dealer : ${x}")
    }
  }

  def onRequest(request: DBOperation): Any = {
    request match {
      case a: Add => preAdd(a)
      //no QueryAll here, QueryAll is private
      //      case a @ Add(d,r) => dbAgent ! DBAction((action returning action.map(_.id) into ((user,id)=> user.copy(id=id))).insertOrUpdate(d) ,GenericResultContainer[UserRow](RowResult(_,self,a)),self) // use side effect or (action += d).result directly
      case u@Update(d, r, c) => GenericResultContainer[UserRow](RowResult(_, self, u))
    }

    def preAdd(a: Add): Unit = {
      if (cache.exists(p => p._2.id == a.data.id)) {
        a.source ! AddResult(success = false, a)
      } else {
        dbAgent ! DBAction((action returning action.map(_.id) into ((user, id) => user.copy(id = id))) += a.data, GenericResultContainer[UserRow](RowResult(_, self, a)), self) // use side effect or (action += d).result directly
      }
    }

  }

  override def receive: Receive = {
    case d:DataOperation => d match {
      case a:QueryByAccount => sender() ! AccountResult(a,cache.values.find( _.id == 1))
    }
    case DataAgent.Init => dbAgent ! DBAction(action.result, SeqResultContainer(SeqResult(_, self, QueryAll(self))), self)
    case request: DBOperation => onRequest(request)
    case r: DBActionResult => dealResult(r)
    case f@DBActionFailed(a, t, o) => o match {
      case a@Add(r, s, c) => s ! AddResult(false, a); log.error(s"db op failed ${t}")
      case x=>log.error(s"db action failed ${t} \n operation: $x")
    }
    case x => log.error("UserData unhandled:" + x)
  }

  def fk(): Unit = {
    DBIO.seq(action.result)
  }

}
