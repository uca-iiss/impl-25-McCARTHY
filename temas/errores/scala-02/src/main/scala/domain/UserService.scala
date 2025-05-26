package domain

object UserService {

  private val database: Map[Int, String] = Map(
    1 -> "Alice",
    2 -> "Bob",
    3 -> "Charlie"
  )

  // Busca usuario por id, devuelve Option[String]
  def findUser(id: Int): Option[String] =
    database.get(id)

  // Procesa una lista de IDs, transforma los nombres a mayúsculas,
  // filtrando valores no definidos usando Option y procesamiento funcional.
  def processUsers(ids: List[Int]): List[String] =
    ids.flatMap(findUser).map(_.toUpperCase)

  // Devuelve Either para modelar éxito o error con mensaje
  def findUserEither(id: Int): Either[String, String] =
    database.get(id).toRight(s"User with id $id not found")

  // Procesa con Either, acumulando los errores y éxitos
  def processUsersWithErrors(ids: List[Int]): (List[String], List[String]) = {
    val results = ids.map(findUserEither)
    val successes = results.collect { case Right(name) => name.toUpperCase }
    val errors = results.collect { case Left(err) => err }
    (successes, errors)
  }
}
