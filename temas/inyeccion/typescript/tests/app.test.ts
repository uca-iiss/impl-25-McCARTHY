import { Notifier, EmailService, SMSService } from '../src/service';

describe('Notifier', () => {
    it('should send an email', () => {
        const emailService = new EmailService();
        const notifier = new Notifier(emailService);
        console.log = jest.fn();

        notifier.notify("Prueba Email");

        expect(console.log).toHaveBeenCalledWith("Email enviado: Prueba Email");
    });

    it('should send an SMS', () => {
        const smsService = new SMSService();
        const notifier = new Notifier(smsService);
        console.log = jest.fn();

        notifier.notify("Prueba SMS");

        expect(console.log).toHaveBeenCalledWith("SMS enviado: Prueba SMS");
    });
});
