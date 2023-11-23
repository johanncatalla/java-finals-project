package com.canteam.Byte.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.canteam.Byte.Controllers.PageNavigator;

public class UserProfileController {

    @FXML
    private Button closeProfileButton;

    @FXML
    private TextField emailField;

    @FXML
    private TextField fullNameField;

    @FXML
    private PasswordField passwordField;
    private PageNavigator pageNavigator = new PageNavigator();

    @FXML
    void onCloseButton(ActionEvent event) {
        pageNavigator.backToPage(closeProfileButton);
    }

}
