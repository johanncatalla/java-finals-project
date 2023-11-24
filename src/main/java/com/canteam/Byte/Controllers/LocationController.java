package com.canteam.Byte.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import com.canteam.Byte.Controllers.PageNavigator;

import java.net.URL;
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

    private PageNavigator pageNavigator = new PageNavigator();

    private Draggable draggable = new Draggable();

    @FXML
    void onBackButton(ActionEvent event) {
        pageNavigator.backToPage(backButton);
    }

    @FXML
    void onSaveChangesButtonClick(ActionEvent event) {
        PageNavigator pageNavigator = new PageNavigator();
        pageNavigator.backToPage(backButton);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        draggable.makeWindowDraggable(statusBar);
    }
}
