package com.example.demo;

public class Ticket {
    private String ticketId;
    private String trainNumber;
    private String from;
    private String to;
    private String date;
    private String time;
    private String status;
    private String type;
    private String price;
    private String trainType;

    public Ticket(String ticketId, String trainNumber, String from, String to, String date, 
                 String time, String status, String type, String price, String trainType) {
        this.ticketId = ticketId;
        this.trainNumber = trainNumber;
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.status = status;
        this.type = type;
        this.price = price;
        this.trainType = trainType;
    }

    // Getters
    public String getTicketId() { return ticketId; }
    public String getTrainNumber() { return trainNumber; }
    public String getFrom() { return from; }
    public String getTo() { return to; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getStatus() { return status; }
    public String getType() { return type; }
    public String getPrice() { return price; }
    public String getTrainType() { return trainType; }

    // Setters
    public void setTicketId(String ticketId) { this.ticketId = ticketId; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }
    public void setFrom(String from) { this.from = from; }
    public void setTo(String to) { this.to = to; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setStatus(String status) { this.status = status; }
    public void setType(String type) { this.type = type; }
    public void setPrice(String price) { this.price = price; }
    public void setTrainType(String trainType) { this.trainType = trainType; }
} 