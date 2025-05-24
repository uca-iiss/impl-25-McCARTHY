package com.example;

public class PaypalProcessor implements CreditCardProcessor {
    @Override
    public void process(String amount) {
        System.out.println("Procesando pago de " + amount + " vía PayPal.");
    }
}
