import { ServiceContainer } from './container';

const emailNotifier = ServiceContainer.getEmailNotifier();
emailNotifier.notify("Hola por correo!");

const smsNotifier = ServiceContainer.getSMSNotifier();
smsNotifier.notify("Hola por SMS!");
