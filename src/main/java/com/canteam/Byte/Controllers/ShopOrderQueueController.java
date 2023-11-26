package com.canteam.Byte.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ShopOrderQueueController {

    @FXML
    private Label txtAddress;

    @FXML
    private Label txtContact;

    @FXML
    private Label txtCustomerName;

    @FXML
    private Label txtOrderNum;

    @FXML
    private Button viewOrderBtn;

    @FXML
    void onViewOrder(ActionEvent event) {

    }

    public void setData(String name, String address, String contact, String orderNum) {
        txtCustomerName.setText(name);
        txtAddress.setText(address);
        txtContact.setText(contact);
        txtOrderNum.setText(orderNum);
    }

}
