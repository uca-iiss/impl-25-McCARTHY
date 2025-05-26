package com.ejemplo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class AuditoriaAspect {

    @Around("execution(* com.ejemplo.BancoServicio.*(..))")
    public Object auditarMetodo(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        if (args.length > 0 && args[0] instanceof String) {
            System.out.println("[AUDITORÍA] Usuario: " + args[0]);
        }

        String metodo = joinPoint.getSignature().getName();
        long inicio = System.nanoTime();

        Object resultado = joinPoint.proceed();

        long fin = System.nanoTime();
        System.out.printf("[AUDITORÍA] Método '%s' ejecutado en %d microsegundos.%n",
                metodo, (fin - inicio) / 1000);

        return resultado;
    }
}
