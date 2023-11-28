package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.AbstractFolder.OrderModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.bson.Document;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class RiderOrderViewController implements Initializable {

    @FXML
    private Button closeBtn;

    @FXML
    private Label contactNoLabel;

    @FXML
    private Label deliveryAddressLabel;

    @FXML
    private Label deliveryFeeLabel;

    @FXML
    private ScrollPane itemScrollPane;

    @FXML
    private Label landmarkLabel;

    @FXML
    private Label modePaymentLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label orderDelivered;

    @FXML
    private Label orderPickedUp;

    @FXML
    private Button orderDeliveredBtn;

    @FXML
    private GridPane orderItemGrid;

    @FXML
    private Label orderNoLabel;

    @FXML
    private Button pickUpOrderBtn;

    @FXML
    private Label shopNameLabel;

    @FXML
    private ImageView statusBar;

    @FXML
    private Label subtotalLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private Label usernameLabel;
    private static int orderNumber;

    public static void setOrderNumber(int inputOrderNumber) {
        orderNumber = inputOrderNumber;
    }

    private PageNavigator pageNavigator = new PageNavigator();

    @FXML
    void onCloseBtnClicked(ActionEvent event) {
        pageNavigator.navigateToPage(closeBtn, "RiderOrderList");
    }

    @FXML
    void onOrderedDelivered(ActionEvent event) {
        orderDeliveredBtn.setVisible(false);
        orderDelivered.setVisible(true);
        OrderModel.updateOrderStatus(orderNumber, "Delivered");
        OrderModel.completeOrder(orderNumber);
    }

    @FXML
    void onPickUpOrder(ActionEvent event) {
        pickUpOrderBtn.setVisible(false);
        orderPickedUp.setVisible(true);
        orderDeliveredBtn.setDisable(false);
        OrderModel.updateOrderStatus(orderNumber, "Picked-up");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Objects.equals(OrderModel.getOrderStatus(orderNumber), "Picked-up")) {
            pickUpOrderBtn.setVisible(false);
            orderPickedUp.setVisible(true);
        } else {
            pickUpOrderBtn.setVisible(true);
            orderDeliveredBtn.setDisable(true);
        }

        ArrayList<Document> riderOrderList = OrderModel.getRiderOrders();
        Document order = new Document();

        for (Document doc : riderOrderList) {
            if (doc.containsKey("Order Number") && doc.get("Order Number").equals(orderNumber)) {
                order = doc;
                break;
            }
        }
        Document cart = (Document) order.get("Cart");

        int i = 0;
        for (String key : cart.keySet()) {
            Document itemInfo = (Document) cart.get(key);
            Label quantityLabel = new Label(itemInfo.get("Quantity").toString()+"×");
            Label itemNameLabel = new Label(itemInfo.get("Name").toString());
            Label priceLabel = new Label("PHP "+itemInfo.get("Total Price").toString()+".00");

            orderItemGrid.add(quantityLabel, 0, i);
            orderItemGrid.add(itemNameLabel, 1, i);
            orderItemGrid.add(priceLabel, 2, i);
            i++;
        }
        nameLabel.setText(order.getString("Full Name"));
        usernameLabel.setText(order.getString("UserName"));
        contactNoLabel.setText(order.getString("Contact"));
        orderNoLabel.setText(String.valueOf(order.getInteger("Order Number")));
        shopNameLabel.setText(order.getString("Store"));
        deliveryAddressLabel.setText(order.getString("Address"));
        modePaymentLabel.setText(order.getString("Mode of Payment"));
        subtotalLabel.setText("PHP "+order.getInteger("Subtotal").toString()+".00");
        deliveryFeeLabel.setText("PHP 20.00");
        totalLabel.setText("PHP "+order.getInteger("Total Price of Order").toString()+".00");
    }
}
