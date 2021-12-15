package template.db
// AUTO-GENERATED Slick data model for table Account
trait AccountTable {

  self:Tables  =>

  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}
  /** Entity class storing rows of table Account
   *  @param id Database column id SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param user Database column user SqlType(BIGINT)
   *  @param platform Database column platform SqlType(VARCHAR), Length(50,true), Default()
   *  @param timestamp Database column timestamp SqlType(BIGINT) */
  case class AccountRow(id: Long, user: Long, platform: String = "", timestamp: Long)
  /** GetResult implicit for fetching AccountRow objects using plain SQL queries */
  implicit def GetResultAccountRow(implicit e0: GR[Long], e1: GR[String]): GR[AccountRow] = GR{
    prs => import prs._
    AccountRow.tupled((<<[Long], <<[Long], <<[String], <<[Long]))
  }
  /** Table description of table account. Objects of this class serve as prototypes for rows in queries. */
  class Account(_tableTag: Tag) extends profile.api.Table[AccountRow](_tableTag, Some("game"), "account") {
    def * = (id, user, platform, timestamp) <> (AccountRow.tupled, AccountRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(user), Rep.Some(platform), Rep.Some(timestamp))).shaped.<>({r=>import r._; _1.map(_=> AccountRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column user SqlType(BIGINT) */
    val user: Rep[Long] = column[Long]("user")
    /** Database column platform SqlType(VARCHAR), Length(50,true), Default() */
    val platform: Rep[String] = column[String]("platform", O.Length(50,varying=true), O.Default(""))
    /** Database column timestamp SqlType(BIGINT) */
    val timestamp: Rep[Long] = column[Long]("timestamp")

    /** Index over (user) (database name game_account) */
    val index1 = index("game_account", user)
  }
  /** Collection-like TableQuery object for table Account */
  lazy val Account = new TableQuery(tag => new Account(tag))
}
