# Tema A.1 - Abstracción

Este proyecto demuestra el concepto de **abstracción** en programación orientada a objetos utilizando el lenguaje **C#**. Se modelan distintos tipos de cuentas bancarias (`CuentaCorriente` y `CuentaAhorros`) que heredan de una clase base abstracta (`CuentaBancaria`). El programa principal (`BancoMain.cs`) muestra cómo utilizar estas clases de manera polimórfica.

## Código fuente

### [`CuentaBancaria`](src/CuentaBancaria.cs) (Clase abstracta)

- **Atributos:**
    - `Titular`: nombre del dueño (solo lectura).
    - `Saldo`: saldo disponible (lectura pública, modificación protegida).

- **Constructor:**
    - Inicializa el titular y el saldo inicial.

- **Métodos abstractos (a implementar por subclases):**
    - `Depositar(decimal cantidad)`
    - `Retirar(decimal cantidad)`

- **Método virtual:**
    - `MostrarInfo()`: muestra el titular y el saldo en consola.

- **Uso:**
    - Clase base para diferentes tipos de cuentas bancarias. No se puede instanciar directamente.
---

### [CuentaAhorros](src/CuentaAhorros.cs) (subclase de CuentaBancaria):

- **Atributos**:
    - `tasaInteres`: fija en 3% (0.03).

- **Constructor:**
    - Llama al constructor de la clase base con titular y saldo inicial.

- **Métodos sobrescritos**:
    - `Depositar(cantidad)`: suma al saldo si la cantidad es positiva.

    - `Retirar(cantidad)`: resta del saldo si hay fondos suficientes.

    - `MostrarInfo()`: muestra detalles específicos de cuenta de ahorros.

- **Método adicional**:

    - `AplicarInteres()`: calcula e incrementa el saldo con interés acumulado.

- **Uso**:

    - Modelo específico de cuenta que permite aplicar intereses sobre el saldo.

---

### [CuentaCorriente](src/CuentaCorriente.cs) (subclase de CuentaBancaria):

- **Atributos:**

    - `limiteDescubierto`: permite sobregiros hasta 500 unidades.

- **Constructor:**

    - Llama al constructor de la clase base con titular y saldo inicial.

- **Métodos sobrescritos:**

    - `Depositar(cantidad)`: suma al saldo si la cantidad es positiva.

    - `Retirar(cantidad)`: permite retiro incluso si hay sobregiro dentro del límite.

    - `MostrarInfo()`: muestra titular, saldo y límite de descubierto.

- **Uso:**

    - Modelo de cuenta que admite saldo negativo limitado.

## Tests

### [CuentaBancariaTests](tests/CuentaBancariaTests.cs) (pruebas unitarias):

- **Herramienta:**
    - Usa el framework de pruebas `xUnit`.

- **Pruebas para CuentaAhorros:**

    - `DepositarEnCuentaAhorros_AumentaSaldo`: verifica que el saldo aumenta al depositar.

    - `RetirarEnCuentaAhorros_RetiraSaldoCorrectamente`: confirma que el retiro descuenta correctamente.

    - `RetirarEnCuentaAhorros_NoPermiteSaldoNegativo`: asegura que no se puede retirar más de lo disponible.

    - `AplicarInteres_AumentaSaldo`: verifica que aplicar interés incrementa el saldo.

- **Pruebas para `CuentaCorriente`:**

    - `RetirarEnCuentaCorriente_PermiteDescubierto`: permite sobregiro dentro del límite.

    - `RetirarEnCuentaCorriente_NoPermiteExcederDescubierto`: impide retiros que excedan el límite de descubierto.

- **Uso:**

    - Garantiza que las funcionalidades clave de las cuentas bancarias se comportan correctamente bajo distintos escenarios.

## Ficheros de configuración de `.NET 6.0`

- [Abstraccion.csproj](src/Abstraccion.csproj)
- [AbstraccionTest.csproj](src/Abstraccion.csproj)

## Ficheros de configuración 

### [Dockerfile](Dockerfile)
Define la imagen de Docker utilizada en el entorno del proyecto.

### [main.tf](main.tf)
Archivo principal de configuración de infraestructura en Terraform.

### [Jenkinsfile](Jenkinsfile)
Contiene la configuración del pipeline de integración continua (CI) usando Jenkins.

## Despliegue
Se requiere descargar la imagen necesaria para llevar a cabo el despliegue:
````bash
docker build -t myjenkins-dotnet .
````

Para realizar el despliegue, se debe ejecutar el siguiente script. Este creará los recursos necesarios en Docker y ejecutará los comandos correspondientes de Terraform:
````bash
python ./deploy_jenkins.py
````

Una vez realizado este paso, podemos usar Jenkins en el localhost.

Para liberar los recursos, es necesario realizar los siguientes comandos.
````bash
docker stop <nombre-contenedor-dind>
docker stop <nombre-contenedor-jenkins>
docker rm <nombre-contenedor-dind>
docker rm <nombre-contenedor-jenkins>
docker network rm <id-network-jenkins>
````

