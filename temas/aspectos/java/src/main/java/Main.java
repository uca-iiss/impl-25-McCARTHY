public class Main {
    public static void main(String[] args) throws InterruptedException {
        Luz sala = new Luz("Luz del salón");
        Termostato termostato = new Termostato("Termostato");
        SmartLock puerta = new SmartLock("Puerta principal");

        sala.turnOn();
        Thread.sleep(2000);
        sala.turnOff();

        termostato.turnOn();
        termostato.setTemperature(25);
        termostato.turnOff();

        try {
            puerta.unlock(); // Fallará sin login
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        SecurityAspect.login();
        puerta.unlock();
        SecurityAspect.logout();
    }
}