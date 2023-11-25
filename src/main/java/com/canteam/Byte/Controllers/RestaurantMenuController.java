package com.canteam.Byte.Controllers;
import com.canteam.Byte.Models.CartModel;
import com.canteam.Byte.Models.ItemModel;
import com.canteam.Byte.Models.ShopModel;
import com.canteam.Byte.Models.UserModel;
import com.canteam.Byte.MongoDB.Connection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class RestaurantMenuController {

    @FXML
    private Button addOnsButton;

    @FXML
    private Button addQtyBtn;

    @FXML
    private Button subtractQtyBtn;
    @FXML
    private Label qtyLabel;

    @FXML
    private Button backButton;

    @FXML
    private Button beveragesButton;

    @FXML
    private ImageView cartIcon;

    @FXML
    private AnchorPane gridPaneContainer, itemViewPane, qtyAddSubPane, scrollAnchorPane;

    @FXML
    private GridPane itemsGridPane;

    @FXML
    private Button koreanStreetFoodBtn;

    @FXML
    private Button popularBtn;

    @FXML
    private Label restaurantName, sectionTitle, sectionDescription;

    @FXML
    private Button riceBowlBtn;

    @FXML
    private Button riceToppingsBtn;

    @FXML
    private TextField searchField;

    @FXML
    private Button sizzlingBtn;

    @FXML
    private ImageView statusBar;

    @FXML
    private GridPane sectionBar;

    @FXML
    private ImageView mealImg;

    @FXML
    private Label mealName;

    @FXML
    private Label mealPrice;

    @FXML
    private TextField specialInstructionsTxt;

    List<ItemModel> shopItems = new ArrayList<>();

    private Draggable draggable = new Draggable();

    private PageNavigator pageNavigator = new PageNavigator();

    @FXML
    protected void onBackButtonClicked(){
        pageNavigator.backToPage(backButton);
    }

    // onPopularButtonClicked() is a method that is called when the popularBtn is clicked
    @FXML
    protected void onPopularButtonClicked(){
        sectionTitle.setText("Popular");
        sectionDescription.setText("These are the most popular items in " + ShopModel.getSelectedShopName());

        // clear the grid pane
        itemsGridPane.getChildren().clear();

        // Add the items to the grid pane
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < shopItems.size(); i++) {
                // Get the fxml file for the cuisine button
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/Item.fxml"));
                AnchorPane item = fxmlLoader.load();

                // Set the data for the cuisine button
                ItemController itemController = fxmlLoader.getController();
                // Check if itemTags contains "Rice Meal"


                if (shopItems.get(i).isItemPopular()) {
                    itemController.setData(shopItems.get(i), "/com/canteam/Byte/assets/images/Store/SampleItem.jpg");
                    // Place the cuisine button in the gridPane
                    if (column == 2) {
                        row++;
                        column = 0;
                    }
                    itemsGridPane.add(item, column++, row);
                }

                // Set the margin for the cuisine button
                GridPane.setMargin(item, new Insets(20));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // onKoreanStreetFoodButtonClicked() is a method that is called when the koreanStreetFoodBtn is clicked
    @FXML
    protected void onKoreanStreetFoodButtonClicked(){
        sectionTitle.setText("Korean Street Food");
        sectionDescription.setText("These are the Korean Street Food items in " + ShopModel.getSelectedShopName());

        conditionShopTags("Korean Street Food");
    }

    // onRiceBowlButtonClicked() is a method that is called when the riceBowlBtn is clicked
    @FXML
    protected void onRiceBowlButtonClicked(){
        sectionTitle.setText("Rice Bowl");
        sectionDescription.setText("These are the Rice Bowl items in " + ShopModel.getSelectedShopName());

        conditionShopTags("Rice Bowl");
    }

    // onRiceToppingsButtonClicked() is a method that is called when the riceToppingsBtn is clicked
    @FXML
    protected void onRiceToppingsButtonClicked(){
        sectionTitle.setText("Rice Toppings");
        sectionDescription.setText("These are the Rice Toppings items in " + ShopModel.getSelectedShopName());

        conditionShopTags("Rice Toppings");
    }

    // onSizzlingButtonClicked() is a method that is called when the sizzlingBtn is clicked
    @FXML
    protected void onSizzlingButtonClicked(){
        sectionTitle.setText("Sizzling");
        sectionDescription.setText("These are the Sizzling items in " + ShopModel.getSelectedShopName());

        conditionShopTags("Sizzling");
    }

    // onBeveragesButtonClicked() is a method that is called when the beveragesButton is clicked
    @FXML
    protected void onBeveragesButtonClicked(){
        sectionTitle.setText("Beverages");
        sectionDescription.setText("These are the Beverages items in " + ShopModel.getSelectedShopName());

        conditionShopTags("Beverages");
    }

    // onAddOnsButtonClicked() is a method that is called when the addOnsButton is clicked
    @FXML
    protected void onAddOnsButtonClicked(){
        sectionTitle.setText("Add-Ons");
        sectionDescription.setText("These are the Add-Ons items in " + ShopModel.getSelectedShopName());

        conditionShopTags("Add-Ons");
    }

    @FXML
    void onAddQty(ActionEvent event) {
        if (Integer.parseInt(qtyLabel.getText()) < 10) {
            qtyLabel.setText(Integer.toString(Integer.parseInt(qtyLabel.getText()) + 1));
        }
    }

    @FXML
    void onSubtractQty(ActionEvent event) {
        if (Integer.parseInt(qtyLabel.getText()) > 1) {
            qtyLabel.setText(Integer.toString(Integer.parseInt(qtyLabel.getText()) - 1));
        }
    }

    private void conditionShopTags(String shopTag){
        // clear the grid pane
        itemsGridPane.getChildren().clear();

        // Add the items to the grid pane
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < shopItems.size(); i++) {
                // Get the fxml file for the cuisine button
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/Item.fxml"));
                AnchorPane item = fxmlLoader.load();

                // Set the data for the cuisine button
                ItemController itemController = fxmlLoader.getController();
                // Check if itemTags contains "Rice Meal"


                if (shopItems.get(i).getItemShopTags().contains(shopTag)) {
                    itemController.setData(shopItems.get(i), "/com/canteam/Byte/assets/images/Store/SampleItem.jpg");
                    // Place the cuisine button in the gridPane
                    if (column == 2) {
                        row++;
                        column = 0;
                    }
                    itemsGridPane.add(item, column++, row);
                }

                // Set the margin for the cuisine button
                GridPane.setMargin(item, new Insets(20));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onEnterPressed(){
        // clear the grid pane
        itemsGridPane.getChildren().clear();

        // Add the items to the grid pane
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < shopItems.size(); i++) {
                // Get the fxml file for the cuisine button
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/Item.fxml"));
                AnchorPane item = fxmlLoader.load();

                // Set the data for the cuisine button
                ItemController itemController = fxmlLoader.getController();
                // Check if itemTags contains "Rice Meal"


                if (shopItems.get(i).getItemName().toLowerCase().contains(searchField.getText())) {
                    itemController.setData(shopItems.get(i), "/com/canteam/Byte/assets/images/Store/SampleItem.jpg");
                    // Place the cuisine button in the gridPane
                    if (column == 2) {
                        row++;
                        column = 0;
                    }
                    itemsGridPane.add(item, column++, row);
                }

                // Set the margin for the cuisine button
                GridPane.setMargin(item, new Insets(20));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showItem(){

        // Set the meal name and price
        mealName.setText(ItemModel.getSelectedItemInfo().get("Item_Name"));
        mealPrice.setText("PHP " + ItemModel.getSelectedItemInfo().get("Item_Price") + ".00");

        // sample item image
        Image imagePlaceHolder = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/canteam/Byte/assets/images/Store/SampleItem.jpg")));
        mealImg.setImage(imagePlaceHolder);

        // Animation for the item view pane after clicking an item
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(javafx.util.Duration.seconds(0.5));
        transition.setNode(itemViewPane);
        transition.setToY(-750);
        transition.play();

        // Set qtyAddSubPane to visible
        qtyAddSubPane.setVisible(true);
    }

    @FXML
    public void hideItem(){
        // Animation for the item view pane after clicking an item
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(javafx.util.Duration.seconds(0.5));
        transition.setNode(itemViewPane);
        transition.setToY(0);
        transition.play();

        // Set qtyAddSubPane to visible
        qtyAddSubPane.setVisible(false);
        qtyLabel.setText("1");

        // clear the special instructions text field
        specialInstructionsTxt.clear();

        // put the scrollAnchorPane back to the top
        scrollAnchorPane.setLayoutY(0);
    }

    @FXML
    private void onAddtoCartBtnClicked(){
        // TODO: Change behavior when extra is checked
        HashMap<String, String> selectedItem = ItemModel.getSelectedItemInfo();

        System.out.println(selectedItem);
        if (selectedItem.get("Item_Sizes").isEmpty()) {
            CartModel.addToCart(UserModel.getUserName(), selectedItem.get("Item_Name"),
                    Double.parseDouble(selectedItem.get("Item_Price")), Integer.parseInt(qtyLabel.getText()),
                    selectedItem.get("Item_Store"), specialInstructionsTxt.getText(), null, selectedItem.get("Item_Image"));
        } else {
            HashMap<String, Integer> sizesMap = ItemModel.convertDocumentStrToHashMap(selectedItem.get("Item_Sizes"));
            CartModel.addToCart(UserModel.getUserName(), selectedItem.get("Item_Name"),
                    Double.parseDouble(selectedItem.get("Item_Price")), Integer.parseInt(qtyLabel.getText()),
                    selectedItem.get("Item_Store"), specialInstructionsTxt.getText(), null, selectedItem.get("Item_Image"));
        }

    }


    private List<ItemModel> getData(){
        MongoClient client = Connection.getInstance();
        MongoDatabase database = client.getDatabase("Stores");
        MongoCollection<Document> collection = database.getCollection(ShopModel.getSelectedShopName());

        FindIterable<Document> documents = collection.find();

        ArrayList<HashMap<String, String>> finalArray = new ArrayList<>();

        // Iterate through the documents
        for (Document document : documents) {
            HashMap<String, String> dbMap = new HashMap<>();
            for (String key : document.keySet()) {
                Object value = document.get(key);
                // value could be null
                if (value == null) {
                    value = "";
                }
                dbMap.put(key, value.toString());
            }
            finalArray.add(dbMap);
        }

        // Create a list of ItemModel objects
        List<ItemModel> itemList = new ArrayList<>();
        ItemModel item;

        // Iterate through the finalArray
        for (int i = 0; i < finalArray.size(); i++){
            item = new ItemModel();
            item.setData(finalArray.get(i));
            System.out.println(item.getItemName());
            itemList.add(item);
        }
        return itemList;
    }


    @FXML
    void initialize() {
        // clear the grid pane
        itemsGridPane.getChildren().clear();

        // Get the data from the database
        shopItems.addAll(getData());

        // Add the items to the grid pane
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < shopItems.size(); i++) {
                ItemModel itemModel = shopItems.get(i);

                // Get the fxml file for the cuisine button
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/Item.fxml"));
                AnchorPane item = fxmlLoader.load();

                // add event listener to the item button
                item.setOnMouseClicked(mouseEvent -> {
                    ItemModel.setSelectedItemInfo(itemModel.getItemInfo());
                    showItem();
                });

                // Set the data for the cuisine button
                ItemController itemController = fxmlLoader.getController();

                itemController.setData(shopItems.get(i), "/com/canteam/Byte/assets/images/Store/SampleItem.jpg");
                if (column == 2) {
                    row++;
                    column = 0;
                }
                itemsGridPane.add(item, column++, row);

                // Set the margin for the cuisine button
                GridPane.setMargin(item, new Insets(20));
            }
            draggable.makeScrollableY(gridPaneContainer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        restaurantName.setText(ShopModel.getSelectedShopName());
        draggable.makeWindowDraggable(statusBar);
        draggable.makeScrollableY(scrollAnchorPane);

        // Make scrollable all the buttons in the section bar
        draggable.makeParentDraggableX(popularBtn);
        draggable.makeParentDraggableX(koreanStreetFoodBtn);
        draggable.makeParentDraggableX(riceBowlBtn);
        draggable.makeParentDraggableX(riceToppingsBtn);
        draggable.makeParentDraggableX(sizzlingBtn);
        draggable.makeParentDraggableX(beveragesButton);
        draggable.makeParentDraggableX(addOnsButton);

        // Make the search field on enter pressed
        searchField.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case ENTER:
                    onEnterPressed();
                    break;
            }
        });

        //Clip the scrollAnchorPane to ItemViewPane
        Rectangle clip = new Rectangle(
                itemViewPane.getPrefWidth(), itemViewPane.getPrefHeight()
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);

        itemViewPane.setClip(clip);
    }

}
