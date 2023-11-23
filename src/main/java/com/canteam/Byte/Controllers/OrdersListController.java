package com.canteam.Byte.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import com.canteam.Byte.Controllers.PageNavigator;

public class OrdersListController {

    @FXML
    private Button closeOrdersButton;

    @FXML
    private GridPane gridOrders;
    private PageNavigator pageNavigator = new PageNavigator();

    public void onCloseOrdersButtonClick() {
        pageNavigator.backToPage(closeOrdersButton);
    }
}
