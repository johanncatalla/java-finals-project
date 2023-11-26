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
import javafx.scene.image.ImageView;

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

    @FXML
    private ImageView statusBar;
    private PageNavigator pageNavigator = new PageNavigator();
    private Draggable draggable = new Draggable();

    @FXML
    private Label alertLabel;

    @FXML
    void onCloseButton(ActionEvent event) {
        alertLabel.setVisible(false);
        pageNavigator.backToPage(closeProfileButton);
    }

    @FXML
    void onSaveButton(ActionEvent event) {;
        // check if there is an empty field
        if (emailField.getText().isEmpty() || fullNameField.getText().isEmpty() || passwordField.getText().isEmpty()){
            alertLabel.setText("Please fill in all fields");
            alertLabel.setStyle(
                    "-fx-text-fill: red;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 15px;");
            alertLabel.setVisible(true);
            return;
        }
        alertLabel.setVisible(true);
        alertLabel.setText("Changes saved");
        alertLabel.setStyle(
                "-fx-text-fill: green;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 15px;");
        // Get new user info
        String newEmail = emailField.getText();
        String newFullName = fullNameField.getText();
        String newPassword = passwordField.getText();

        UserModel.setEmail(newEmail);
        UserModel.setFullName(newFullName);
        UserModel.setUserPassword(newPassword);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alertLabel.setVisible(false);
        // Make draggable
        draggable.makeWindowDraggable(statusBar);

        // Set text for fields
        profileIcon.setText(UserModel.getFullName().substring(0, 1).toUpperCase());
        emailField.setText(UserModel.getEmail());
        fullNameField.setText(UserModel.getFullName());
    }
}
