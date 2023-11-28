package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.AbstractFolder.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LocationController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private TextField contactField;

    @FXML
    private TextField landmarkField;

    @FXML
    private ImageView mapView, statusBar;

    @FXML
    private TextArea moreDetailsField;

    @FXML
    private Button saveChangesButton;

    @FXML
    private Label newUserAlert;

    private PageNavigator pageNavigator = new PageNavigator();

    private Draggable draggable = new Draggable();

    @FXML
    void onBackButton(ActionEvent event) {
        if (UserModel.isNewUser()){
            newUserAlert.setVisible(true);
        } else {
            pageNavigator.backToPage(backButton);
        }
    }

    @FXML
    void onSaveChangesButtonClick(ActionEvent event) {
        if (contactField.getText().isEmpty() || landmarkField.getText().isEmpty() || moreDetailsField.getText().isEmpty()){
            newUserAlert.setText("Please fill in all fields");
            newUserAlert.setVisible(true);
            return;
        }
        UserModel.setUserAddress(new ArrayList<String>(){{
            add(landmarkField.getText());
            add(moreDetailsField.getText());
        }});
        UserModel.setUserContact(contactField.getText());

        // Alert success green text
        newUserAlert.setStyle("-fx-text-fill: #1e9a1e");
        newUserAlert.setText("Changes saved successfully");
        newUserAlert.setVisible(true);

        if (UserModel.isNewUser()){
            UserModel.setNewOldUser(false);
            pageNavigator.navigateToPage(saveChangesButton, "Home");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        draggable.makeWindowDraggable(statusBar);
        if (!UserModel.isNewUser()) {
            contactField.setText(UserModel.getUserContact());
            landmarkField.setText(UserModel.getLandmark());
            moreDetailsField.setText(UserModel.getAddressDetails());
        }
    }
}
