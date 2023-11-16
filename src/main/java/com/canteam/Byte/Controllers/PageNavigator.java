package com.canteam.Byte.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

class PageNavigator{

    /**
     * Navigates to a page
     * @param node The node to be clicked
     * @param pageName The name of the page to be navigated to
     */
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

    /**
     * Makes a node clickable and navigates to a page
     * @param node The node to be clicked
     * @param pageName The name of the page to be navigated to
     */
    public void makeNavigator(Node node, String pageName){
        node.setOnMouseClicked(mouseEvent ->{
            navigateToPage(node, pageName);
        });
    }
}