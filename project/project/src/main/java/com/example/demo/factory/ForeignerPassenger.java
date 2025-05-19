package com.example.demo.factory;

public class ForeignerPassenger implements Passenger {
    @Override
    public double calculatePrice(double basePrice) {
        return basePrice * 1.2; // 20% extra for foreigners
    }

    @Override
    public String getType() {
        return "Foreigner";
    }
} 