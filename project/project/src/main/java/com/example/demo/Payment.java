package com.example.demo;

public class Payment {
    private final String cardNumber;
    private final String cardHolder;
    private final String expireMonth;
    private final String expireYear;
    private final String cvv;
    private final double amount;
    private final String from;
    private final String to;
    private final String date;
    private final String type;
    private final String tickets;

    private Payment(PaymentBuilder builder) {
        this.cardNumber = builder.cardNumber;
        this.cardHolder = builder.cardHolder;
        this.expireMonth = builder.expireMonth;
        this.expireYear = builder.expireYear;
        this.cvv = builder.cvv;
        this.amount = builder.amount;
        this.from = builder.from;
        this.to = builder.to;
        this.date = builder.date;
        this.type = builder.type;
        this.tickets = builder.tickets;
    }

    // Getters
    public String getCardNumber() { return cardNumber; }
    public String getCardHolder() { return cardHolder; }
    public String getExpireMonth() { return expireMonth; }
    public String getExpireYear() { return expireYear; }
    public String getCvv() { return cvv; }
    public double getAmount() { return amount; }
    public String getFrom() { return from; }
    public String getTo() { return to; }
    public String getDate() { return date; }
    public String getType() { return type; }
    public String getTickets() { return tickets; }

    // Builder class
    public static class PaymentBuilder {
        private String cardNumber;
        private String cardHolder;
        private String expireMonth;
        private String expireYear;
        private String cvv;
        private double amount;
        private String from;
        private String to;
        private String date;
        private String type;
        private String tickets;

        public PaymentBuilder cardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public PaymentBuilder cardHolder(String cardHolder) {
            this.cardHolder = cardHolder;
            return this;
        }

        public PaymentBuilder expireMonth(String expireMonth) {
            this.expireMonth = expireMonth;
            return this;
        }

        public PaymentBuilder expireYear(String expireYear) {
            this.expireYear = expireYear;
            return this;
        }

        public PaymentBuilder cvv(String cvv) {
            this.cvv = cvv;
            return this;
        }

        public PaymentBuilder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public PaymentBuilder from(String from) {
            this.from = from;
            return this;
        }

        public PaymentBuilder to(String to) {
            this.to = to;
            return this;
        }

        public PaymentBuilder date(String date) {
            this.date = date;
            return this;
        }

        public PaymentBuilder type(String type) {
            this.type = type;
            return this;
        }

        public PaymentBuilder tickets(String tickets) {
            this.tickets = tickets;
            return this;
        }

        public Payment build() {
            // Validate required fields
            if (cardNumber == null || cardNumber.isEmpty()) {
                throw new IllegalStateException("Card number is required");
            }
            if (cardHolder == null || cardHolder.isEmpty()) {
                throw new IllegalStateException("Card holder name is required");
            }
            if (expireMonth == null || expireMonth.isEmpty()) {
                throw new IllegalStateException("Expire month is required");
            }
            if (expireYear == null || expireYear.isEmpty()) {
                throw new IllegalStateException("Expire year is required");
            }
            if (cvv == null || cvv.isEmpty()) {
                throw new IllegalStateException("CVV is required");
            }
            if (amount <= 0) {
                throw new IllegalStateException("Amount must be greater than 0");
            }
            if (from == null || from.isEmpty()) {
                throw new IllegalStateException("From station is required");
            }
            if (to == null || to.isEmpty()) {
                throw new IllegalStateException("To station is required");
            }
            if (date == null || date.isEmpty()) {
                throw new IllegalStateException("Date is required");
            }
            if (type == null || type.isEmpty()) {
                throw new IllegalStateException("Ticket type is required");
            }
            if (tickets == null || tickets.isEmpty()) {
                throw new IllegalStateException("Number of tickets is required");
            }

            return new Payment(this);
        }
    }
} 