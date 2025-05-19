package com.example.demo.factory;

public class ChildPassenger implements Passenger {
    @Override
    public double calculatePrice(double basePrice) {
        return basePrice * 0.5; // 50% discount for children
    }

    @Override
    public String getType() {
        return "Child";
    }
} 