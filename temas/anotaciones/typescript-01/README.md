# Tema B.2 - Anotaciones

Proyecto en TypeScript que demuestra el uso de decoradores para anotar clases y métodos en un controlador de usuarios. Incluye ejemplos, pruebas unitarias con Jest y configuración para despliegue y automatización.

## Código fuente

### [`decorators.ts`](src/decorators.ts) (Decoradores personalizados)

Este fichero define varios decoradores que añaden metadatos o modifican el comportamiento de clases y métodos. Utiliza la librería reflect-metadata para gestionar metainformación en tiempo de ejecución.

- **Decoradores definidos:**

    - `@Controller(basePath)`

        - **Tipo:** `ClassDecorator`

        - **Función:** Asigna una ruta base (basePath) a la clase controladora mediante metadatos.

    - `@Route(method, path)`

        - **Tipo:** `MethodDecorator`

        - **Función:** Registra rutas HTTP (GET, POST, etc.) asociadas a métodos, y las almacena como metadatos.

    - `@Log`

        - **Tipo:** `MethodDecorator`

        - **Función:** Imprime en consola una traza de los argumentos recibidos al llamar al método. Útil para depuración.

    - `@Auth(role)`

        - **Tipo:** `MethodDecorator`

        - **Función:** Restringe el acceso al método dependiendo del rol del usuario. Lanza error si no coincide.

    - `@ValidateStringParams`

        - **Tipo:** `MethodDecorator`

        - **Función:** Valida que todos los parámetros del método sean cadenas (string). Lanza error si alguno no lo es.

- **Uso:**
    - Estos decoradores permiten modularizar funcionalidades transversales como autorización, logging y validación, favoreciendo un diseño limpio y extensible en aplicaciones con controladores HTTP.

---

### [`simulate.ts`](src/simulate.ts) (Simulador de peticiones HTTP)

Este archivo define una utilidad para simular el enrutamiento de peticiones HTTP basándose en metadatos añadidos por los decoradores definidos en decorators.ts.

- **Función principal:**

    - `simulateRequest(controllerClass, method, path, ...args)`

        - **Función:** Emula una petición HTTP (GET o POST) a un método de un controlador anotado.

        - **Lógica:**

            - Obtiene la ruta base (`@Controller`) y las rutas registradas (`@Route`) mediante `reflect-metadata`.

            - Busca un método que coincida con el verbo HTTP y la ruta completa.

            - Si lo encuentra, instancia la clase y ejecuta el método decorado con los argumentos dados.

            - Imprime el resultado o los errores en consola.

- **Uso:**

    - Permite probar la lógica de los controladores sin necesidad de un servidor real, usando los metadatos definidos por los decoradores. Es ideal para entornos de desarrollo y testeo.

---

### [`user.controller.ts`](src/user.controller.ts) (Controlador con decoradores personalizados)

Este archivo define una clase UserController que demuestra el uso de decoradores personalizados para enriquecer métodos con metadatos, validaciones y lógica adicional.

- **Decoradores aplicados:**

    - `@Controller("/api/user")`

        - Asocia la clase con una ruta base para todas sus rutas HTTP.

- **Métodos:**

    - `greet(name: string)`

        - **Decoradores:**

            - `@Route("GET", "/greet")`: expone el método como una ruta GET.

            - `@Log`: registra las llamadas al método y sus argumentos.

            - `@ValidateStringParams`: valida que todos los parámetros sean cadenas.

        - **Función:** Devuelve un saludo personalizado.

    - `performAdminAction(user: any)`

        - **Decoradores:**

            - `@Route("POST", "/admin")`: expone el método como una ruta POST.

            - `@Auth("admin")`: requiere que el usuario tenga rol "admin".

            - `@Log`: registra la ejecución del método.

        - **Función:** Simula una acción protegida exclusiva para usuarios con permisos de administrador.

- **Uso:**
    - Este controlador sirve como ejemplo de integración de múltiples decoradores en un entorno simulado, mostrando cómo modularizar validaciones, control de acceso y trazabilidad de forma clara y reutilizable.

## Tests

### [`user.controller.test.ts`](tests/user.controller.test.ts) (Pruebas de comportamiento con decoradores)

Este archivo contiene pruebas unitarias para verificar cómo los decoradores afectan el comportamiento del `UserController`.

- **Casos de prueba:**

    - **Saludo correcto:**

        - Simula una petición `GET /api/user/greet` con `"Alice"`.

        - Verifica que se imprime `"Hello, Alice!"`.

    - **Acción permitida para admin:**

        - Simula una `POST /api/user/admin` con un usuario con rol `"admin"`.

        - Verifica la respuesta positiva con el nombre del administrador.

    - **Acción bloqueada para no-admin:**

        - Simula misma ruta con un usuario sin permisos.

        - Se espera error `"Unauthorized"`.

    - **Parámetro inválido en saludo:**

        - Envía un número en lugar de cadena a `greet`.

        - Verifica que lanza un error por tipo incorrecto.

- **Uso:**

    - Probar que los decoradores como `@Route`, `@Auth`, `@Log` y `@ValidateStringParams se aplican correctamente y modifican la ejecución según lo esperado.

    - Estas pruebas validan la lógica encapsulada en decoradores, asegurando control de acceso, validación de tipos y comportamiento esperado de rutas HTTP sin un framework web real.

## Ficheros de configuración de `Node.js` y `Jest`

- [tsconfig.json](./tsconfig.json)
- [package.json](./package.json)
- [jest.config.js](./jest.config.js)

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
docker build -t myjenkins-nodejsTS .
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
