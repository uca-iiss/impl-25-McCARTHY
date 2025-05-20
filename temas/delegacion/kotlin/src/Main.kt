fun main() 
{
    val rugido = Rugido()
    val canto = Canto()
    val pesada = CaminataPesada()
    val ligera = CaminataLigera()

    // Creamos animales con diferentes comportamientos delegados
    val leon = Animal(rugido, pesada)
    val pajaro = Animal(canto, ligera)
    
    // Delegación de propiedades observables
    println(leon.nombre)
    leon.nombre = "León"
    leon.habitat = "Sabana africana"

    pajaro.nombre = "Canario"
    pajaro.habitat = "Bosque"

    println("\n--- USAMOS LOS MÉTODOS DELEGADOS ---")
    leon.describir()
    leon.hacerSonido() // Delegación de interfaz
    // leon.sonido.hacerSonido() -> Sin la delegación de interfaz tendríamos que llamar al método de la clase concreta
    leon.moverse() // Delegación manual

    println("═".repeat(50))

    pajaro.describir()
    pajaro.hacerSonido() // Delegación de interfaz
    pajaro.moverse() // Delegación manual
}
