import { Notifier, EmailService, SMSService, MessageService } from './service';

// Contenedor simple de inyección manual
export class ServiceContainer {
    static getEmailNotifier(): Notifier {
        const emailService: MessageService = new EmailService();
        return new Notifier(emailService);
    }

    static getSMSNotifier(): Notifier {
        const smsService: MessageService = new SMSService();
        return new Notifier(smsService);
    }
}
