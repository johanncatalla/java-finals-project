package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.bson.Document;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReferenceArray;

import com.canteam.Byte.Models.UserModel;
import com.canteam.Byte.Models.OrderModel;

public class UserOrderListController implements Initializable {

    @FXML
    private Button closeOrdersButton;

    @FXML
    private GridPane gridOrders;
    private PageNavigator pageNavigator = new PageNavigator();

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Document> userOrderList = OrderModel.getUserOrders(UserModel.getUserName());

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < userOrderList.size(); i++) {
                // Get the fxml file for the cuisine button
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/UserOrderQueue.fxml"));
                AnchorPane orderContainer = fxmlLoader.load();

                // Set the data for the cuisine button
                UserOrderQueueController userOrderQueueController = fxmlLoader.getController();

                // Get the current item's data
                Document orderData = userOrderList.get(i);
                System.out.println(orderData);
                Document cartData = (Document) orderData.get("Cart");
                String orderItemString = "";
                String orderStore = orderData.getString("Store");
                String orderStatus = orderData.getString("Order Status");
                String orderNumber = orderData.getInteger("Order Number").toString();

                // Get the string of order items
                int item = 0;
                for (String key : cartData.keySet()) {
                    if (item == cartData.keySet().size() - 1) {
                        orderItemString += key;
                    } else {
                        orderItemString += key + ", ";
                        item++;
                    }
                }

                userOrderQueueController.setData(orderItemString, orderStore, orderStatus, orderNumber);

                gridOrders.add(orderContainer, column, row++);

                // Set the margin for the cuisine button
                GridPane.setMargin(orderContainer, new Insets(10));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    public void onCloseOrdersButtonClick(ActionEvent event) {
        pageNavigator.navigateToPage(closeOrdersButton, "Home");
    }
}