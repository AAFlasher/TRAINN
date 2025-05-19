package com.example.demo;

import com.example.demo.Singelton.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupPageController {
    @FXML
    private TextField fullNameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField confirmPasswordField;
    
    @FXML
    private Label statusLabel;
    
    @FXML
    private Button signupButton;
    
    @FXML
    private Hyperlink loginLink;

    @FXML
    public void initialize() {
        // Initialize any required components
    }

    @FXML
    private void onSignupButtonClick() {
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate input
        if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() || 
            password.isEmpty() || confirmPassword.isEmpty()) {
            statusLabel.setText("Please fill in all fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            statusLabel.setText("Passwords do not match");
            return;
        }

        if (!isValidEmail(email)) {
            statusLabel.setText("Please enter a valid email address");
            return;
        }

        // Register the user in the database
        try (java.sql.Connection conn = DatabaseConnection.getInstance().getConnection()) {
            // First check if username already exists
            String checkSql = "SELECT COUNT(*) FROM users WHERE username = ?";
            try (java.sql.PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, username);
                java.sql.ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    statusLabel.setText("Username already exists. Please choose another.");
                    return;
                }
            }
            
            // Insert new user
            String insertSql = "INSERT INTO users (full_name, email, username, password) VALUES (?, ?, ?, ?)";
            try (java.sql.PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, fullName);
                insertStmt.setString(2, email);
                insertStmt.setString(3, username);
                insertStmt.setString(4, password);
                int affectedRows = insertStmt.executeUpdate();
                
                if (affectedRows > 0) {
                    statusLabel.setText("Registration successful! Please log in.");
                    // Navigate to login page after successful registration
                    navigateToLogin();
                } else {
                    statusLabel.setText("Registration failed. Please try again.");
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Database error: " + e.getMessage());
        } catch (IOException e) {
            statusLabel.setText("Error: Could not navigate to login page");
        }
    }

    @FXML
    private void onLoginLinkClick() throws IOException {
        navigateToLogin();
    }

    private void navigateToLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/LoginPage.fxml"));
        Parent root = loader.load();
        
        Stage stage = (Stage) loginLink.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private boolean isValidEmail(String email) {
        // Basic email validation
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
} 