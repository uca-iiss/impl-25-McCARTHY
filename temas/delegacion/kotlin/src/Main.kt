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

    println("Usamos los métodos delegados")
    leon.describir()
    leon.hacerSonido() 
    leon.moverse()     

    println("═".repeat(20))

    pajaro.describir()
    pajaro.hacerSonido()
    pajaro.moverse()     
}
