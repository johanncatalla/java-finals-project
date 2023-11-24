package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.canteam.Byte.Controllers.PageNavigator;

import java.net.URL;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable {

    @FXML
    private Button closeProfileButton;

    @FXML
    private TextField emailField;

    @FXML
    private TextField fullNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label profileIcon;

    @FXML
    private Button saveButton;
    private PageNavigator pageNavigator = new PageNavigator();

    @FXML
    void onCloseButton(ActionEvent event) {
        pageNavigator.backToPage(closeProfileButton);
    }

    @FXML
    void onSaveButton(ActionEvent event) {
        // Get new user info
        String newEmail = emailField.getText();
        String newFullName = fullNameField.getText();
        String newPassword = passwordField.getText();

        // Update user info in database
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set the prompt text for the text fields by current user info
        profileIcon.setText(UserModel.getFullName().substring(0, 1));

        emailField.setPromptText(UserModel.getEmail());
        fullNameField.setPromptText(UserModel.getFullName());
        passwordField.setPromptText(UserModel.getUserPassword());
    }
}
