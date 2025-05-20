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
    leon.nombre = "León"
    leon.habitat = "Sabana africana"

    pajaro.nombre = "Canario"
    pajaro.habitat = "Bosque"

    println("\n--- USAMOS LOS MÉTODOS DELEGADOS ---")
    leon.describir()
    leon.hacerSonido() 
    leon.moverse()     

    println("═".repeat(50))

    pajaro.describir()
    pajaro.hacerSonido()
    pajaro.moverse()     
}
