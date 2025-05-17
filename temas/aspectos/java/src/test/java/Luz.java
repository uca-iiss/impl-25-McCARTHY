public class Luz implements Device {
    private String name;
    private boolean isOn = false;

    public Luz(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void turnOn() {
        isOn = true;
        System.out.println(name + " encendida.");
    }

    public void turnOff() {
        isOn = false;
        System.out.println(name + " apagada.");
    }

    public boolean isOn() {
        return isOn;
    }
}