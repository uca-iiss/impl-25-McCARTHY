package ejemplo

import scala.util.{Try, Success, Failure}

// --- Definición de clases caso para representar pedidos e items ---
case class ItemPedido(id: String, nombre: String, precio: Double, cantidad: Int)
case class Pedido(id: String, cliente: String, items: List[ItemPedido], direccionEntrega: Option[String])

object ProcesamientoPedidos {

  /**
   * Calcula el total de un pedido usando 'Option' para manejar errores:
   * - None: Pedido invalidos (sin items o con items invalidos).
   * - Some(total): Pedido validos con total calculado.
   *
   * Mecanismo de manejo de errores:
   * - 'Option' evita el uso de 'null' y fuerza al caller a manejar ambos casos.
   * - 'foldLeft' + 'for-comprehension' para acumular el total de manera segura.
   */
  def calcularTotal(pedido: Pedido): Option[Double] = {
    if (pedido.items.isEmpty) {
      None // Pedido sin items → invalidos
    } else {
      pedido.items.foldLeft(Option(0.0)) { (acum, item) =>
        for {
          totalAcum <- acum // Extrae el acumulador (o propaga None)
          itemTotal <- if (item.cantidad > 0 && item.precio >= 0)
                        Some(item.cantidad * item.precio) // Ítem validos
                      else
                        None // Ítem invalidos (cantidad <= 0 o precio negativo)
        } yield totalAcum + itemTotal
      }
    }
  }

  /**
   * Procesa un stream de pedidos usando 'Either' para distinguir:
   * - Right((id, total)): Pedido procesado correctamente.
   * - Left(mensajeError): Pedido con error (detallado en el mensaje).
   *
   * Mecanismo de manejo de errores:
   * - 'Either' es ideal para streams donde queremos preservar los errores
   *   sin detener el procesamiento.
   * - Se combina con 'Option' (de 'calcularTotal') para decidir Left/Right.
   */
  def procesarStreamPedidos(pedidos: LazyList[Pedido]): LazyList[Either[String, (String, Double)]] = {
    pedidos.map { pedido =>
      calcularTotal(pedido) match {
        case Some(total) if total > 0 =>
          Right((pedido.id, total)) // Pedido validos y con total positivo
        case Some(total) if total <= 0 =>
          Left(s"Pedido ${pedido.id} tiene total invalidos: $total") // Total <= 0 (invalidos)
        case Some(_) =>
          Left(s"Pedido ${pedido.id} tiene un total no manejado correctamente") // Cualquier otro valor de Some no cubierto
        case None =>
          Left(s"Pedido ${pedido.id} no tiene items validos") // items vacíos o invalidos
      }

    }
  }

  /**
   * Parsea un pedido desde datos crudos (p.ej., JSON) usando 'Try':
   * - Success(pedido): Parseo exitoso.
   * - Failure(excepción): Error en el parseo (p.ej., tipo de dato incorrecto).
   *
   * Mecanismo de manejo de errores:
   * - 'Try' captura excepciones y las convierte en valores manipulables.
   * - Útil para operaciones que pueden fallar con excepciones (como casts).
   */
  def parsearPedido(datos: Map[String, Any]): Try[Pedido] = Try {
    // Extracción segura con getOrElse (evita NoSuchElementException)
    val id = datos.getOrElse("id", "").asInstanceOf[String]
    val cliente = datos.getOrElse("cliente", "").asInstanceOf[String]

    // Parseo de items (puede lanzar ClassCastException si los tipos son incorrectos)
    val itemsData = datos.getOrElse("items", List.empty).asInstanceOf[List[Map[String, Any]]]
    val items = itemsData.map { itemData =>
      ItemPedido(
        itemData("id").asInstanceOf[String], // Puede fallar si falta "id" o el tipo es incorrecto
        itemData("nombre").asInstanceOf[String],
        itemData("precio").asInstanceOf[Double],
        itemData("cantidad").asInstanceOf[Int]
      )
    }

    // Dirección es opcional (usamos Option para manejarlo)
    val direccion = datos.get("direccion").map(_.asInstanceOf[String])

    Pedido(id, cliente, items, direccion)
  }
}