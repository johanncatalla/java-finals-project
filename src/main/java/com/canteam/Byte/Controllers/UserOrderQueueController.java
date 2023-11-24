package com.canteam.Byte.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class UserOrderQueueController implements Initializable {

    @FXML
    private Button btnViewOrder;

    @FXML
    private Label txtOrderItems;

    @FXML
    private Label txtOrderShop;

    @FXML
    private Label txtOrderStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // get data

    }
}
