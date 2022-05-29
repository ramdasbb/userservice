package service

import anorm.SqlParser.get
import anorm.{SQL, ~, _}
import javax.inject.Inject
import models.{DatabaseExecutionContext, User}
import play.api.db.DBApi

import scala.concurrent.Future

// servie - dao
// interface DAOIMPL - get , insert()
@javax.inject.Singleton // sevice
class UserRepository @Inject() (dbapi: DBApi) (implicit ec: DatabaseExecutionContext) {
  private val db = dbapi.database("default") // default database using h2

  val simple = {
    get[String]("user.name") ~
      get[Int]("user.mob_no") map {
      case  name ~ mobNo =>
        User( name, mobNo)
    }
  }

  def insert(user: User): Future[Option[Long]] = Future {
    db.withConnection { implicit connection =>
      SQL("""
        insert into user values (
          {name}, {mobNo}
        )
      """).bind(user).executeInsert()
    }
  }(ec)

  def findByName(name: String): Future[Option[User]] = Future {
    db.withConnection { implicit connection =>
      SQL"select * from user where name = $name".as(simple.singleOpt)
    }
  }(ec)

}
