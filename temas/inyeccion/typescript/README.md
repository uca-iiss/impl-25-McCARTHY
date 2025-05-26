# 💡 Inyección de dependencias en TypeScript

## 🧩 ¿Qué es esto?

Este proyecto es un ejemplo práctico y sencillo de cómo aplicar **inyección de dependencias** en TypeScript, sin usar ningún framework. Está pensado para ayudarte a entender este concepto tan útil en el desarrollo de software moderno, especialmente cuando se quiere escribir código limpio, flexible y fácil de mantener.

---

## 🔍 ¿Qué es la inyección de dependencias?

Imagina que tienes una clase que necesita enviar mensajes, pero no sabes si van por email, por SMS o por otro medio. En lugar de que la clase decida **cómo** enviar los mensajes, otra parte del código (el contenedor) se encarga de proporcionarle ese servicio ya configurado.

Ventajas:

* El código queda **desacoplado** (cada parte se centra en lo suyo),
* Es mucho más fácil de **probar**,
* Puedes cambiar una parte sin romper todo lo demás.

---

## 🛠️ Estructura del proyecto

```
temas/inyeccion/typescript/
├── app.ts             // Código principal que usa los notifiers
├── container.ts       // Contenedor que inyecta las dependencias
├── service.ts         // Interfaces y clases concretas (Email y SMS)
├── app.test.ts        // Pruebas con Jest
├── Dockerfile         // Imagen para ejecutar el proyecto
├── Jenkinsfile        // Pipeline de automatización
└── package.json       // Configuración de dependencias y scripts
```

---

## 🤖 ¿Qué hace exactamente?

### 🔧 Paso 1: La interfaz

```ts
interface MessageService {
  sendMessage(message: string): void;
}
```

Tenemos dos implementaciones:

* `EmailService` → simula el envío de un email.
* `SMSService` → simula el envío de un SMS.

### 📬 Paso 2: La clase `Notifier`

```ts
class Notifier {
  constructor(private service: MessageService) {}
  notify(message: string) {
    this.service.sendMessage(message);
  }
}
```

El `Notifier` no sabe cómo se envía el mensaje. Solo sabe que hay un servicio que se encarga.

### 🧰 Paso 3: El contenedor

```ts
class ServiceContainer {
  static getEmailNotifier() {
    return new Notifier(new EmailService());
  }

  static getSMSNotifier() {
    return new Notifier(new SMSService());
  }
}
```

Aquí es donde se realiza la **inyección de dependencias**. El contenedor se encarga de crear y configurar los objetos que se van a utilizar.

### 🚀 Paso 4: Ejecución en `app.ts`

```ts
const emailNotifier = ServiceContainer.getEmailNotifier();
emailNotifier.notify("Hola por correo!");

const smsNotifier = ServiceContainer.getSMSNotifier();
smsNotifier.notify("Hola por SMS!");
```

📦 **Resultado esperado en consola:**

```
Email enviado: Hola por correo!
SMS enviado: Hola por SMS!
```

---

## ✅ Pruebas automatizadas

En `app.test.ts` usamos Jest para verificar que los mensajes que se imprimen son los correctos. Se simula `console.log` para comprobarlo sin depender de la salida real en pantalla.

---

## 🐳 ¿Cómo se ejecuta?

### Con Docker:

```bash
docker build -t inyeccion-typescript .
docker run --rm inyeccion-typescript npm start
```

Para ejecutar los tests:

```bash
docker run --rm inyeccion-typescript npm test
```

---

## ⚙️ ¿Y Jenkins?

El `Jenkinsfile` define una **pipeline de CI/CD** con las siguientes etapas:

1. **Clonación del repositorio**
2. **Construcción de la imagen Docker**
3. **Ejecución de pruebas**
4. **Ejecución de la aplicación**
5. **Limpieza automática de contenedores terminados**

Todo esto se ejecuta de forma automática en cada cambio, asegurando que el código funciona correctamente.

---

## 🧹 Limpieza automática

Al final del pipeline, Jenkins elimina los contenedores que hayan terminado su ejecución para evitar que se acumulen:

```sh
docker ps -aq --filter status=exited | xargs --no-run-if-empty docker rm
```

