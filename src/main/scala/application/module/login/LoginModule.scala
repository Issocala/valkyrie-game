package application.module.login

import akka.actor.{ActorRef, Props}
import application.client.Client.{LoginSuccess, SendToClient}
import application.data.DataAgent.{DataResult, RequestData}
import application.data.UserData.AccountResult
import application.data.{AccountData, OperationInfo, UserData}
import mobius.core.digest.Codec
import mobius.modular.module.Module.ProtocolHandler
import mobius.modular.module.{Module, ModuleHolder}
import template.db.Tables.UserRow


/**
  * Created by RXL on 2018/3/31.
  */
object LoginModule extends ModuleHolder {

  override val Protocols = List(1, 2)

  override def props = Props(classOf[LoginModule])

  private case class Login(client:ActorRef, password:String) extends OperationInfo
}

class LoginModule extends Module with ProtocolHandler{

  import LoginModule._
  import protocol.p1._

//  var dataAgent: ActorRef = _
  var userData: ActorRef = _
  var accountData: ActorRef = _

  @inline
  def codecPassword(p:String): String = Codec.md5(p)

  def register(r: ReceivedFromClient): Unit = {
    val msg = cs1.parseFrom(r.message)
    userData ! UserData.Add(UserRow(1, msg.name), self, r.client)
  }

  def login(r: ReceivedFromClient): Unit = {
    val account = cs2.parseFrom(r.message).account
    userData ! UserData.QueryByAccount(account.account, Login(r.client, codecPassword(account.password)))
  }

  def deal(r: ReceivedFromClient): Unit = {
    r.protoID match {
      case 1 => register(r)
      case 2 => login(r)
    }
  }

  def addUser(a: UserData.AddResult): Unit = {
    a.add.client ! SendToClient(1, sc1(a.success))
  }


  def test(): Unit = {
    case class CC(i:Int)
    val c =CC(10)
    val d =CC(10)
    val e =CC(11)
    c.i match {
      case e.i => println("e---")
      case d.i => println("d---")
      case 11 => println("11---")
      case x => println("x---")
    }
  }

  def dealAccount(r: AccountResult): Unit = {
    r.q.info match {
      case l : Login => val success:Boolean = r.data match {
        case Some(row)=> row match {
          case _ => true
        }
        case None =>false
      }

        l.client ! SendToClient(2,sc2(success,r.data match {
          case None => -1
          case Some(x1) => x1.id
        }))
        if(success) l.client ! LoginSuccess(r.data.get.id)
      case x => log.info(s"deal account: not defined $x")
    }
  }




  /*
  * for test only
  * */
  def testAccountData(): Unit = {
//    accountData ! AccountData.Add(AccountRow(1,1,account = Some("xxxx"),timestamp = DbUtil.timesTamp),self,self)
//    accountData ! AccountData.Add(AccountRow(1,1,account = Some("xxxx"),timestamp=null),self,self)
//    accountData ! AccountData.Update(AccountRow(4,5,account = Some("xfxxx"),timestamp = null),self,self)
//    accountData ! AccountData.Query(0,Login(self,"x"),self)
//    accountData ! AccountData.Update(AccountRow(4,5,account = "xfffxxx",timestamp = DbUtil.currentTimeMillis),self,self)
  }

  override def receiveFirst: Receive = {
    case r: ReceivedFromClient => deal(r)
    case r:AccountResult => dealAccount(r)
    case DataResult(ds, a) => ds match {
      case UserData => userData = a.get//; userData ! UserData.Add(UserRow(1, Some("hudia"), account = Some("xfxxx"), password = Some(codecPassword("1234x"))), self, self)
      case AccountData => accountData = a.get//;testAccountData()
    }
    case a: UserData.AddResult => addUser(a)
//    case x => log.debug(s"unhandled login message:$x")
  }

  override def initData(): Unit = {
    dataAgent ! RequestData(UserData) //log.debug("login module");
    dataAgent ! RequestData(AccountData)
  }


}
