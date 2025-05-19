package com.example.demo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Text SignUP;
    @FXML
    private Button BackButton;
    @FXML
    private Button LoginButton;


    @FXML
    public void onTextFieldSignup() {

        try {
            // Load the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUP.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) SignUP.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onBackButton() {
        try {
            // Load the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
            AnchorPane root = loader.load();  // Load the FXML into the root pane

            // Create a new scene with the root loaded from FXML
            Scene scene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) BackButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onButtonLogin() {
        try {
            // Load the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            AnchorPane root = loader.load();  // Load the FXML into the root pane

            // Create a new scene with the root loaded from FXML
            Scene scene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) LoginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}