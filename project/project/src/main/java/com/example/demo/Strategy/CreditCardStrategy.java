package com.example.demo.Strategy;

public class CreditCardStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount, String cardNumber, String cardHolder) {
        // Simulate credit card payment processing
        System.out.println("Processing credit card payment for " + cardHolder);
        System.out.println("Amount: " + amount);
        System.out.println("Card Number: " + cardNumber);
        
        // For demo purposes, always return true
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "Credit Card";
    }
} 