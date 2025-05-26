class Investigador(nombre: String, edad: Int, val especialidad: String)
  extends Persona(nombre, edad) {

  def saludar(): String = 
    s"Soy $nombre, investigador en $especialidad."

  def saludar(formal: Boolean): String = 
    if (formal) s"Buenas, mi nombre es Dr. $nombre, especialista en $especialidad."
    else saludar()

  def presentarse(): String = saludar(formal = true)
}

