package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.OrderModel;
import com.canteam.Byte.Models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.bson.Document;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserOrderViewController implements Initializable {

    @FXML
    private Button closeBtn;

    @FXML
    private Label confirmedByLabel;

    @FXML
    private Label deliveryAddressLabel;

    @FXML
    private Label deliveryFeeLabel;

    @FXML
    private Label modePaymentLabel;

    @FXML
    private Label orderNumberLabel;

    @FXML
    private Label orderStoreLabel;

    @FXML
    private GridPane ordersGridPane;

    @FXML
    private AnchorPane scrollAnchorPane;

    @FXML
    private ImageView statusBar;

    @FXML
    private Label subtotalLabel;

    @FXML
    private Label totalLabel;
    private static int orderNumber;

    private PageNavigator pageNavigator = new PageNavigator();
    private Draggable draggable = new Draggable();

    @FXML
    void onCloseBtnClicked(ActionEvent event) {
        pageNavigator.navigateToPage(closeBtn, "UserOrderList");
    }

    public static void setOrderNumber(int inputOrderNumber) { orderNumber = inputOrderNumber; }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Make the window draggable
        draggable.makeScrollableY(scrollAnchorPane);
        ArrayList<Document> userOrders = OrderModel.getUserOrders(UserModel.getUserName());
        Document order = new Document();

        for (Document doc : userOrders) {
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

            priceLabel.setAlignment(Pos.CENTER_RIGHT);
            itemNameLabel.wrapTextProperty().setValue(true);
            itemNameLabel.maxHeight(50);

            // Place quantity label and item name in a Hbox
            HBox quantityItemNameHBox = new HBox(quantityLabel, itemNameLabel);
            quantityItemNameHBox.setSpacing(10);
            quantityItemNameHBox.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(itemNameLabel, Priority.ALWAYS);

            // Align price label to the right
            priceLabel.setAlignment(Pos.CENTER_RIGHT);

            // Add Hbox to grid pane
            ordersGridPane.add(quantityItemNameHBox, 0, i);
            ordersGridPane.add(priceLabel, 1, i);

            // Set margin for labels
            GridPane.setMargin(quantityLabel, new javafx.geometry.Insets(0, 0, 5, 0));
            i++;
        }
        confirmedByLabel.setText(order.getString("Store"));
        orderStoreLabel.setText(order.getString("Store"));
        orderNumberLabel.setText("#"+order.getInteger("Order Number"));
        deliveryAddressLabel.setText(UserModel.getLandmark()+", "+UserModel.getAddressDetails());
        modePaymentLabel.setText(order.getString("Mode of Payment"));
        subtotalLabel.setText("PHP "+order.getInteger("Subtotal").toString()+".00");
        deliveryFeeLabel.setText("PHP 20.00");
        totalLabel.setText("PHP "+order.getInteger("Total Price of Order").toString()+".00");

    }

}
