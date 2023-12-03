package com.canteam.Byte.Controllers;

import javafx.scene.Node;

public class Draggable {
    private double mouseAnchorX;
    private double mouseAnchorY;

    /**
     * This method makes a node scrollable on the Y axis
     * @param node The node to be made scrollable
     */
    public void makeScrollableY(Node node){
        node.setOnScroll(scrollEvent -> {
            node.setLayoutY(node.getLayoutY() + scrollEvent.getDeltaY());
        });
    }

    /**
     * This method makes a node scrollable on the X axis
     * @param node The node to be made draggable
     */
    public void makeScrollableX(Node node){
        node.setOnScroll(scrollEvent -> {
            node.setLayoutX(node.getLayoutX() + scrollEvent.getDeltaY());
        });
    }
    /**
     * This method makes the parent node (window) draggable on the X and Y axis
     * @param node The node to be made draggable
     */
    public void makeWindowDraggable(Node node){
        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getSceneX();
            mouseAnchorY = mouseEvent.getSceneY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.getScene().getWindow().setX(mouseEvent.getScreenX() - mouseAnchorX);
            node.getScene().getWindow().setY(mouseEvent.getScreenY() - mouseAnchorY);
        });
    }
}
