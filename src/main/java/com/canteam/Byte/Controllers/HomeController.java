package com.canteam.Byte.Controllers;
import com.canteam.Byte.Models.CuisineModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.image.Image;

import java.io.File;
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
    private AnchorPane burgerMenuPane;

    @FXML
    private ImageView cartIcon;

    @FXML
    private TextField searchField;

    @FXML
    protected ImageView testImageView;

    @FXML
    private GridPane cuisinesGridPane;

    private List<CuisineModel> cuisineList = new ArrayList<>();

    private Draggable draggable = new Draggable();

    private List<CuisineModel> getData(){
      List<CuisineModel> cuisineList = new ArrayList<>();
      CuisineModel cuisine;

      MongoClient client = Connection.getInstance();
      MongoDatabase database = client.getDatabase("Stores");
      MongoCollection<Document> collection = database.getCollection("Mangyupsal");

        FindIterable<Document> documents = collection.find();

        HashMap<String, HashMap<String, Object>> finalMap = new HashMap<>();

        int num = 0;
        for (Document document : documents) {
            HashMap<String, Object> dbMap = new HashMap<>();
            for (String key : document.keySet()) {
                Object value = document.get(key);
                dbMap.put(key, value);
            }
            finalMap.put("Item "+(num++), dbMap);
        }

        String arrTags = (String) finalMap.get("Item 0").get("Item_Name");

      // Just a demo/placeholder data for now
      for (int i = 0; i < 12; i++){
          cuisine = new CuisineModel();
          cuisine.setCuisineName(arrTags +" "+ i);
          cuisine.setCuisineImageSrc("/com/canteam/Byte/assets/images/CuisineType/Milktea.png");
          cuisineList.add(cuisine);
      }
      return cuisineList;
    }


    @FXML
    protected void onBurgerOpenIconClicked() throws IOException {
        TranslateTransition burgerMenuTransition = new TranslateTransition();
        burgerMenuTransition.setNode(burgerMenuPane);
        burgerMenuTransition.setToX(390);
        burgerMenuTransition.setDuration(Duration.seconds(.5));
        burgerMenuTransition.play();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/canteam/Byte/fxml/Home.fxml")));

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(scene);

        stage.show();
        burgerOpenIcon.getScene().getWindow().hide();
    }

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
        // Will create an error at start but will be ok when you open the home page
        cuisineList.addAll(getData());
        int column = 1;
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

            // Make cuisine pane draggable
            draggable.makeDraggable(cuisinesGridPane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

