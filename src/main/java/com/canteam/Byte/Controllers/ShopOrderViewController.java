package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.OrderModel;
import com.canteam.Byte.Models.UserModel;
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

public class ShopOrderViewController implements Initializable {

    @FXML
    private Button closeBtn;

    @FXML
    private Button confirmOrderBtn;

    @FXML
    private Label deliveryFeeLabel;

    @FXML
    private Button denyOrderBtn;

    @FXML
    private ScrollPane instructionScrollPane;

    @FXML
    private ScrollPane itemScrollPane;

    @FXML
    private Label modePaymentLabel;

    @FXML
    private GridPane orderInstructions;

    @FXML
    private GridPane orderItemGrid;

    @FXML
    private ImageView statusBar;

    @FXML
    private Label subtotalLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private Label orderConfirmed;

    @FXML
    private Label orderCancelled;

    @FXML
    private Label contactNumLabel;

    @FXML
    private Label deliveryAddressLabel;

    @FXML
    private Label fullNameLabel;

    @FXML
    private Label orderNumLabel;

    @FXML
    private Label userNameLabel;

    private PageNavigator pageNavigator = new PageNavigator();
    private static int orderNumber;

    @FXML
    void onCloseBtnClicked(ActionEvent event) {
        pageNavigator.navigateToPage(closeBtn, "ShopOrderList");
    }

    @FXML
    void onConfirmOrder(ActionEvent event) {
        denyOrderBtn.setVisible(false);
        confirmOrderBtn.setVisible(false);
        orderConfirmed.setVisible(true);
        OrderModel.updateOrderStatus(orderNumber, "Confirmed");

    }

    @FXML
    void onDenyOrder(ActionEvent event) {
        denyOrderBtn.setVisible(false);
        confirmOrderBtn.setVisible(false);
        orderCancelled.setVisible(true);
        OrderModel.updateOrderStatus(orderNumber, "Cancelled");
        OrderModel.completeOrder(orderNumber);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Objects.equals(OrderModel.getOrderStatus(orderNumber), "Confirmed")) {
            confirmOrderBtn.setVisible(false);
            denyOrderBtn.setVisible(false);
            orderConfirmed.setVisible(true);
        }
        ArrayList<Document> shopOrderList = OrderModel.getStoreOrders(UserModel.getUserName());
        Document order = new Document();

        for (Document doc : shopOrderList) {
            if (doc.containsKey("Order Number") && doc.get("Order Number").equals(orderNumber)) {
                order = doc;
                break;
            }
        }
        Document cart = (Document) order.get("Cart");

        // Load content to grid pane
        int i = 0;
        for (String key : cart.keySet()) {
            // Column 0 = Quantity + Item Name
            // Column 1 = Price
            // Create labels
            Document itemInfo = (Document) cart.get(key);
            Label quantityLabel = new Label(itemInfo.get("Quantity").toString()+"×");
            Label itemNameLabel = new Label(itemInfo.get("Name").toString());
            Label priceLabel = new Label("PHP "+itemInfo.get("Total Price").toString()+".00");

            orderItemGrid.add(quantityLabel, 0, i);
            orderItemGrid.add(itemNameLabel, 1, i);
            orderItemGrid.add(priceLabel, 2, i);

            // Add instructions, if there are, to the grid pane
            String itemInstruct = itemInfo.get("Instructions").toString();
            if (!itemInstruct.isEmpty()) {
                Label itemInstructionsLabel = new Label(itemInfo.get("Instructions").toString());
                orderInstructions.add(itemNameLabel, 0, i);
                orderInstructions.add(quantityLabel, 1, i);
            }
            i++;
        }
        fullNameLabel.setText(order.getString("Full Name"));
        userNameLabel.setText(order.getString("UserName"));
        contactNumLabel.setText(order.getString("Contact"));
        orderNumLabel.setText(String.valueOf(order.getInteger("Order Number")));
        deliveryAddressLabel.setText(order.getString("Address"));
        modePaymentLabel.setText(order.getString("Mode of Payment"));
        subtotalLabel.setText("PHP "+order.getInteger("Subtotal").toString()+".00");
        deliveryFeeLabel.setText("PHP 20.00");
        totalLabel.setText("PHP "+order.getInteger("Total Price of Order").toString()+".00");
    }

    public static void setOrderNumber(int inputOrderNumber) {
        orderNumber = inputOrderNumber;

    }
}
