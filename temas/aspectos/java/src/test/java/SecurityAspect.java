import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.JoinPoint;

@Aspect
public class SecurityAspect {
    private static boolean authenticated = false;

    public static void login() {
        authenticated = true;
        System.out.println("[AUTH] Usuario autenticado.");
    }

    public static void logout() {
        authenticated = false;
        System.out.println("[AUTH] Usuario desconectado.");
    }

    public static boolean isLoggedIn() {
        return authenticated;
    }
}