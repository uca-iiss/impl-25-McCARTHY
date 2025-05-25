class ConsoleLogger : Logger {
    override fun logInfo(message: String) {
        println("INFO: $message")
    }

    override fun logError(message: String) {
        println("ERROR: $message")
    }
}
