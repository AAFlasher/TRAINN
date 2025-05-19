package com.example.demo.proxy;

public class PaymentProxy {
    public boolean processPayment(double amount, String cardNumber, String cardHolder) {
        // In a real application, this would connect to a payment gateway
        // For now, we'll simulate a successful payment if the card number is valid
        return isValidCardNumber(cardNumber);
    }

    private boolean isValidCardNumber(String cardNumber) {
        // Basic validation: card number should be 16 digits
        if (cardNumber == null || cardNumber.length() != 16) {
            return false;
        }

        // Check if all characters are digits
        for (char c : cardNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        // Luhn algorithm for card number validation
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
} 