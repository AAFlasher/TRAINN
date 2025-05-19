package com.example.demo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
        private Button LoginBTN;
    @FXML
    protected void onButtonLogin() {
        try {
            // Load the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
            AnchorPane root = loader.load();  // Load the FXML into the root pane

            // Create a new scene with the root loaded from FXML
            Scene scene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) LoginBTN.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
