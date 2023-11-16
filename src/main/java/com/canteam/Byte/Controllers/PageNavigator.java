package com.canteam.Byte.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

class PageNavigator{

    public void navigateToPage(Node node, String pageName){
            try{
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/canteam/Byte/fxml/"+pageName+".fxml")));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                node.getScene().getWindow().hide();
            } catch (Exception e){
                e.printStackTrace();
            }
    }
    public void makeNavigator(Node node, String pageName){
        node.setOnMouseClicked(mouseEvent ->{
            navigateToPage(node, pageName);
        });
    }
}