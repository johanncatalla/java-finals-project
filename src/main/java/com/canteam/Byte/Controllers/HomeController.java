package com.canteam.Byte.Controllers;
import com.canteam.Byte.Models.CuisineModel;
import com.canteam.Byte.Models.ShopModel;
import com.canteam.Byte.Models.UserModel;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.mongodb.client.*;
import org.bson.Document;
import com.canteam.Byte.MongoDB.Connection;

public class HomeController implements Initializable {

    @FXML
    private Button burgerCloseIcon, burgerOpenIcon;

    @FXML
    private Label addressDetails;

    @FXML
    private Label landmarkLabel;

    @FXML
    private AnchorPane burgerMenuPane;

    @FXML
    private Button cartIcon;

    @FXML
    private TextField searchField;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userIcon;

    @FXML
    protected ImageView toRestaurantButton, statusBar;

    @FXML
    private GridPane cuisinesGridPane, dailyDealsGridPane;

    @FXML
    private Hyperlink logoutLink, ordersLink, profileLink, addressLink;

    private List<CuisineModel> cuisineList = new ArrayList<>();

    private Draggable draggable = new Draggable();
    private PageNavigator pageNavigator = new PageNavigator();

    // Get data from the database and return a list of cuisine models
    private List<CuisineModel> getData(){
      List<CuisineModel> cuisineList = new ArrayList<>();
      CuisineModel cuisine;

      MongoClient client = Connection.getInstance();
      MongoDatabase database = client.getDatabase("Byte");
      MongoCollection<Document> collection = database.getCollection("Tags");

      // find the document with Category = "Cuisine Tags"
        Document query = new Document("Category", "Cuisine Tags");
        FindIterable<Document> documents = collection.find(query);
        Document document = documents.first();
        ArrayList<String> arrTags = (ArrayList<String>) document.get("Tags");

      // For each tag, create a cuisine model and add it to the list
      for (String tag : arrTags) {
          cuisine = new CuisineModel();
          cuisine.setCuisineName(tag);
          // TODO: Change this to the actual image of the cuisine
          cuisine.setCuisineImageSrc("/com/canteam/Byte/assets/images/CuisineType/"+tag+".jpg");
          cuisineList.add(cuisine);
      }
      return cuisineList;
    }

    // Navigates to the cart page
    @FXML
    protected void onCartIconClicked() throws IOException {
        pageNavigator.forwardToPage(cartIcon, "Home", "Cart");
    }

    // Signs out the user, clears the navigation history and navigates to the login page
    @FXML
    protected void onLogoutLinkClicked(){
        UserModel.signOut();
        pageNavigator.navigateToPage(logoutLink, "login");
        PageNavigator.clearHistory();
    }

    // Opens the burger menu
    @FXML
    protected void onBurgerOpenIconClicked() {
        TranslateTransition burgerMenuTransition = new TranslateTransition();
        burgerMenuTransition.setNode(burgerMenuPane);
        burgerMenuTransition.setToX(390);
        burgerMenuTransition.setDuration(Duration.seconds(.5));
        burgerMenuTransition.play();
    }

    // Closes the burger menu
    @FXML
    protected void onBurgerCloseIconClicked(){
        TranslateTransition burgerMenuTransition = new TranslateTransition();
        burgerMenuTransition.setNode(burgerMenuPane);
        burgerMenuTransition.setToX(0);
        burgerMenuTransition.setDuration(Duration.seconds(.5));
        burgerMenuTransition.play();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up address details
        landmarkLabel.setText(UserModel.getLandmark());
        addressDetails.setText(UserModel.getAddressDetails());

        // Get data
        RestaurantsController.shopList = RestaurantsController.getData();
        cuisineList.addAll(getData());

        // Set up user icon and name
        userNameLabel.setText(UserModel.getFullName());
        userIcon.setText(String.valueOf(UserModel.getUserName().toUpperCase().charAt(0)));

        // Setup window draggable
        draggable.makeWindowDraggable(statusBar);

        // Set up enter on search field
        searchField.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode().toString().equals("ENTER")){
                RestaurantsController.setInitialSearch(searchField.getText());
                pageNavigator.forwardToPage(searchField, "Home", "Restaurants");
            }
        });

        // Set up data for the shops
        int column = 0;
        try {
            for (int i = 0; i < RestaurantsController.shopList.size(); i++){
                // Shop name
                String shopName = RestaurantsController.shopList.get(i).getShopName();

                // Create an achor pane with the size of 126x162 (WxH)
                AnchorPane shopButton = new AnchorPane();
                shopButton.setPrefSize(126, 162);

                // Create image for the shop image
                Image imagePlaceholder = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/canteam/Byte/assets/images/Store/Daily Deals/"+shopName+".jpg")));

                // Create an image view for the shop image
                ImageView shopImage = new ImageView(imagePlaceholder);
                shopImage.setFitWidth(126);
                shopImage.setFitHeight(162);

                // Place the imageview in the anchor pane
                shopButton.getChildren().add(shopImage);

                // Clip the anchor pane to rounded rectangle
                Rectangle clip = new Rectangle(126, 162);
                clip.setArcWidth(20);
                clip.setArcHeight(20);
                shopButton.setClip(clip);

                // Add listener for the shop button
                int finalI = i;
                shopButton.setOnMousePressed(mouseEvent -> {
                    // Set selected shop name
                    ShopModel.setSelectedShopName(RestaurantsController.shopList.get(finalI).getShopName());
                    // Navigate to the restaurant page
                    pageNavigator.forwardToPage(shopButton, "Home", "RestaurantMenu");
                });

                shopButton.setStyle("-fx-cursor: hand;");

                // Set the margin for the shop button
                GridPane.setMargin(shopButton, new Insets(0, 10, 0, 0));

                // Place the shop button in the gridPane
                dailyDealsGridPane.add(shopButton, column++, 0);
                RestaurantsController.disableIfHasCart(shopButton, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set up data for the cuisines
        column = 1;
        int row = 0;
        try {
            for (int i = 0; i < cuisineList.size(); i++){
                // Get the fxml file for the cuisine button
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/CuisinesButton.fxml"));
                AnchorPane cuisineButton = fxmlLoader.load();

                // Set the data for the cuisine button
                CuisinesButtonController cuisinesButtonController = fxmlLoader.getController();
                cuisinesButtonController.setData(cuisineList.get(i));

                // Place the cuisine button in the gridPane
                if (row == 2){
                    row = 0;
                    column++;
                }
                cuisinesGridPane.add(cuisineButton, column, row++);

                // Set the margin for the cuisine button
                GridPane.setMargin(cuisineButton, new Insets(10));
            }

            // Make navigators
            pageNavigator.makeForwardNavigator(toRestaurantButton, "Home","Restaurants");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onMyOrders() {
        pageNavigator.navigateToPage(ordersLink, "UserOrderList");
    }
    public void onMyDeliveryAddress() {
        pageNavigator.forwardToPage(addressLink, "Home", "Location");
    }
    public void onMyProfile() {
        pageNavigator.forwardToPage(profileLink, "Home", "UserProfile");
    }
}


