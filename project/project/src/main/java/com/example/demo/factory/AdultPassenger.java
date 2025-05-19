package com.example.demo.factory;

public class AdultPassenger implements Passenger {
    @Override
    public double calculatePrice(double basePrice) {
        return basePrice; // Full price for adults
    }

    @Override
    public String getType() {
        return "Adult";
    }
} 