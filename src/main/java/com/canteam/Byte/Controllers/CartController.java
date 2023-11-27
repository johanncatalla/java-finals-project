package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.CartModel;
import com.canteam.Byte.Models.OrderModel;
import com.canteam.Byte.Models.ShopModel;
import com.canteam.Byte.Models.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

    @FXML
    private AnchorPane payPane;

    @FXML
    private Label payUserName, payTotal, payAlert;

    @FXML
    private TextField payField;

    @FXML
    private Button cancelPay, confirmPay;

    private HashMap<String, HashMap<String, String>> userCart = CartModel.getCart();

    private PageNavigator pageNavigator = new PageNavigator();

    private Draggable draggable = new Draggable();

    public static boolean addItemClicked = false;

    @FXML
    public void onCloseBtnClicked() {
        // Enable the cart button in the restaurant page and restaurant menu page
        addItemClicked = false;
        pageNavigator.backToPage(closeBtn);
    }

    @FXML
    public void onAddItemLinkClicked() {
        ShopModel.setSelectedShopName(CartModel.getStore());
        addItemClicked = true;
        pageNavigator.forwardToPage(addItemLink,"Cart", "RestaurantMenu");
    }

    @FXML
    public void onPlaceOrderBtnClicked() throws IOException {
        // if payment mode is digital currency
        if (paymentModeCmb.getValue().equals("Credit card")){
            payUserName.setText(UserModel.getUserName());
            payTotal.setText("PHP " + CartModel.getTotalPriceOfOrder() + ".00");
            payAlert.setText("");
            confirmPay.setDisable(true);
            payPane.setVisible(true);
        } else {
                CartModel.changeModeOfPayment(UserModel.getUserName(), paymentModeCmb.getValue());
                OrderModel.placeOrder(UserModel.getUserName());

                // Enable the cart button in the restaurant page and restaurant menu page
                addItemClicked = false;

                pageNavigator.navigateToPage(placeOrderBtn, "Cart");
            }

    }

    @FXML
    private void onConfirmPayClicked(){
        // check if payfield is all numbers
        if (payField.getText().matches("[0-9]+")){
            if (Double.parseDouble(payField.getText()) >= CartModel.getTotalPriceOfOrder()){
                payAlert.setText("");
                payPane.setVisible(false);
                CartModel.changeModeOfPayment(UserModel.getUserName(), paymentModeCmb.getValue());
                OrderModel.placeOrder(UserModel.getUserName());

                // Enable the cart button in the restaurant page and restaurant menu page
                addItemClicked = false;

                pageNavigator.navigateToPage(placeOrderBtn, "Cart");
            } else {
                payAlert.setText("Insufficient");
                payAlert.setVisible(true);
                payAlert.setStyle("-fx-text-fill: red;");
                payField.setStyle("-fx-border-color: red;");
            }
        } else {
            payAlert.setVisible(true);
            payAlert.setText("Invalid");
            payAlert.setStyle("-fx-text-fill: red;");
            payField.setStyle("-fx-border-color: red;");
        }
    }

    @FXML
    private void payFieldListener(){
        if (!payField.getText().isEmpty()){
            if (!payField.getText().matches("[0-9]+")){
                confirmPay.setDisable(true);
                payAlert.setVisible(true);
                payAlert.setText("Invalid");
                payAlert.setStyle("-fx-text-fill: red;");
                payField.setStyle("-fx-border-color: red;");
                return;
            }
            payAlert.setVisible(false);
            payField.setStyle("-fx-border-color: grey;");
            confirmPay.setDisable(false);
        } else {
            confirmPay.setDisable(true);
        }
    }

    @FXML
    private void onCancelPayClicked(){
        payPane.setVisible(false);
        payField.setText("");
    }


    public void loadOrders() throws IOException {
        // Clear the gridpane
        ordersGridPane.getChildren().clear();
        System.out.println(userCart);
        if (userCart.isEmpty()) {
            // Set the add item link
            addItemLink.setText("+ Add an item to your cart");
            addItemLink.setOnAction(actionEvent -> {
                // Set the selected shop name
                ShopModel.setSelectedShopName(CartModel.getStore());

                // Disable the cart button in the restaurant page and restaurant menu page
                addItemClicked = true;
                pageNavigator.forwardToPage(addItemLink, "Cart", "Restaurants");
            });

            // Set subtotal and total price
            subtotalLabel.setText("PHP 0.00");
            totalLabel.setText("PHP 0.00");
            deliveryFeeLabel.setText("PHP 0.00");

            // Disable place order button
            placeOrderBtn.setDisable(true);

            return;
        } else {
            int row = 1;
            String itemStore = CartModel.getStore();
            for (String itemName : userCart.keySet()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/OrderItemCard.fxml"));
                AnchorPane orderItemCard = fxmlLoader.load();

                // Set the data for the cuisine button
                OrderItemCardController orderItemCardController = fxmlLoader.getController();
                orderItemCardController.removeBtn.setOnAction(actionEvent -> {
                    // remove from userCart
                    userCart.remove(itemName);
                    // reload the orders
                    try {
                        loadOrders();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    // delete the item from the cart
                    CartModel.deleteItemFromCart(UserModel.getUserName(), itemName);
                    subtotalLabel.setText("PHP " + CartModel.getSubtotal() + ".00");
                    if (CartModel.getCart().isEmpty()) {
                        totalLabel.setText("PHP 0.00");
                        // Set delivery fee
                        deliveryFeeLabel.setText("PHP 0.00");
                        CartModel.emptyCart(UserModel.getUserName());
                    } else {
                        totalLabel.setText("PHP " + CartModel.getTotalPriceOfOrder() + ".00");
                        // Set delivery fee
                        deliveryFeeLabel.setText("PHP " + "20.00");
                    }
                });

                HashMap<String, String> itemDetails = userCart.get(itemName);
                String itemQuantity = itemDetails.get("Quantity");
                String itemTotalPrice = itemDetails.get("Total Price");
                System.out.println("Store: "+itemStore);
                System.out.println("Name: "+itemName);

                orderItemCardController.setData(itemStore, itemName ,itemTotalPrice, itemQuantity);

                ordersGridPane.add(orderItemCard, 0, row);
                // Set margin bottom to 5
                GridPane.setMargin(orderItemCard, new javafx.geometry.Insets(0, 0, 5, 0));
                row++;
            }
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
    }
}
