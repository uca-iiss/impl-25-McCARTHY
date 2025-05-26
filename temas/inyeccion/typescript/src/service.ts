// Interface
export interface MessageService {
    sendMessage(message: string): void;
}

// Implementación concreta
export class EmailService implements MessageService {
    sendMessage(message: string): void {
        console.log(`Email enviado: ${message}`);
    }
}

export class SMSService implements MessageService {
    sendMessage(message: string): void {
        console.log(`SMS enviado: ${message}`);
    }
}

// Cliente que recibe el servicio inyectado
export class Notifier {
    private service: MessageService;

    constructor(service: MessageService) {
        this.service = service;
    }

    notify(message: string): void {
        this.service.sendMessage(message);
    }
}
