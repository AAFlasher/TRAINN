package com.example.demo.proxy;

public class RealPaymentProcessor implements PaymentProcessor {
    private String paymentStatus;

    @Override
    public boolean processPayment(double amount, String cardNumber, String cardHolder) {
        // Simulate actual payment processing
        System.out.println("Processing payment of $" + amount + " for " + cardHolder);
        System.out.println("Card ending in: " + cardNumber.substring(cardNumber.length() - 4));
        
        // Simulate successful payment
        this.paymentStatus = "Payment processed successfully";
        return true;
    }

    @Override
    public String getPaymentStatus() {
        return paymentStatus;
    }
} 