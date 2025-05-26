package app

import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import java.io.File
import java.io.IOException

class MainApp {
    val intro: String
        get() = "Demostración de gestión de errores en Kotlin"
}

class InvalidInputException(message: String) : Exception(message)

fun parseInteger(input: String): Int {
    return try {
        input.toInt()
    } catch (e: NumberFormatException) {
        throw InvalidInputException("El valor '$input' no es un número válido.")
    }
}

fun accessElement(index: Int): Int {
    val data = listOf(10, 20, 30)
    return data[5]
}

fun readConfig(path: String): String {
    try {
        return File(path).readText()
    } catch (e: IOException) {
        throw IOException("Error al leer el archivo de configuración.")
    }
}

fun riskyOperation(value: Int): Int {
    if (value < 0) {
        throw IllegalArgumentException("No se aceptan valores negativos.")
    }
    else{
    return 0 + value
    }
}
