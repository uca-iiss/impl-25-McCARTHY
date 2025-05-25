package domain

import org.scalatest.funsuite.AnyFunSuite

class UserServiceSpec extends AnyFunSuite {

  test("findUser returns Some(name) for existing user") {
    assert(UserService.findUser(1).contains("Alice"))
  }

  test("findUser returns None for non-existing user") {
    assert(UserService.findUser(999).isEmpty)
  }

  test("processUsers returns uppercase names for valid users only") {
    val result = UserService.processUsers(List(1, 999, 2))
    assert(result == List("ALICE", "BOB"))
  }

  test("findUserEither returns Right(name) for existing user") {
    assert(UserService.findUserEither(2) == Right("Bob"))
  }

  test("findUserEither returns Left(error) for non-existing user") {
    val result = UserService.findUserEither(123)
    assert(result == Left("User with id 123 not found"))
  }

  test("processUsersWithErrors separates successes and errors") {
    val (successes, errors) = UserService.processUsersWithErrors(List(1, 99, 2))
    assert(successes.contains("ALICE") && successes.contains("BOB"))
    assert(errors.contains("User with id 99 not found"))
  }
}
