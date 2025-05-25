class Aplicacion(private val nombre: String, private val logger: Logger) : Logger by logger {
    fun iniciar() {
        logInfo("Aplicación $nombre iniciada.")
    }

    fun falloCritico() {
        logError("Fallo crítico en $nombre.")
    }
}
