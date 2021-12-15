package util

import com.typesafe.config.{Config, ConfigFactory}

/**
 * Created by RXL on 2015/8/21.
 */
object Conf {
  val config: Config = ConfigFactory.load()
  ConfigFactory.invalidateCaches()
//  config.checkValid(ConfigFactory.defaultReference)

  lazy val tcpHost=config.getString("gs-app.tcp.hostname")
  lazy val tcpPort=config.getInt("gs-app.tcp.port")
  object Database{
    lazy val gameDbName="db.gamedb"
  }
}
