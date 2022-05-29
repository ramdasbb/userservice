package models

import java.util.Date

import anorm.SqlParser.get
import anorm.{Macro, ToParameterList, ~}
import play.api.libs.json.Json

case class User(name: String, mobNo: Int)

object User {
  implicit def toParameters: ToParameterList[User] =
    Macro.toParameters[User]
}
