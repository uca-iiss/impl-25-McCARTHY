# Abstraccion en Lambdas

## Introducción
 

## Estructura de Directorio

- `/README.md`: Archivo actual.
- `/common.scala`: 
- `/CreditCard.scala`: 
- `/PaymentMethod.scala`: 
- `/Paypal.scala`: 
- `/PaymentMethodSpec.scala`: 

## Conceptos Previos


## Código de Ejemplo
A continuacion tenemos el codigo principal de abstracción en Scala:

[**PayPal.scala**](./abstraccion-pagos/src/main/scala/payment/PayPal.scala)
```scala
package payment

class PayPal(private val email: String, private var balance: Money) extends PaymentMethod {

  val name: String = "PayPal"

  def pay(amount: Money): TransactionStatus = {
    if (amount <= balance) {
      balance -= amount
      logTransaction(amount, Success)
      Success
    } else {
      logTransaction(amount, Failure)
      Failure
    }
  }

  def recharge(amount: Money): Unit = {
    balance += amount
  }
  
  def getBalance: Money = balance
}
```

[**PaymentMethod.scala**](./abstraccion-pagos/src/main/scala/payment/PaymentMethod.scala)
```scala
package payment

// Clase abstracta base
abstract class PaymentMethod {

  val name: String

  def pay(amount: Money): TransactionStatus

  protected def logTransaction(amount: Money, status: TransactionStatus): Unit = {
    println(s"[$name] Transacción de $amount€ => Estado: $status")
  }
}
```

[**CreditCard.scala**](./abstraccion-pagos/src/main/scala/payment/CreditCard.scala)
```scala
package payment

class CreditCard(cardNumber: String, var creditLimit: Money) extends PaymentMethod {

  val name: String = "Tarjeta de Crédito"
  def pay(amount: Money): TransactionStatus = {
    if (amount <= creditLimit) {
      creditLimit -= amount
      logTransaction(amount, Success)
      Success
    } else {
      logTransaction(amount, Failure)
      Failure
    }
  }
}
```

[**common.scala**](./abstraccion-pagos/src/main/scala/payment/common.scala)
```scala
package payment

type Money = Double
sealed trait TransactionStatus

case object Success extends TransactionStatus
case object Failure extends TransactionStatus
```


## Código de tests
Ahora, se muestra unos tests para probar el correcto uso de las funciones abstraccion en Scala:

[**PaymentMethodSpec.scala**](./abstraccion-pagos/src/test/scala/payment/PaymentMethodSpec.scala)
```scala
package payment

import org.scalatest.funsuite.AnyFunSuite

class PaymentMethodSpec extends AnyFunSuite {

  test("CreditCard permite pago si hay suficiente crédito") {
    val card = new CreditCard("1234", 100.0)
    val result = card.pay(50.0)
    assert(result == Success)
  }

  test("CreditCard rechaza pago si no hay suficiente crédito") {
    val card = new CreditCard("1234", 30.0)
    val result = card.pay(50.0)
    assert(result == Failure)
  }

  test("PayPal permite pago si hay saldo suficiente") {
    val paypal = new PayPal("user@example.com", 80.0)
    val result = paypal.pay(60.0)
    assert(result == Success)
  }

  test("PayPal rechaza pago si el saldo es insuficiente") {
    val paypal = new PayPal("user@example.com", 20.0)
    val result = paypal.pay(30.0)
    assert(result == Failure)
  }

  test("Paypal recarga saldo corretamente"){
    val paypal = new PayPal("user@example.com", 0.0)
    paypal.recharge(100.0)
    assert(paypal.getBalance == 100.0)
  }
}
```

## Ejecución Test
Para ejecutar el código y pasar los test de dicho código, realiza los siguientes pasos detallados que incluyen la creación de un Jenkinsfile, creación pipeline y ejecución del pipeline

### 1. Creación Jenkinsfile
A continuación, hemos creado el Jenkinsfile necesario para realizar el pipeline, este se encuentra en la carpeta con el resto de código

```Jenkinsfile
pipeline {
    agent {
        docker {
            image 'sbtscala/scala-sbt:eclipse-temurin-11.0.14.1_1.6.2_2.13.8'
            args '-v /root/.ivy2:/root/.ivy2' // Cache de dependencias
        }
    }

    environment {
        PROJECT_DIR = "temas/abstraccion/scala/abstraccion-pagos"
    }

    stages {
        stage('Limpiar') {
            steps {
                dir("${env.PROJECT_DIR}") {
                    sh 'sbt clean'
                }
            }
        }

        stage('Compilar') {
            steps {
                dir("${env.PROJECT_DIR}") {
                    sh 'sbt compile'
                }
            }
        }

        stage('Test') {
            steps {
                dir("${env.PROJECT_DIR}") {
                    sh 'sbt test'
                }
            }
        }
    }

    post {
        success {
            echo 'Build finalizado correctamente.'
        }
        failure {
            echo 'Ha fallado alguna etapa del build.'
        }
    }
}
```

### 2. Crear Pipeline
Una vez realizados los pasos anteriores, abrimos Jenkins y creamos un nuevo Pipeline. Para ello: 

 - Lo definimos como `Pipeline script from SCM` y como SCM seleccionamos `Git`.
 - Ponemos la siguiente URL: `https://github.com/uca-iiss/WIRTH-impl-25`.
 - En branch ponemos `*/feature-abstraccion`.
 - Por último, en Script Path añadimos `temas/abstraccion/scala/abstraccion-pagos/Jenkinsfile`

Y con esta configuración hemos creado el pipeline necesario para la ejecución de los test

### 3. Ejecutar los Tests
Una vez creado el pipeline, ejecutamos dando a `Construir ahora` y el propion Jenkins pasará los test automaticamente.