package models

import anorm.{Macro, ToParameterList, ~}

case class User(name: String, mobNo: Int)

object User {
  implicit def toParameters: ToParameterList[User] =
    Macro.toParameters[User]
}
