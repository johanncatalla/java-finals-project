
package com.canteam.Byte.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class UserOrderQueueController {

    @FXML
    private Button btnViewOrder;

    @FXML
    private AnchorPane orderContainer;

    @FXML
    private Label txtOderNumber;

    @FXML
    private Label txtOrderItems;

    @FXML
    private Label txtOrderShop;

    @FXML
    private Label txtOrderStatus;

    @FXML
    void onViewOrder(ActionEvent event) {

    }

    public void setData(String orderItems, String orderShop, String orderStatus, String orderNum) {
        txtOrderItems.setText(orderItems);
        txtOrderShop.setText(orderShop);
        txtOrderStatus.setText(orderStatus);
        txtOderNumber.setText(orderNum);

    }

}

