package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.CartModel;
import com.canteam.Byte.Models.ShopModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CartController implements Initializable {

    @FXML
    private Hyperlink addItemLink;

    @FXML
    private Button closeBtn;

    @FXML
    private Label deliveryFeeLabel;

    @FXML
    private GridPane ordersGridPane;

    @FXML
    private ComboBox<String> paymentModeCmb;

    @FXML
    private Button placeOrderBtn;

    @FXML
    private Label subtotalLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private ImageView statusBar;

    private HashMap<String, HashMap<String, String>> userCart = CartModel.getCart();

    private PageNavigator pageNavigator = new PageNavigator();

    private Draggable draggable = new Draggable();

    @FXML
    public void onCloseBtnClicked() {
        pageNavigator.backToPage(closeBtn);
    }

    @FXML
    public void onAddItemLinkClicked() {
        ShopModel.setSelectedShopName(CartModel.getStore());
        pageNavigator.forwardToPage(addItemLink, "Cart", "RestaurantMenu");
    }

    @FXML
    public void onPlaceOrderBtnClicked(){
        // TODO: Place order
    }


    public void loadOrders() throws IOException {
        int row = 1;
        for (String itemName : userCart.keySet()) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/OrderItemCard.fxml"));
            AnchorPane orderItemCard = fxmlLoader.load();

            // Set the data for the cuisine button
            OrderItemCardController orderItemCardController = fxmlLoader.getController();

            HashMap<String, String> itemDetails = userCart.get(itemName);
            String itemQuantity = itemDetails.get("Quantity");
            String itemTotalPrice = itemDetails.get("Total Price");
            String itemInstructions = itemDetails.get("Instructions");
            String itemStore = itemDetails.get("Store");

            orderItemCardController.setData(itemStore, itemName ,itemTotalPrice, itemQuantity);

            ordersGridPane.add(orderItemCard, 0, row);
            // Set margin bottom to 5
            GridPane.setMargin(orderItemCard, new javafx.geometry.Insets(0, 0, 5, 0));
            row++;
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set payment mode combobox - Cash on delivery and Credit card
        paymentModeCmb.getItems().addAll("Cash on delivery", "Credit card");
        paymentModeCmb.getSelectionModel().selectFirst();

        // Make window draggable
        draggable.makeWindowDraggable(statusBar);

        // Make gridpane scrollable
        draggable.makeScrollableY(ordersGridPane);

        // Load the orders
        try {
            loadOrders();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Set subtotal and total price
        subtotalLabel.setText("PHP " + CartModel.getSubtotal() + ".00");
        totalLabel.setText("PHP " + CartModel.getTotalPriceOfOrder() + ".00");

        // Set delivery fee
        deliveryFeeLabel.setText("PHP " + "20.00");


    }
}
