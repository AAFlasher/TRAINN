package com.example.demo;

import com.example.demo.Singelton.DatabaseConnection;
import com.example.demo.Strategy.PaymentStrategy;
import com.example.demo.Strategy.CreditCardStrategy;
import com.example.demo.Strategy.DebitCardStrategy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentPageController {
    @FXML
    private TextField cardNumberField;
    
    @FXML
    private TextField cardHolderField;
    
    @FXML
    private ChoiceBox<String> expiryMonth;
    
    @FXML
    private ChoiceBox<String> expiryYear;
    
    @FXML
    private TextField cvvField;
    
    @FXML
    private Label fromLabel;
    
    @FXML
    private Label toLabel;
    
    @FXML
    private Label dateLabel;
    
    @FXML
    private Label typeLabel;
    
    @FXML
    private Label ticketsLabel;
    
    @FXML
    private Label priceLabel;
    
    @FXML
    private Label trainNumberLabel;
    
    @FXML
    private Label departureTimeLabel;
    
    @FXML
    private Label trainTypeLabel;
    
    @FXML
    private Label statusLabel;

    @FXML
    private ChoiceBox<String> paymentMethodChoice;

    private int userId;
    private int ticketId;
    private String from;
    private String to;
    private String date;
    private String type;
    private String tickets;
    private String price;
    private String trainNumber;
    private String departureTime;
    private String trainType;
    private PaymentStrategy paymentStrategy;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public void setTicketDetails(String from, String to, String date, String type,
                               String tickets, String price, String trainNumber, 
                               String departureTime, String trainType) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.type = type;
        this.tickets = tickets;
        this.price = price;
        this.trainNumber = trainNumber;
        this.departureTime = departureTime;
        this.trainType = trainType;

        fromLabel.setText("From: " + from);
        toLabel.setText("To: " + to);
        dateLabel.setText("Date: " + date);
        typeLabel.setText("Type: " + type);
        ticketsLabel.setText("Tickets: " + tickets);
        double priceValue = Double.parseDouble(price);
        priceLabel.setText(String.format("Price: %.2f EGP", priceValue));
        trainNumberLabel.setText("Train Number: " + trainNumber);
        departureTimeLabel.setText("Departure Time: " + departureTime);
        trainTypeLabel.setText("Train Type: " + trainType);
    }

    @FXML
    public void initialize() {
        // Initialize expiry month and year
        expiryMonth.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        expiryYear.getItems().addAll("2025", "2026", "2027", "2028", "2029", "2030");

        // Initialize payment method choice
        paymentMethodChoice.getItems().addAll("Credit Card", "Debit Card");
        paymentMethodChoice.setValue("Credit Card");
        paymentMethodChoice.setOnAction(e -> setPaymentStrategy());

        // Add auto-space formatting for card number
        cardNumberField.textProperty().addListener((obs, oldText, newText) -> {
            String digits = newText.replaceAll("\\D", "");
            StringBuilder formatted = new StringBuilder();
            for (int i = 0; i < digits.length(); i++) {
                if (i > 0 && i % 4 == 0) {
                    formatted.append(" ");
                }
                formatted.append(digits.charAt(i));
            }
            if (!newText.equals(formatted.toString())) {
                cardNumberField.setText(formatted.toString());
                cardNumberField.positionCaret(formatted.length());
            }
        });

        // Set default payment strategy
        setPaymentStrategy();
    }

    private void setPaymentStrategy() {
        String selectedMethod = paymentMethodChoice.getValue();
        if ("Credit Card".equals(selectedMethod)) {
            paymentStrategy = new CreditCardStrategy();
        } else if ("Debit Card".equals(selectedMethod)) {
            paymentStrategy = new DebitCardStrategy();
        }
    }

    @FXML
    private void onConfirmButtonClick() throws IOException {
        if (cardNumberField.getText().isEmpty() || cardHolderField.getText().isEmpty() ||
            expiryMonth.getValue() == null || expiryYear.getValue() == null || cvvField.getText().isEmpty()) {
            statusLabel.setText("Please fill in all payment details");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String cardNumber = cardNumberField.getText().replaceAll("\\s+", "");
        if (cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            statusLabel.setText("Invalid card number format");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String cvv = cvvField.getText();
        if (cvv.length() != 3 || !cvv.matches("\\d+")) {
            statusLabel.setText("Invalid CVV format");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String priceText = priceLabel.getText();
        double amount;
        try {
            priceText = priceText.replaceAll("[^0-9.]", "");
            amount = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid price format");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Process payment using the selected strategy
        boolean success = paymentStrategy.processPayment(amount, cardNumber, cardHolderField.getText());

        if (success) {
            try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
                String sql = "INSERT INTO payments (ticket_id, user_id, card_number, card_holder, expiry_month, expiry_year, amount, payment_status, payment_method) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, ticketId);
                    stmt.setInt(2, userId);
                    stmt.setString(3, cardNumber);
                    stmt.setString(4, cardHolderField.getText());
                    stmt.setString(5, expiryMonth.getValue());
                    stmt.setString(6, expiryYear.getValue());
                    stmt.setDouble(7, amount);
                    stmt.setString(8, "SUCCESS");
                    stmt.setString(9, paymentStrategy.getPaymentMethodName());
                    stmt.executeUpdate();
                }
                
                statusLabel.setText("Payment successful!");
                statusLabel.setStyle("-fx-text-fill: green;");
                navigateToConfirmation();
            } catch (SQLException e) {
                e.printStackTrace();
                statusLabel.setText("Database error occurred");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        } else {
            statusLabel.setText("Payment failed. Please check your card details and try again.");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void onBackButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/HomePage.fxml"));
        Parent root = loader.load();
        
        HomePageController controller = loader.getController();
        controller.setUserId(userId);
        
        Stage stage = (Stage) cardNumberField.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void navigateToConfirmation() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/ConfirmationPage.fxml"));
        Parent root = loader.load();
        
        Stage stage = (Stage) cardNumberField.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
