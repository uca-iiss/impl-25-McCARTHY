import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.JoinPoint;

@Aspect
public class EnergyControlAspect {
    private Map<String, Long> startTimes = new HashMap<>();

    @Pointcut("execution(* Device.turnOn(..)) && target(dev)")
    public void turnedOn(Device dev) {}

    @Pointcut("execution(* Device.turnOff(..)) && target(dev)")
    public void turnedOff(Device dev) {}

    @Before("turnedOn(dev)")
    public void markStart(Device dev) {
        startTimes.put(dev.getName(), System.currentTimeMillis());
    }

    @After("turnedOff(dev)")
    public void checkDuration(Device dev) {
        Long start = startTimes.remove(dev.getName());
        if (start != null) {
            long duration = (System.currentTimeMillis() - start) / 1000;
            if (duration > 10) {
                System.out.println("[ENERGY] " + dev.getName() +
                        " estuvo encendido por " + duration + "s. Considera optimizar.");
            }
        }
    }
}