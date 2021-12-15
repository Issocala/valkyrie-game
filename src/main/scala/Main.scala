import akka.actor.ActorSystem
import application.ServerRoot
import application.ServerRoot.MainMessage



/**
  * Created by RXL on 2015/8/22.
  */
object Main {
  def startSystem():ActorSystem = {
    val system=ActorSystem("mobiusServerSystem")
    val rootRef=system.actorOf(ServerRoot.props,"serverRoot")
    rootRef ! MainMessage.Start
    system
  }

  def onShutdown(actorSystem:ActorSystem): Unit ={
    actorSystem.terminate()
    println("system shutdown")
  }

  def main(args: Array[String]): Unit =
  {
    val system=startSystem()
    scala.sys.addShutdownHook(onShutdown(system))
  }
}
