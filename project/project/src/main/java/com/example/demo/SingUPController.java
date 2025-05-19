package com.example.demo;

import com.example.demo.Singelton.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class SingUPController {
    @FXML
    private TextField FirstName;
    
    @FXML
    private TextField LastName;
    
    @FXML
    private TextField Email;
    
    @FXML
    private TextField Password;
    
    @FXML
    private TextField Phone;
    
    @FXML
    private TextField ID;
    
    @FXML
    private TextField Username;
    
    @FXML
    private Button SignUp;
    
    @FXML
    private Button Back;
    
    @FXML
    private Text statusText;

    @FXML
    private void onSignUpButtonClick() throws IOException {
        String username = Username.getText().trim();
        String firstName = FirstName.getText().trim();
        String lastName = LastName.getText().trim();
        String email = Email.getText().trim();
        String password = Password.getText().trim();
        String phone = Phone.getText().trim();
        String nationalId = ID.getText().trim();

        // Validate input
        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || 
            password.isEmpty() || phone.isEmpty() || nationalId.isEmpty()) {
            statusText.setText("Please fill in all fields");
            statusText.setStyle("-fx-fill: red;");
            return;
        }
        if (!isValidEmail(email)) {
            statusText.setText("Invalid email format");
            statusText.setStyle("-fx-fill: red;");
            return;
        }
        if (!phone.matches("^01[0125][0-9]{8}$")) {
            statusText.setText("Invalid phone number format");
            statusText.setStyle("-fx-fill: red;");
            return;
        }
        if (!nationalId.matches("\\d{14}")) {
            statusText.setText("National ID must be 14 digits");
            statusText.setStyle("-fx-fill: red;");
            return;
        }
        if (password.length() < 6) {
            statusText.setText("Password must be at least 6 characters");
            statusText.setStyle("-fx-fill: red;");
            return;
        }

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            // Check for existing username
            String checkUserSql = "SELECT * FROM users WHERE username = ? OR email = ? OR national_id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkUserSql)) {
                checkStmt.setString(1, username);
                checkStmt.setString(2, email);
                checkStmt.setString(3, nationalId);
                ResultSet rs = checkStmt.executeQuery();
                
                if (rs.next()) {
                    if (rs.getString("username").equals(username)) {
                        statusText.setText("Username already registered");
                    } else if (rs.getString("email").equals(email)) {
                        statusText.setText("Email already registered");
                    } else {
                        statusText.setText("National ID already registered");
                    }
                    statusText.setStyle("-fx-fill: red;");
                    return;
                }
            }

            // Insert new user
            String insertSql = "INSERT INTO users (username, first_name, last_name, email, password, phone, national_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, firstName);
                insertStmt.setString(3, lastName);
                insertStmt.setString(4, email);
                insertStmt.setString(5, password);
                insertStmt.setString(6, phone);
                insertStmt.setString(7, nationalId);
                insertStmt.executeUpdate();
            }

            statusText.setText("Registration successful! Redirecting to login...");
            statusText.setStyle("-fx-fill: green;");
            clearFields();
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    javafx.application.Platform.runLater(this::navigateToLogin);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (SQLException e) {
            e.printStackTrace();
            statusText.setText("Database error occurred");
            statusText.setStyle("-fx-fill: red;");
        }
    }

    private void clearFields() {
        Username.clear();
        FirstName.clear();
        LastName.clear();
        Email.clear();
        Password.clear();
        Phone.clear();
        ID.clear();
    }

    @FXML
    private void onBackButton() throws IOException {
        navigateToLogin();
    }

    private void navigateToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/LoginPage.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) Back.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
