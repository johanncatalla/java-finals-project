package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.CartModel;
import com.canteam.Byte.Models.OrderModel;
import com.canteam.Byte.Models.ShopModel;
import com.canteam.Byte.Models.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private AnchorPane payPane, shadow;

    @FXML
    private Label payUserName, payTotal, payAlert;

    @FXML
    private TextField payField;

    @FXML
    private Button cancelPay, confirmPay;

    private double paidTotal, changeTotal;

    private PageNavigator pageNavigator = new PageNavigator();

    private Draggable draggable = new Draggable();
    private HashMap<String, HashMap<String, String>> userCart = CartModel.getCart();


    public static boolean addItemClicked = false;

    /**
     * Handles the action when the close button is clicked.
     * Sets addItemClicked to false and navigates back to the previous page using pageNavigator.
     */
    @FXML
    public void onCloseBtnClicked() {
        addItemClicked = false;
        pageNavigator.backToPage(closeBtn);
    }

    /**
     * Handles the action when the add item link is clicked.
     * Sets the selected shop name, sets addItemClicked to true, and navigates to the cart page or restaurant menu using pageNavigator.
     */
    @FXML
    public void onAddItemLinkClicked() {
        ShopModel.setSelectedShopName(CartModel.getStore());
        addItemClicked = true;
        pageNavigator.forwardToPage(addItemLink, "Cart", "RestaurantMenu");
    }

    /**
     * Handles the action when the place order button is clicked.
     * If the payment mode is set to "Credit card," it prepares payment details for confirmation.
     * Otherwise, it changes the mode of payment, places the order, and navigates to the cart page.
     *
     * @throws IOException If an error occurs during the process of placing the order
     */
    @FXML
    public void onPlaceOrderBtnClicked() throws IOException {
        if (paymentModeCmb.getValue().equals("Credit card")) {
            // Prepare payment details for credit card payment
            payUserName.setText(UserModel.getUserName());
            payTotal.setText("PHP " + CartModel.getTotalPriceOfOrder() + ".00");
            payAlert.setText("");
            confirmPay.setDisable(true);
            payPane.setVisible(true);
        } else {
            // Change mode of payment, place order, and navigate to the cart page
            onSuccessOrder("COD");
            CartModel.changeModeOfPayment(UserModel.getUserName(), paymentModeCmb.getValue());
            OrderModel.placeOrder(UserModel.getUserName());
            addItemClicked = false;
            pageNavigator.navigateToPage(placeOrderBtn, "Cart");
        }
    }

    /**
     * Handles the action when the confirm payment button is clicked.
     * Validates the payment amount entered and proceeds with the order placement if the amount is sufficient.
     * Displays error messages for invalid or insufficient payment amounts.
     */
    @FXML
    private void onConfirmPayClicked() throws InterruptedException {
        String paymentText = payField.getText();
        double totalPrice = CartModel.getTotalPriceOfOrder();
        if (paymentText.matches("[0-9]+")) {
            double pay = Double.parseDouble(paymentText);
            if (pay >= totalPrice) {
                // Sufficient payment: Change mode of payment, place order, and navigate to the cart page
                payAlert.setText("");
                payPane.setVisible(false);

                paidTotal = pay;
                changeTotal = pay - totalPrice;

                onSuccessOrder("Card");

                CartModel.changeModeOfPayment(UserModel.getUserName(), paymentModeCmb.getValue());
                OrderModel.placeOrder(UserModel.getUserName());
                addItemClicked = false;
                pageNavigator.navigateToPage(placeOrderBtn, "Cart");
            } else {
                // Insufficient payment: Display error message
                payAlert.setText("Insufficient");
                payAlert.setVisible(true);
                payAlert.setStyle("-fx-text-fill: red;");
                payField.setStyle("-fx-border-color: red;");
            }
        } else {
            // Invalid input: Display error message
            payAlert.setVisible(true);
            payAlert.setText("Invalid");
            payAlert.setStyle("-fx-text-fill: red;");
            payField.setStyle("-fx-border-color: red;");
        }
    }

    /**
     * Listener for changes in the payField (payment amount input field).
     * Enables/disables the confirm payment button based on the validity of the input.
     * Updates UI to provide feedback on the validity of the entered payment amount.
     */
    @FXML
    private void payFieldListener() {
        if (!payField.getText().isEmpty()) {
            // If the field is not empty
            if (!payField.getText().matches("[0-9]+")) {
                // If input is not numeric
                confirmPay.setDisable(true);
                payAlert.setVisible(true);
                payAlert.setText("Invalid");
                payAlert.setStyle("-fx-text-fill: red;");
                payField.setStyle("-fx-border-color: red;");
                return;
            }
            // Valid numeric input
            payAlert.setVisible(false);
            payField.setStyle("-fx-border-color: grey;");
            confirmPay.setDisable(false);
        } else {
            // If the field is empty
            confirmPay.setDisable(true);
        }
    }

    /**
     * Handles the action when the 'Cancel Pay' button is clicked.
     * Hides the payment pane and clears the payment field.
     */
    @FXML
    private void onCancelPayClicked(){
        payPane.setVisible(false);
        payField.setText("");
    }

    public void loadOrders() throws IOException {
        // Clear the gridpane
        ordersGridPane.getChildren().clear();
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

                subtotalLabel.setText("PHP " + CartModel.getSubtotal() + ".00");
                deliveryFeeLabel.setText("PHP 20.00");
                totalLabel.setText("PHP " + CartModel.getTotalPriceOfOrder() + ".00");

                HashMap<String, String> itemDetails = userCart.get(itemName);
                String itemQuantity = itemDetails.get("Quantity");
                String itemTotalPrice = itemDetails.get("Total Price");

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

    public void onSuccessOrder(String mode){

        // shadow
        shadow.setVisible(true);

        // Load the payment successful page and wait for it to close
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/PaymentSuccessful.fxml"));
        AnchorPane paymentSuccessful = null;
        try {
            paymentSuccessful = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        double priceOfOrder = (double) CartModel.getTotalPriceOfOrder();

        PaymentSuccessfulController paymentSuccessfulController = fxmlLoader.getController();
        if (mode.equals("COD")) {
            paymentSuccessfulController.setData("PHP %.2f".formatted(priceOfOrder));
        } else {
            paymentSuccessfulController.setData("PHP %.2f".formatted(priceOfOrder), "PHP %.2f".formatted(paidTotal), "PHP %.2f".formatted(changeTotal));
        }

        // Create a new stage for the payment successful page
        Stage paymentSuccessfulStage = new Stage();
        paymentSuccessfulStage.setScene(new Scene(paymentSuccessful));

        // remove title bar
        paymentSuccessfulStage.initStyle(StageStyle.UNDECORATED);

        // Show
        paymentSuccessfulStage.showAndWait();

        shadow.setVisible(false);

    }
}
