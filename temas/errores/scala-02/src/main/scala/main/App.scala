package main

import domain.UserService

object App {
  def main(args: Array[String]): Unit = {
    val ids = List(1, 4, 2, 5, 3)

    println("Process users ignoring missing (Option):")
    println(UserService.processUsers(ids))

    println("\nProcess users reporting errors (Either):")
    val (successes, errors) = UserService.processUsersWithErrors(ids)
    println("Successes: " + successes)
    println("Errors: " + errors)
  }
}
