package com.canteam.Byte.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class UserOrderQueueController {

    @FXML
    private Button btnViewOrder;

    @FXML
    private Label txtOderNumber;

    @FXML
    private Label txtOrderItems;

    @FXML
    private Label txtOrderShop;

    @FXML
    private Label txtOrderStatus;

    @FXML
    private AnchorPane orderContainer;

    public void setData(String orderNum, String orderItems, String orderShop, String orderStatus) {
        txtOderNumber.setText(orderNum);
        txtOrderItems.setText(orderItems);
        txtOrderShop.setText(orderShop);
        txtOrderStatus.setText(orderStatus);
    }


}
