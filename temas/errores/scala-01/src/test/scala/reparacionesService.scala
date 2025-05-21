object ReparacionService {
    def procesarOrden(orden: Orden): Either[String, Resultado] = {
      for {
        dispositivo <- orden.dispositivo.toRight("Error: No hay dispositivo en la orden")
        diagnostico <- dispositivo.diagnostico.toRight("Error: No se ha realizado diagnostico")
        _ <- if (diagnostico.irreparable) Left("Error: Dispositivo irreparable") else Right(())
      } yield Resultado(s"Reparacion iniciada para ${dispositivo.marca} ${dispositivo.modelo}")
    }
  }