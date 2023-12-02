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

public class RiderOrderListController implements Initializable {

    @FXML
    private Button closeOrdersButton;

    @FXML
    private GridPane gridOrders;

    @FXML
    private AnchorPane scrollAnchor;

    @FXML
    private AnchorPane statusBar;

    @FXML
    private Button reloadBtn;

    @FXML
    void onReload(ActionEvent event) {
        pageNavigator.navigateToPage(reloadBtn, "RiderOrderList");
    }

    private PageNavigator pageNavigator = new PageNavigator();
    private Draggable draggable = new Draggable();

    @FXML
    void onCloseOrdersButtonClick(ActionEvent event) {
        UserModel.signOut();
        pageNavigator.navigateToPage(closeOrdersButton, "login");
        PageNavigator.clearHistory();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        draggable.makeScrollableY(scrollAnchor);
        draggable.makeWindowDraggable(statusBar);
        ArrayList<Document> riderOrders = OrderModel.getRiderOrders();
        System.out.println(riderOrders);

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i <riderOrders.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/RiderOrderQueue.fxml"));
                AnchorPane orderContainer = fxmlLoader.load();

                RiderOrderQueueController riderOrderQueueController = fxmlLoader.getController();

                Document orderData = riderOrders.get(i);
                Document cartData = (Document) orderData.get("Cart");

                String fullName = orderData.getString("Full Name");
                String contact = orderData.getString("Contact");
                String address = orderData.getString("Address");
                String orderNumber = orderData.getInteger("Order Number").toString();
                String shop = orderData.getString("Store");

                riderOrderQueueController.setData(fullName, contact, address, orderNumber, shop);

                gridOrders.add(orderContainer, column, row++);
                GridPane.setMargin(orderContainer, new Insets(0, 0, 20, 0));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
