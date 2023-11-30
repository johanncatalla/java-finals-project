package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.OrderModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class RiderOrderQueueController {

    @FXML
    private Button btnDeliverOrder;

    @FXML
    private AnchorPane orderContainer;

    @FXML
    private Label txtAddress;

    @FXML
    private Label txtContact;

    @FXML
    private Label txtName;

    @FXML
    private Label txtOrderNum;

    @FXML
    private Label txtShopName;

    @FXML
    private Label pickedUpTxt;

    private PageNavigator pageNavigator = new PageNavigator();

    @FXML
    void onDeliver(ActionEvent event) {
        RiderOrderViewController.setOrderNumber(Integer.parseInt(txtOrderNum.getText()));
        pageNavigator.navigateToPage(btnDeliverOrder, "RiderOrderView");
    }

    void setData(String fullname, String contact, String address, String orderNumber, String shop) {
        this.txtName.setText(fullname);
        this.txtContact.setText(contact);
        this.txtAddress.setText(address);
        this.txtOrderNum.setText(orderNumber);
        this.txtShopName.setText(shop);
        if (Objects.equals(OrderModel.getOrderStatus(Integer.valueOf(txtOrderNum.getText())), "Picked-up")) {
            pickedUpTxt.setVisible(true);
        }
    }

}
