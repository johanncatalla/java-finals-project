package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.CartModel;
import com.canteam.Byte.Models.ShopModel;
import com.canteam.Byte.MongoDB.Connection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RestaurantsController implements Initializable {
    @FXML
    private ImageView statusBar;

    @FXML
    private GridPane shopsGridPane;

    @FXML
    private AnchorPane gridPaneContainer;

    @FXML
    private Button backButton;

    @FXML
    private TextField searchField;

    @FXML
    private ImageView cartIcon;

    private Draggable draggable = new Draggable();

    public static List<ShopModel> shopList = new ArrayList<>();

    PageNavigator pageNavigator = new PageNavigator();

    private static String initialSearch = null;

    public static List<ShopModel> getData(){
        List<ShopModel> shopList = new ArrayList<>();
        ShopModel shop;

        MongoClient client = Connection.getInstance();
        MongoDatabase database = client.getDatabase("Stores");

        // Get Collection Names For Testing
        List<String> collectionNames = new ArrayList<>();
        for (String name : database.listCollectionNames()) {
            collectionNames.add(name);
        }

        // Just a demo/placeholder data for now
        for (int i = 0; i < collectionNames.size(); i++){
            shop = new ShopModel();
            shop.setShopName(collectionNames.get(i));
            shop.setShopImageSrc("/com/canteam/Byte/assets/images/ShopImgPlaceholder.png");
            shopList.add(shop);
        }
        return shopList;
    }

    @FXML
    protected void onBackButtonClicked(){
        initialSearch = null;
        pageNavigator.backToPage(backButton);
    }

    void onSearchEntered(String text){
        // Clear the gridpane
        shopsGridPane.getChildren().clear();

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < shopList.size(); i++) {
                // Get the fxml file for the cuisine button
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/ShopButton.fxml"));
                AnchorPane cuisineButton = fxmlLoader.load();

                // Set the data for the cuisine button
                ShopButtonController shopButtonController = fxmlLoader.getController();

                if (shopList.get(i).getShopName().toLowerCase().contains(text.toLowerCase())){
                    shopButtonController.setData(shopList.get(i));
                    // Place the cuisine button in the gridPane
                    if (column == 2) {
                        row++;
                        column = 0;
                    }
                    shopsGridPane.add(cuisineButton, column++, row);

                    // Set the margin for the cuisine button
                    GridPane.setMargin(cuisineButton, new Insets(0, 20, 15, 20));

                    // Disable other shops not matching the shop in the cart
                    disableIfHasCart(cuisineButton, i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setInitialSearch(String search) {
        initialSearch = search;
    }

    @FXML
    private void onCartIconClicked(){
        pageNavigator.forwardToPage(cartIcon, "Restaurants", "Cart");
    }

    public static void disableIfHasCart(AnchorPane shopButton, int i){
        String hasCart = CartModel.getStore();
        System.out.println(hasCart);
        if (hasCart != null){
            if (hasCart.equals(shopList.get(i).getShopName())){
                shopButton.setOpacity(1);
                shopButton.setDisable(false);
            } else {
                shopButton.setOpacity(0.5);
                shopButton.setDisable(true);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // if CartController.addItemClicked is true, disable the add to cart button
        if (CartController.addItemClicked){
            cartIcon.setVisible(false);
        } else {
            cartIcon.setVisible(true);
        }

        // Set up window dragging
        draggable.makeWindowDraggable(statusBar);

        // Set up enter on search field
        searchField.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode().toString().equals("ENTER")){
                onSearchEntered(searchField.getText());
            }
        });

        // Set up data for the shops

        if (initialSearch != null){
            onSearchEntered(initialSearch);
        } else {
            String hasCart = CartModel.getStore();
            int column = 0;
            int row = 1;
            try {
                for (int i = 0; i < shopList.size(); i++) {
                    // Get the fxml file for the cuisine button
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/ShopButton.fxml"));
                    AnchorPane shopButton = fxmlLoader.load();

                    // Set the data for the cuisine button
                    ShopButtonController shopButtonController = fxmlLoader.getController();
                    shopButtonController.setData(shopList.get(i));

                    // Place the cuisine button in the gridPane
                    if (column == 2) {
                        row++;
                        column = 0;
                    }
                    shopsGridPane.add(shopButton, column++, row);

                    // Set the margin for the cuisine button
                    GridPane.setMargin(shopButton, new Insets(0, 20, 15, 20));

                    // Disable other shops not matching the shop in the cart
                    disableIfHasCart(shopButton, i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Make gridpane scrollable
        draggable.makeScrollableY(gridPaneContainer);
    }
}
