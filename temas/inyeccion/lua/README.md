# Tema C.2 - Inyección

Este proyecto demuestra el concepto de inyección de dependencias mediante un sistema de notificaciones. Contiene una interfaz base (`Notifier.lua`) y varias implementaciones concretas, que son inyectadas en el servicio central `NotificationService`. El objetivo es desacoplar el servicio de las implementaciones específicas, facilitando pruebas y extensibilidad.

## Código fuente

### [`Notifier.lua`](src/Notifier.lua) (Interfaz base para servicios de notificación)

Este archivo define una clase abstracta `Notifier` que sirve como contrato común para todas las implementaciones de notificaciones. Se usa como base para aplicar el patrón de inyección de dependencias.

- **Detalles clave:**

    - **Método:**

        - `send(message)`

        - **Comportamiento:**

            - Define un método `send` que lanza un error si no se implementa.

            - Obliga a que las clases hijas (como `EmailNotifier`, `SMSNotifier`, etc.) implementen su propia lógica de envío.

- **Uso:**
    - Permitir que el `NotificationService` funcione con cualquier tipo de notificador (correo, SMS, Slack, etc.) sin estar acoplado a una implementación concreta, facilitando la extensión y pruebas.

---

### [`EmailNotifier.lua`](src/EmailNotifier.lua) (Implementación concreta del notificador por correo electrónico)

Este archivo define una clase `EmailNotifier` que implementa el método send definido en la interfaz `Notifier`.

- **Estructura y comportamiento:**

    - `EmailNotifier:new()`:

        - Método constructor que crea una nueva instancia del notificador, utilizando metatables para simular clases en Lua.

    - `EmailNotifier:send(message)`:

        - Implementación concreta del método send, que devuelve un mensaje indicando que se ha enviado un correo electrónico con el contenido proporcionado.

- **Uso:**
    - Esta clase puede ser inyectada en un `NotificationService`, permitiendo enviar mensajes por correo sin que el servicio conozca detalles de la implementación. Sigue el patrón de inyección de dependencias, facilitando el reemplazo por otras implementaciones (`SMSNotifier`, `SlackNotifier`, etc.).

---

### [`SMSNotifier.lua`](src/SMSNotifier.lua) (Implementación concreta del notificador por SMS)

Este archivo define una clase `SMSNotifier` que implementa el método `send` de la interfaz `Notifier`.

- **Estructura y comportamiento:**

    - `SMSNotifier:new()`:

        - Constructor que crea una instancia del notificador usando el patrón típico de objetos en Lua con metatables.

    - `SMSNotifier:send(message)`:

        - Implementa el envío de un mensaje simulando un SMS. Devuelve una cadena con el prefijo `[SMS]` para indicar el canal utilizado.

- **Uso:**
    - Permite enviar notificaciones vía SMS en una arquitectura desacoplada. Esta clase puede inyectarse dinámicamente en el `NotificationService`, cumpliendo el contrato definido por `Notifier`. Esto promueve el principio de inversión de dependencias, facilitando pruebas y cambios sin afectar al servicio principal.

---

### [`SlackNotifier.lua`](src/SlackNotifier.lua) (Notificador específico para Slack)

Este archivo implementa un componente `SlackNotifier` que sigue la interfaz general `Notifier`, permitiendo el envío de mensajes a través de Slack (de manera simulada).

- **Componentes clave:**

    - `SlackNotifier:new()`:

        - Constructor que crea una instancia del objeto utilizando metatables, el patrón habitual de "clase" en Lua.

    - `SlackNotifier:send(message)`:

        - Método sobrescrito que simula el envío de un mensaje. Devuelve un string con el prefijo `[Slack]`, indicando el canal usado.

- **Uso:**

    - Esta clase es una implementación intercambiable del contrato `Notifier`. Gracias a la inyección de dependencias, puede sustituir fácilmente a otras implementaciones como `EmailNotifier` o `SMSNotifier` sin modificar el código del `NotificationService`. Esto permite desacoplar el canal de notificación de la lógica de negocio, facilitando pruebas, mantenimiento y extensión del sistema.

---

### [`NotificationService.lua`](src/NotificationService.lua) (Servicio con Inyección de Dependencias)

Este archivo define el servicio principal de notificaciones que utiliza inyección de dependencias para enviar mensajes a través de cualquier canal compatible.

- **Componentes clave:**

    - `NotificationService:new(notifier)`:

        Constructor que recibe externamente un objeto `notifier` con un método `send`.

        - Utiliza `assert` para garantizar que el objeto cumple con el contrato requerido.

        - Aplica el patrón de metatables para crear una instancia.

    - `NotificationService:notify(message)`:

        Método que delega la responsabilidad de enviar un mensaje al notificador inyectado, usando su método `send`.

- **Uso:**
    - Este módulo demuestra un uso claro del principio de inversión de dependencias. El servicio no sabe ni le importa cómo se envía el mensaje (Email, SMS, Slack, etc.), solo necesita que el objeto tenga un método `send`. Esto:

        - Favorece el desacoplamiento.

        - Permite sustituir fácilmente implementaciones.

## Tests

### [`NotificationService_spec.lua`](test/NotificationService_spec.lua) (Pruebas unitarias para inyección de dependencias)

Este archivo contiene pruebas que validan el comportamiento del servicio de notificaciones usando diferentes tipos de notificadores inyectados dinámicamente.

- **Casos de prueba:**

    - **"envía notificación por Email"**

        Verifica que `NotificationService` use correctamente `EmailNotifier`, devolviendo el mensaje con el prefijo `[Email]`.

    - **"envía notificación por SMS"**
    
        Asegura que al inyectar `SMSNotifier`, el mensaje se envíe con el formato esperado `[SMS]`.

    - **"envía notificación por Slack"**

        Comprueba que `SlackNotifier` se puede usar intercambiablemente y produce `[Slack]`.

    - **"falla si no se inyecta un objeto válido"**

        Prueba negativa: se espera un error si el objeto inyectado no implementa `send`, cumpliendo así con el contrato del servicio.

- **Uso:**
    - Este test reafirma que el sistema está diseñado con un enfoque desacoplado y extensible, donde nuevos canales de notificación pueden añadirse fácilmente sin modificar el servicio base. También demuestra robustez, al validar que los errores se detectan tempranamente si se rompe el contrato de inyección.

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
docker build -t myjenkins-busted .
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