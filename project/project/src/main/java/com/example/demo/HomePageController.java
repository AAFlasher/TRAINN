package com.example.demo;

import com.example.demo.Singelton.DatabaseConnection;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePageController {
    private static int currentUserId; // Static variable to store user ID across pages
    
    @FXML
    private ComboBox<String> FROM;
    
    @FXML
    private ComboBox<String> TO;
    
    @FXML
    private DatePicker DatePicker;
    
    @FXML
    private ChoiceBox<String> No;
    
    @FXML
    private ChoiceBox<String> Type;
    
    @FXML
    private ChoiceBox<String> TrainType;
    
    @FXML
    private ChoiceBox<String> Status;
    
    @FXML
    private Button ShowTicketsBtn;
    
    @FXML
    private Button Payment;
    
    @FXML
    private Button Logout;

    @FXML
    private Label pricePerTicketLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private TableView<TrainInfo> trainTable;
    
    @FXML
    private TableColumn<TrainInfo, String> trainNumberColumn;
    
    @FXML
    private TableColumn<TrainInfo, String> fromColumn;
    
    @FXML
    private TableColumn<TrainInfo, String> toColumn;
    
    @FXML
    private TableColumn<TrainInfo, String> departureTimeColumn;
    
    @FXML
    private TableColumn<TrainInfo, String> trainTypeColumn;

    private static final double BASE_PRICE_PER_KM = 0.5; // Base price per kilometer in EGP
    private final Map<String, Double> priceMultipliers = new HashMap<>();
    private final Map<String, Double> trainTypeMultipliers = new HashMap<>();
    private final Map<String, Map<String, Double>> distances = new HashMap<>();
    private TrainInfo selectedTrain;

    public static class TrainInfo {
        private final String trainNumber;
        private final String from;
        private final String to;
        private final String departureTime;
        private final String trainType;

        public TrainInfo(String trainNumber, String from, String to, String departureTime, String trainType) {
            this.trainNumber = trainNumber;
            this.from = from;
            this.to = to;
            this.departureTime = departureTime;
            this.trainType = trainType;
        }

        public String getTrainNumber() { return trainNumber; }
        public String getFrom() { return from; }
        public String getTo() { return to; }
        public String getDepartureTime() { return departureTime; }
        public String getTrainType() { return trainType; }
    }

    @FXML
    public void initialize() {
        System.out.println("HomePageController: Initializing"); // Debug log
        
        // Load unique stations from trains table
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            // Get unique from_stations
            String fromSql = "SELECT DISTINCT from_station FROM trains ORDER BY from_station";
            try (PreparedStatement stmt = conn.prepareStatement(fromSql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String station = rs.getString("from_station");
                    FROM.getItems().add(station);
                    System.out.println("Added FROM station: " + station);
                }
            }
            
            // Get unique to_stations
            String toSql = "SELECT DISTINCT to_station FROM trains ORDER BY to_station";
            try (PreparedStatement stmt = conn.prepareStatement(toSql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String station = rs.getString("to_station");
                    TO.getItems().add(station);
                    System.out.println("Added TO station: " + station);
                }
            }
            
            System.out.println("Loaded " + FROM.getItems().size() + " FROM stations");
            System.out.println("Loaded " + TO.getItems().size() + " TO stations");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to load stations. Please try again.");
        }
        
        // Initialize choice boxes
        No.getItems().addAll("1", "2", "3", "4", "5");
        No.setValue("1");
        
        Type.getItems().addAll("Adult", "Child", "Senior", "Student");
        Type.setValue("Adult");
        
        TrainType.getItems().addAll("Express", "Local", "High-Speed");
        TrainType.setValue("Express");
        
        Status.getItems().addAll("Active", "Pending", "Completed");
        Status.setValue("Active");
        
        // Initialize price multipliers
        priceMultipliers.put("Adult", 1.0);
        priceMultipliers.put("Child", 0.5);
        priceMultipliers.put("Senior", 0.7);
        priceMultipliers.put("Student", 0.8);
        
        trainTypeMultipliers.put("Express", 1.2);
        trainTypeMultipliers.put("Local", 1.0);
        trainTypeMultipliers.put("High-Speed", 1.5);
        
        // Initialize distances
        initializeDistances();
        
        // Add listeners for price updates
        FROM.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("FROM station changed to: " + newVal);
            updatePriceDisplay();
            updateTrainTable();
        });
        TO.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("TO station changed to: " + newVal);
            updatePriceDisplay();
            updateTrainTable();
        });
        Type.valueProperty().addListener((obs, oldVal, newVal) -> updatePriceDisplay());
        No.valueProperty().addListener((obs, oldVal, newVal) -> updatePriceDisplay());
        TrainType.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Train type changed to: " + newVal);
            updatePriceDisplay();
            updateTrainTable();
        });
        
        // Initialize table columns
        trainNumberColumn.setCellValueFactory(new PropertyValueFactory<>("trainNumber"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        trainTypeColumn.setCellValueFactory(new PropertyValueFactory<>("trainType"));
        
        // Add selection listener to train table
        trainTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedTrain = newSelection;
            if (newSelection != null) {
                System.out.println("Selected train: " + newSelection.getTrainNumber());
            }
        });
    }

    private void initializeDistances() {
        // Cairo distances
        Map<String, Double> cairoDistances = new HashMap<>();
        cairoDistances.put("Alexandria", 220.0);
        cairoDistances.put("Giza", 20.0);
        cairoDistances.put("Port Said", 200.0);
        cairoDistances.put("Suez", 120.0);
        cairoDistances.put("Luxor", 650.0);
        cairoDistances.put("Aswan", 900.0);
        cairoDistances.put("Ismailia", 120.0);
        cairoDistances.put("Damietta", 200.0);
        distances.put("Cairo", cairoDistances);

        // Alexandria distances
        Map<String, Double> alexandriaDistances = new HashMap<>();
        alexandriaDistances.put("Cairo", 220.0);
        alexandriaDistances.put("Port Said", 220.0);
        alexandriaDistances.put("Damietta", 180.0);
        distances.put("Alexandria", alexandriaDistances);

        // Luxor distances
        Map<String, Double> luxorDistances = new HashMap<>();
        luxorDistances.put("Cairo", 650.0);
        luxorDistances.put("Aswan", 250.0);
        distances.put("Luxor", luxorDistances);

        // Aswan distances
        Map<String, Double> aswanDistances = new HashMap<>();
        aswanDistances.put("Cairo", 900.0);
        aswanDistances.put("Luxor", 250.0);
        distances.put("Aswan", aswanDistances);

        // Port Said distances
        Map<String, Double> portSaidDistances = new HashMap<>();
        portSaidDistances.put("Cairo", 200.0);
        portSaidDistances.put("Alexandria", 220.0);
        portSaidDistances.put("Ismailia", 80.0);
        distances.put("Port Said", portSaidDistances);

        // Suez distances
        Map<String, Double> suezDistances = new HashMap<>();
        suezDistances.put("Cairo", 120.0);
        suezDistances.put("Ismailia", 80.0);
        distances.put("Suez", suezDistances);
    }

    private double getDistance(String from, String to) {
        // Check if we have the direct distance
        if (distances.containsKey(from) && distances.get(from).containsKey(to)) {
            return distances.get(from).get(to);
        }
        // Check if we have the reverse distance
        if (distances.containsKey(to) && distances.get(to).containsKey(from)) {
            return distances.get(to).get(from);
        }
        // If no distance found, return a default value
        return 100.0;
    }

    private void updateTrainTable() {
        String fromStation = FROM.getValue();
        String toStation = TO.getValue();

        if (fromStation == null || toStation == null) {
            return;
        }

        trainTable.getItems().clear();
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String sql = "SELECT * FROM trains WHERE from_station = ? AND to_station = ? ORDER BY departure_time";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, fromStation);
                stmt.setString(2, toStation);
                
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) {
                    String trainNumber = String.valueOf(rs.getString("train_number"));
                    String from = rs.getString("from_station");
                    String to = rs.getString("to_station");
                    String time = rs.getString("departure_time");
                    String type = rs.getString("train_type");
                    
                    TrainInfo train = new TrainInfo(trainNumber, from, to, time, type);
                    trainTable.getItems().add(train);
                }
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to load trains: " + e.getMessage());
        }
        
        if (trainTable.getItems().isEmpty()) {
            showAlert("No Trains", "No trains found for the selected route.");
        }
    }

    private void updatePriceDisplay() {
        String selectedType = Type.getValue();
        String selectedNumber = No.getValue();
        String selectedTrainType = TrainType.getValue();
        String selectedFrom = FROM.getValue();
        String selectedTo = TO.getValue();
        
        if (selectedType != null && selectedNumber != null && selectedTrainType != null 
            && selectedFrom != null && selectedTo != null) {
            
            // Calculate base price based on distance
            double distance = getDistance(selectedFrom, selectedTo);
            double basePrice = distance * BASE_PRICE_PER_KM;
            
            // Apply multipliers
            double pricePerTicket = basePrice * 
                                  priceMultipliers.get(selectedType) * 
                                  trainTypeMultipliers.get(selectedTrainType);
            
            int numberOfTickets = Integer.parseInt(selectedNumber);
            double totalPrice = pricePerTicket * numberOfTickets;
            
            // Update both price labels with EGP symbol
            pricePerTicketLabel.setText(String.format("Price per ticket: %.2f EGP", pricePerTicket));
            totalPriceLabel.setText(String.format("Total price: %.2f EGP", totalPrice));
        }
    }

    @FXML
    private void onShowTicketsButtonClick() {
        try {
            if (currentUserId <= 0) {
                showAlert("Error", "User ID not set. Please log in again.");
                navigateToLogin();
                return;
            }
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/TicketsPage.fxml"));
            Parent root = loader.load();
            
            TicketsPageController controller = loader.getController();
            controller.setUserId(currentUserId);
            
            Scene scene = new Scene(root);
            Stage stage = (Stage) ShowTicketsBtn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not load tickets page. Please try again.");
        }
    }

    @FXML
    private void onButtonPayment() throws IOException {
        if (selectedTrain == null) {
            showAlert("Error", "Please select a train first");
            return;
        }
        
        if (currentUserId <= 0) {
            showAlert("Error", "User ID not set. Please log in again.");
            return;
        }
        
        // Verify the user exists in the database
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String checkSql = "SELECT id FROM users WHERE id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, currentUserId);
                ResultSet rs = checkStmt.executeQuery();
                if (!rs.next()) {
                    showAlert("Error", "User not found. Please log in again.");
                    navigateToLogin();
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to verify user information. Please try again.");
            return;
        }

        String from = FROM.getValue();
        String to = TO.getValue();
        String date = DatePicker.getValue().toString();
        String type = Type.getValue();
        String tickets = No.getValue();
        String price = String.valueOf(calculatePrice());
        String trainNumber = selectedTrain.getTrainNumber();
        String departureTime = selectedTrain.getDepartureTime();
        String trainType = selectedTrain.getTrainType();
        String status = Status.getValue();

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            // Store ticket in database
            String sql = "INSERT INTO tickets (user_id, from_station, to_station, journey_date, ticket_type, price, train_number, departure_time, train_type, number_of_tickets, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, currentUserId);
                stmt.setString(2, from);
                stmt.setString(3, to);
                stmt.setString(4, date);
                stmt.setString(5, type);
                stmt.setDouble(6, calculatePrice());
                stmt.setString(7, trainNumber);
                stmt.setString(8, departureTime);
                stmt.setString(9, trainType);
                stmt.setInt(10, Integer.parseInt(tickets));
                stmt.setString(11, status);
                stmt.executeUpdate();

                // Get the generated ticket ID
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int ticketId = rs.getInt(1);
                        navigateToPayment(from, to, date, type, tickets, price, trainNumber, departureTime, trainType, ticketId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to store ticket information");
        }
    }

    private void navigateToPayment(String from, String to, String date, String type, String tickets, 
                                 String price, String trainNumber, String departureTime, String trainType, int ticketId) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/PaymentPage.fxml"));
        Parent root = loader.load();
        
        PaymentPageController controller = loader.getController();
        controller.setUserId(currentUserId);
        controller.setTicketId(ticketId);
        controller.setTicketDetails(from, to, date, type, tickets, price, trainNumber, departureTime, trainType);
        
        Stage stage = (Stage) Payment.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private double calculatePrice() {
        String selectedFrom = FROM.getValue();
        String selectedTo = TO.getValue();
        double distance = getDistance(selectedFrom, selectedTo);
        double basePrice = distance * BASE_PRICE_PER_KM;
        String passengerType = Type.getValue();
        String trainType = TrainType.getValue();
        int numberOfTickets = Integer.parseInt(No.getValue());

        // Apply passenger type and train type multipliers
        double passengerMultiplier = priceMultipliers.get(passengerType);
        double trainMultiplier = trainTypeMultipliers.get(trainType);
        double pricePerTicket = basePrice * passengerMultiplier * trainMultiplier;

        // Calculate total price
        return pricePerTicket * numberOfTickets;
    }

    @FXML
    private void onButtonLogout() throws IOException {
        currentUserId = 0; // Clear the user ID before logging out
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/LoginPage.fxml"));
        Parent root = loader.load();
        
        Stage stage = (Stage) Logout.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void navigateToLogin() throws IOException {
        currentUserId = 0; // Clear the user ID before navigating to login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/LoginPage.fxml"));
        Parent root = loader.load();
        
        Stage stage = (Stage) Payment.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setUserId(int userId) {
        currentUserId = userId; // Set the static user ID
    }
}
