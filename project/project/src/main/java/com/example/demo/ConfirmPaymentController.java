package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.io.IOException;

public class ConfirmPaymentController {
    @FXML
    private Button returnButton;

    @FXML
    protected void onReturnToHomeClick() {
        try {
            // Navigate back to home page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) returnButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Show error message to user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Navigation Error");
            alert.setContentText("Could not return to home page. Please try again.");
            alert.showAndWait();
        }
    }
}
