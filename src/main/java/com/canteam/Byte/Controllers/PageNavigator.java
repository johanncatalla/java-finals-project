package com.canteam.Byte.Controllers;

import com.canteam.Byte.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Objects;

class PageNavigator {

    // Stacks for Navigation History
    private static ArrayList<String> navigationHistory = new ArrayList<>();

    public static void push(String pageName){
        navigationHistory.add(pageName);
        System.out.println(navigationHistory.toString());
    }

    public static String pop(){
        String destination = navigationHistory.get(navigationHistory.size() - 1);
        navigationHistory.remove(navigationHistory.size()-1);
        System.out.println(navigationHistory.toString());
        return destination;
    }

    public static void clearHistory(){
        navigationHistory.clear();
    }

    /**
     * Navigates to a page
     * @param node The node to be clicked
     * @param pageName The name of the page to be navigated to
     */
    public void navigateToPage(Node node, String pageName){
            try{
                MainApplication.changeScene(pageName);
            } catch (Exception e){
                e.printStackTrace();
            }
    }

    public void forwardToPage(Node node,String sourcePageName, String destinationPageName){
        push(sourcePageName);
        navigateToPage(node, destinationPageName);
    }

    public void backToPage(Node node){
        navigateToPage(node, pop());
    }

    /**
     * Makes a node clickable and navigates to a page
     * @param node The node to be clicked
     * @param sourcePageName The name of the current page
     * @param destinationPageName The name of the destination page.
     */
     public void makeForwardNavigator(Node node, String sourcePageName, String destinationPageName){
        node.setOnMouseClicked(mouseEvent -> {
            forwardToPage(node, sourcePageName, destinationPageName);
        });
    }

    /**
     * Makes a node clickable and navigates back a page
     * @param node The node to be clicked
     */
    public void makeBackwardNavigator(Node node){
        node.setOnMouseClicked(mouseEvent -> {
            backToPage(node);
        });
    }
}