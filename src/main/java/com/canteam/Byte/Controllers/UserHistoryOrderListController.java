package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.OrderModel;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserHistoryOrderListController implements Initializable {

    @FXML
    private Button closeOrdersButton;

    @FXML
    private GridPane gridOrders;

    @FXML
    private AnchorPane scrollAnchor, statusBar;
    private PageNavigator pageNavigator = new PageNavigator();

    private Draggable draggable = new Draggable();

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        draggable.makeScrollableY(scrollAnchor);
        draggable.makeWindowDraggable(statusBar);

        ArrayList<Document> userOrderList = OrderModel.getUserOrdersHistory(UserModel.getUserName());
        System.out.println("History"+userOrderList);

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < userOrderList.size(); i++) {
                // Get the fxml file for the cuisine button
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/UserHistoryOrderQueue.fxml"));
                AnchorPane orderContainer = fxmlLoader.load();

                // Set the data for the cuisine button
                UserHistoryOrderQueueController userHistoryOrderQueueController = fxmlLoader.getController();

                // Get the current item's data
                Document orderData = userOrderList.get(i);
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

                userHistoryOrderQueueController.setData(orderItemString, orderStore, orderStatus, orderNumber);

                gridOrders.add(orderContainer, column, row++);

                // Set the margin for the cuisine button
                GridPane.setMargin(orderContainer, new Insets(0, 0, 20, 0));
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