package com.example.demo.Strategy;

public class DebitCardStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount, String cardNumber, String cardHolder) {
        // Simulate debit card payment processing
        System.out.println("Processing debit card payment for " + cardHolder);
        System.out.println("Amount: " + amount);
        System.out.println("Card Number: " + cardNumber);
        
        // For demo purposes, always return true
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "Debit Card";
    }
} 