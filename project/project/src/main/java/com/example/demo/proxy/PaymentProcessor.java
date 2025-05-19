package com.example.demo.proxy;

public interface PaymentProcessor {
    boolean processPayment(double amount, String cardNumber, String cardHolder);
    String getPaymentStatus();
} 