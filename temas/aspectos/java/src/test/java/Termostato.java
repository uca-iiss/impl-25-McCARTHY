public class Termostato implements Device {
    private String name;
    private boolean isOn = false;
    private int temperature = 20;

    public Termostato(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void setTemperature(int t) {
        if (t < 5 || t > 35)
            throw new IllegalArgumentException("Temperatura fuera de rango");
        temperature = t;
        System.out.println(name + " temperatura ajustada a " + temperature + "C.");
    }

    public int getTemperature() {
        return temperature;
    }

    public void turnOn() {
        isOn = true;
        System.out.println(name + " encendido.");
    }

    public void turnOff() {
        isOn = false;
        System.out.println(name + " apagado.");
    }

    public boolean isOn() {
        return isOn;
    }
}