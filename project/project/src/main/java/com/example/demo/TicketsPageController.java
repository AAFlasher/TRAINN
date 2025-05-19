package com.example.demo;

import com.example.demo.Singelton.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketsPageController {
    @FXML
    private TableView<Ticket> ticketsTable;
    @FXML
    private TableColumn<Ticket, String> ticketIdColumn;
    @FXML
    private TableColumn<Ticket, String> trainNumberColumn;
    @FXML
    private TableColumn<Ticket, String> fromColumn;
    @FXML
    private TableColumn<Ticket, String> toColumn;
    @FXML
    private TableColumn<Ticket, String> dateColumn;
    @FXML
    private TableColumn<Ticket, String> timeColumn;
    @FXML
    private TableColumn<Ticket, String> statusColumn;
    @FXML
    private TableColumn<Ticket, String> typeColumn;
    @FXML
    private TableColumn<Ticket, String> priceColumn;
    @FXML
    private TableColumn<Ticket, String> trainTypeColumn;
    @FXML
    private Button backButton;
    @FXML
    private Button cancelTicketButton;
    @FXML
    private Button printTicketButton;
    @FXML
    private Label userIdLabel;
    @FXML
    private ComboBox<String> statusComboBox;

    private ObservableList<Ticket> purchasedTickets = FXCollections.observableArrayList();
    private int userId;

    public void setUserId(int userId) {
        System.out.println("Setting userId: " + userId); // Debug log
        this.userId = userId;
        userIdLabel.setText("User ID: " + userId); // Display user ID
        loadTickets(); // Load tickets when userId is set
    }

    @FXML
    public void initialize() {
        System.out.println("Initializing TicketsPageController"); // Debug log
        
        // Initialize status ComboBox
        statusComboBox.getItems().addAll("Active", "Cancelled", "Completed", "Pending");
        statusComboBox.setValue("Active"); // Default value
        
        // Add listener for status changes
        statusComboBox.setOnAction(e -> {
            Ticket selectedTicket = ticketsTable.getSelectionModel().getSelectedItem();
            if (selectedTicket != null) {
                updateTicketStatus(selectedTicket, statusComboBox.getValue());
            }
        });
        
        // Initialize table columns
        ticketIdColumn.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        trainNumberColumn.setCellValueFactory(new PropertyValueFactory<>("trainNumber"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        trainTypeColumn.setCellValueFactory(new PropertyValueFactory<>("trainType"));

        // Set the items to the table
        ticketsTable.setItems(purchasedTickets);

        
        // Add a listener to enable/disable buttons based on selection
        ticketsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean hasSelection = newSelection != null;
            cancelTicketButton.setDisable(!hasSelection);
            printTicketButton.setDisable(!hasSelection);
            statusComboBox.setDisable(!hasSelection);
            
            if (hasSelection) {
                statusComboBox.setValue(newSelection.getStatus());
            }
        });
    }

    private void updateTicketStatus(Ticket ticket, String newStatus) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String sql = "UPDATE tickets SET status = ? WHERE ticket_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newStatus);
                stmt.setInt(2, Integer.parseInt(ticket.getTicketId()));
                stmt.executeUpdate();
                
                // Update the ticket status in the table
                ticket.setStatus(newStatus);
                ticketsTable.refresh();
                
                showSuccess("Success", "Ticket status updated to " + newStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database Error", "Failed to update ticket status. Please try again.");
        }
    }

    private void loadTickets() {
        if (userId <= 0) {
            System.out.println("Invalid userId: " + userId); // Debug log
            return;
        }

        System.out.println("Loading tickets for userId: " + userId); // Debug log
        purchasedTickets.clear(); // Clear existing tickets

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String sql = "SELECT * FROM tickets WHERE user_id = ? ORDER BY journey_date DESC, created_at DESC";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();

                int count = 0;
                while (rs.next()) {
                    count++;
                    Ticket ticket = new Ticket(
                        String.valueOf(rs.getInt("ticket_id")),
                        rs.getString("train_number"),
                        rs.getString("from_station"),
                        rs.getString("to_station"),
                        rs.getDate("journey_date").toString(),
                        rs.getString("departure_time"),
                        rs.getString("status"),
                        rs.getString("ticket_type"),
                        String.format("%.2f EGP", rs.getDouble("price")),
                        rs.getString("train_type")
                    );
                    purchasedTickets.add(ticket);
                }
                System.out.println("Loaded " + count + " tickets"); // Debug log
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database Error", "Failed to load tickets. Please try again.");
        }
    }

    @FXML
    protected void onBackButtonClick() {
        try {
            navigateToHome();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Navigation Error", "Could not return to home page. Please try again.");
        }
    }

    @FXML
    protected void onCancelTicketClick() {
        Ticket selectedTicket = ticketsTable.getSelectionModel().getSelectedItem();
        if (selectedTicket != null) {
            if (selectedTicket.getStatus().equals("CANCELLED")) {
                showError("Invalid Action", "This ticket is already cancelled.");
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cancel Ticket");
            alert.setHeaderText("Cancel Ticket Confirmation");
            alert.setContentText("Are you sure you want to cancel this ticket?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                updateTicketStatus(selectedTicket, "CANCELLED");
            }
        } else {
            showError("No Selection", "Please select a ticket to cancel.");
        }
    }

    @FXML
    protected void onPrintTicketClick() {
        Ticket selectedTicket = ticketsTable.getSelectionModel().getSelectedItem();
        if (selectedTicket != null) {
            if (selectedTicket.getStatus().equals("CANCELLED")) {
                showError("Invalid Action", "Cannot print a cancelled ticket.");
                return;
            }

            // Show ticket details in a dialog
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ticket Details");
            alert.setHeaderText("Your Ticket Information");
            alert.setContentText(String.format(
                "Ticket ID: %s\nTrain Number: %s\nFrom: %s\nTo: %s\nDate: %s\nTime: %s\nType: %s\nPrice: %s\nTrain Type: %s\nStatus: %s",
                selectedTicket.getTicketId(),
                selectedTicket.getTrainNumber(),
                selectedTicket.getFrom(),
                selectedTicket.getTo(),
                selectedTicket.getDate(),
                selectedTicket.getTime(),
                selectedTicket.getType(),
                selectedTicket.getPrice(),
                selectedTicket.getTrainType(),
                selectedTicket.getStatus()
            ));
            alert.showAndWait();
        } else {
            showError("No Selection", "Please select a ticket to print.");
        }
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showSuccess(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void navigateToHome() throws IOException {
        if (userId <= 0) {
            showError("Error", "User ID not set. Please log in again.");
            navigateToLogin();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/HomePage.fxml"));
        Parent root = loader.load();

        HomePageController controller = loader.getController();
        controller.setUserId(userId);

        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void navigateToLogin() throws IOException {
        userId = 0; // Clear the user ID before navigating to login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/LoginPage.fxml"));
        Parent root = loader.load();
        
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
} 