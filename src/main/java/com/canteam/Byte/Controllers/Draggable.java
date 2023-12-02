package com.canteam.Byte.Controllers;

import javafx.scene.Node;

public class Draggable {
    private double mouseAnchorX;
    private double mouseAnchorY;

    /**
     *  This method makes a node draggable on the X axis
     * @param node The node to be made draggable
     */
    public void makeDraggableX(Node node){
        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getSceneX();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
        });
    }

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

    public void makeParentScrollableX(Node node){
        node.setOnScroll(scrollEvent -> {
            node.getParent().setLayoutX(node.getLayoutX() + scrollEvent.getDeltaX());
        });
    }

    public void makeParentScrollableY(Node node){
        node.setOnScroll(scrollEvent -> {
            node.getParent().setLayoutY(node.getLayoutY() + scrollEvent.getDeltaY());
        });
    }


    /**
     * This method makes a node draggable on the X axis relative to its parent
     * @param node The node to be made draggable
     */
    public void makeParentDraggableX(Node node){
        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getSceneX();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.getParent().setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
        });
    }

    /**
     * This method makes a node draggable on the Y axis
     * @param node The node to be made draggable
     */
    public void makeDraggableY(Node node){
        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorY = mouseEvent.getSceneY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });
    }

    /**
     * This method makes a node draggable on the X and Y axis
     * @param node The node to be made draggable
     */
    public void makeDraggableXY(Node node){
        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getSceneX();
            mouseAnchorY = mouseEvent.getSceneY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
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
