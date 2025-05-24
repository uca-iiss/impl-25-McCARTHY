package com.example;

import com.google.inject.Inject;

public class BillingService {
    private final CreditCardProcessor processor;

    @Inject
    public BillingService(CreditCardProcessor processor) {
        this.processor = processor;
    }

    public void checkout(String amount) {
        processor.process(amount);
    }
}
