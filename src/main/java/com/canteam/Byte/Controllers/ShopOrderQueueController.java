package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.AbstractFolder.OrderModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ShopOrderQueueController implements Initializable {

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
    private Label orderConfirmed;

    private PageNavigator pageNavigator = new PageNavigator();

    @FXML
    void onViewOrder(ActionEvent event) {
        ShopOrderViewController.setOrderNumber(Integer.parseInt(txtOrderNum.getText()));
        pageNavigator.navigateToPage(viewOrderBtn, "ShopOrderView");
    }


    public void setData(String name, String address, String contact, String orderNum) {
        txtCustomerName.setText(name);
        txtAddress.setText(address);
        txtContact.setText(contact);
        txtOrderNum.setText(orderNum);
        if (Objects.equals(OrderModel.getOrderStatus(Integer.valueOf(txtOrderNum.getText())), "Confirmed")) {
            orderConfirmed.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
