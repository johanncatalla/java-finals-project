package com.canteam.Byte.Controllers;

import com.canteam.Byte.Application;
import javafx.scene.Node;

import java.util.ArrayList;

class PageNavigator {

    // Stacks for Navigation History
    private static ArrayList<String> navigationHistory = new ArrayList<>();

    public static void push(String pageName){
        navigationHistory.add(pageName);
    }

    public static String pop(){
        String destination = navigationHistory.get(navigationHistory.size() - 1);
        navigationHistory.remove(navigationHistory.size()-1);
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
                Application.changeScene(pageName);
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