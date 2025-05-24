package com.example;

import com.google.inject.AbstractModule;

public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CreditCardProcessor.class).to(PaypalProcessor.class);
    }
}
