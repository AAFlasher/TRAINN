package com.example.demo.Strategy;

public interface PaymentStrategy {
    boolean processPayment(double amount, String cardNumber, String cardHolder);
    String getPaymentMethodName();
} 