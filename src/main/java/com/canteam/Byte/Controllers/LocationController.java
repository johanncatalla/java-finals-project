package com.canteam.Byte.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import com.canteam.Byte.Controllers.PageNavigator;

public class LocationController {

    @FXML
    private Button backButton;

    @FXML
    private TextField contactField;

    @FXML
    private TextField landmarkField;

    @FXML
    private ImageView mapView;

    @FXML
    private TextField moreDetailsField;

    @FXML
    private Button saveChangesButton;

    @FXML
    void onBackButton(ActionEvent event) {

    }

    @FXML
    void onSaveChangesButtonClick(ActionEvent event) {
        PageNavigator pageNavigator = new PageNavigator();
        pageNavigator.backToPage(backButton);
    }

}
