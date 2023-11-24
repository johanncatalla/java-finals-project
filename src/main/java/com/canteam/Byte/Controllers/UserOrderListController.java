package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class UserOrderListController {

    @FXML
    private Button closeOrdersButton;

    @FXML
    private GridPane gridOrders;
    private PageNavigator pageNavigator = new PageNavigator();

    public void onCloseOrdersButtonClick() {
        pageNavigator.backToPage(closeOrdersButton);
    }


}