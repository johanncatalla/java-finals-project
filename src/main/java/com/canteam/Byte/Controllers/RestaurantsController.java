package com.canteam.Byte.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class RestaurantsController implements Initializable {
    @FXML
    private ImageView statusBar;

    private Draggable draggable = new Draggable();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up window dragging
        draggable.makeWindowDraggable(statusBar);

    }
}
