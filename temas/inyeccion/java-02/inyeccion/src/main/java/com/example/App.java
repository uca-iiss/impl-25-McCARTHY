package com.example;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class App {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new GuiceModule());
        BillingService service = injector.getInstance(BillingService.class);
        service.checkout("100.00€");
    }
}
