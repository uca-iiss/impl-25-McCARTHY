trait Academico {
  def departamento: String
  def infoAcademica(): String = s"Perteneciente al departamento de $departamento"
}

