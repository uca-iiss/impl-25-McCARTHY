package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BillingServiceTest {

    class FakeProcessor implements CreditCardProcessor {
        @Override
        public void process(String amount) {
            System.out.println("Procesando en mock: " + amount);
        }
    }

    @Test
    void testCheckoutCallsProcessor() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BillingService service = new BillingService(new FakeProcessor());
        service.checkout("999€");

        String output = outContent.toString().trim();
        assertTrue(output.contains("Procesando en mock: 999€"));
    }
}
