package com.example.demo;

import com.example.demo.Singelton.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPageController {
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Button signupButton;
    
    @FXML
    private Label statusLabel;

    @FXML
    public void initialize() {
        // Test database connection when the login page loads
        if (!DatabaseConnection.getInstance().testConnection()) {
            statusLabel.setText("Database connection failed. Please check your database settings.");
            statusLabel.setStyle("-fx-text-fill: red;");
        }

        // Add Enter key handler for username field
        usernameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });

        // Add Enter key handler for password field
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onLoginButtonClick();
            }
        });
    }

    @FXML
    private void onLoginButtonClick() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both username and password");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String sql = "SELECT id FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int userId = rs.getInt("id");
                    statusLabel.setText("Login successful!");
                    statusLabel.setStyle("-fx-text-fill: green;");
                    navigateToHome(userId);
                } else {
                    statusLabel.setText("Invalid username or password");
                    statusLabel.setStyle("-fx-text-fill: red;");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Database error occurred");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void onSignupButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/SignUP.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) signupButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading signup page:");
            e.printStackTrace();
        }
    }

    private void navigateToHome(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/HomePage.fxml"));
            Parent root = loader.load();
            
            HomePageController controller = loader.getController();
            controller.setUserId(userId);
            
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading home page:");
            e.printStackTrace();
        }
    }
} 