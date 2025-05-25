package com.ejemplo.proyecto;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
public class LoggingAspect {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Pointcut para préstamo de libros
    @Pointcut("execution(* com.ejemplo.proyecto.BookManager.lendBook(..))")
    public void lendBookOperation() {}

    // Pointcut para devolución de libros
    @Pointcut("execution(* com.ejemplo.proyecto.BookManager.returnBook(..))")
    public void returnBookOperation() {}

    // Pointcut para operaciones del BookManager que NO sean prestar ni devolver
    // @Pointcut("execution(* com.ejemplo.proyecto.BookManager.*(..)) && !lendBookOperation() && !returnBookOperation()")
    // public void otherBookManagerOperations() {}

    // Advice que se ejecuta antes de prestar un libro
    @Before("lendBookOperation()")
    public void beforeLendBook(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("[" + getCurrentTime() + "] PRÉSTAMO INICIADO - MiembroID: " + args[0] + ", ISBN: " + args[1]);
    }

    // Advice que se ejecuta después de prestar un libro
    @After("lendBookOperation()")
    public void afterLendBook(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("[" + getCurrentTime() + "] PRÉSTAMO COMPLETADO - MiembroID: " + args[0] + ", ISBN: " + args[1]);
    }

    // Advice que se ejecuta antes de devolver un libro
    @Before("returnBookOperation()")
    public void beforeReturnBook(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("[" + getCurrentTime() + "] DEVOLUCIÓN INICIADA - MiembroID: " + args[0] + ", ISBN: " + args[1]);
    }

    // Advice que se ejecuta después de devolver un libro
    @After("returnBookOperation()")
    public void afterReturnBook(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("[" + getCurrentTime() + "] DEVOLUCIÓN COMPLETADA - MiembroID: " + args[0] + ", ISBN: " + args[1]);
    }

    // Advice para otras operaciones del BookManager
    // @Before("otherBookManagerOperations()")
    // public void beforeOtherOperations(JoinPoint joinPoint) {
    //     System.out.println("[" + getCurrentTime() + "] Ejecutando otro método: " + joinPoint.getSignature().getName());
    // }

    private String getCurrentTime() {
        return LocalDateTime.now().format(formatter);
    }
}
