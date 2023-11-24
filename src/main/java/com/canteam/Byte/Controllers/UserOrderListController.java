package com.canteam.Byte.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

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
