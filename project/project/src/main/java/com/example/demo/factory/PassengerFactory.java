package com.example.demo.factory;

public class PassengerFactory {
    public static Passenger createPassenger(String type) {
        return switch (type.toLowerCase()) {
            case "child" -> new ChildPassenger();
            case "adult" -> new AdultPassenger();
            case "foreigner" -> new ForeignerPassenger();
            default -> throw new IllegalArgumentException("Invalid passenger type: " + type);
        };
    }
} 