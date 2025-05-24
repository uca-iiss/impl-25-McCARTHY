package com.example;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class PaypalProcessorTest {

    @Test
    void testProcess() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        CreditCardProcessor processor = new PaypalProcessor();
        processor.process("50€");

        String output = outContent.toString().trim();
        assertTrue(output.contains("Procesando pago de 50€ vía PayPal."));
    }
}
