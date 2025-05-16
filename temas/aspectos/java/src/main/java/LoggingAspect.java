import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;

@Aspect
public class LoggingAspect {

    @Pointcut("execution(* Device.turnOn(..)) || execution(* Device.turnOff(..))")
    public void powerControl() {}

    @Before("powerControl()")
    public void logAction(JoinPoint jp) {
        Device device = (Device) jp.getTarget();
        System.out.println("[LOG] Acción: " + jp.getSignature().getName() +
            " sobre dispositivo: " + device.getName());
    }
}