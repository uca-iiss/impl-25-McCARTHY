fun main() {
    val logger = ConsoleLogger()
    val app = Aplicacion("MiApp", logger)

    app.iniciar()
    app.falloCritico()
}
