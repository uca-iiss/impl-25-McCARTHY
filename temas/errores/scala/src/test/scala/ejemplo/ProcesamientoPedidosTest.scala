package ejemplo

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scala.util.Success

class ProcesamientoPedidosSpec extends AnyFlatSpec with Matchers {

  // --- Datos de prueba ---
  val pedidoValido = Pedido("001", "Cliente A", List(
    ItemPedido("i1", "Producto 1", 10.0, 2), // Total: 20
    ItemPedido("i2", "Producto 2", 5.0, 3)   // Total: 15 → Total pedido: 35
  ), Some("Calle Falsa 123"))

  val pedidoSinItems = Pedido("002", "Cliente B", List.empty, None) // Inválido (sin items)
  val pedidoConItemInvalido = Pedido("003", "Cliente C", List(
    ItemPedido("i3", "Producto 3", -5.0, 1) // Inválido (precio negativo)
  ), None)

  // --- Pruebas para 'calcularTotal' (Option) ---
  "calcularTotal" should "devolver Some(35.0) para un pedido valido" in {
    ProcesamientoPedidos.calcularTotal(pedidoValido) shouldBe Some(35.0)
  }

  it should "devolver None para un pedido sin items" in {
    ProcesamientoPedidos.calcularTotal(pedidoSinItems) shouldBe None
  }

  it should "devolver None para un pedido con items invalidos" in {
    ProcesamientoPedidos.calcularTotal(pedidoConItemInvalido) shouldBe None
  }

  // --- Pruebas para 'procesarStreamPedidos' (Either) ---
  "procesarStreamPedidos" should "procesar un stream con pedidos validos e invalidos" in {
    val stream = LazyList(pedidoValido, pedidoSinItems, pedidoConItemInvalido)
    val resultados = ProcesamientoPedidos.procesarStreamPedidos(stream).toList

    resultados should contain (Right(("001", 35.0))) // Pedido válido
    resultados should contain (Left("Pedido 002 no tiene items validos")) // Sin items
    resultados should contain (Left("Pedido 003 no tiene items validos")) // Ítem inválido
  }

  // --- Pruebas para 'parsearPedido' (Try) ---
  "parsearPedido" should "parsear correctamente un pedido valido" in {
    val datosValidos = Map(
      "id" -> "004",
      "cliente" -> "Cliente D",
      "items" -> List(
        Map("id" -> "i4", "nombre" -> "Producto 4", "precio" -> 15.0, "cantidad" -> 2)
      ),
      "direccion" -> "Av. Siempre Viva 742"
    )

    val resultado = ProcesamientoPedidos.parsearPedido(datosValidos)
    resultado.isSuccess shouldBe true // Verifica Success
    resultado.get.id shouldBe "004" // Accede al valor parseado
  }

  it should "fallar al parsear datos invalidos (p.ej., precio como String)" in {
    val datosInvalidos = Map(
      "id" -> "005",
      "cliente" -> "Cliente E",
      "items" -> List(
        Map("id" -> "i5", "nombre" -> "Producto 5", "precio" -> "diez", "cantidad" -> 1) // "precio" no es Double
      )
    )

    val resultado = ProcesamientoPedidos.parsearPedido(datosInvalidos)
    resultado.isFailure shouldBe true // Verifica Failure
  }
}