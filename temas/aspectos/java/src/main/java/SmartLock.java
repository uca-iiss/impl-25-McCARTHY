public class SmartLock implements Device {
    private String name;
    private boolean locked = true;

    public SmartLock(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void unlock() {
        System.out.println(name + " desbloqueada.");
        locked = false;
    }

    public void lock() {
        System.out.println(name + " bloqueada.");
        locked = true;
    }

    public void turnOn() {} // No-op
    public void turnOff() {} // No-op
}