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
//        System.out.println(node.toString());
        node.setOnMousePressed(mouseEvent -> {
//            System.out.println("Pressed");
            mouseAnchorX = mouseEvent.getSceneX();
        });

        node.setOnMouseDragged(mouseEvent -> {
//            System.out.println("Dragging");
            node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
        });
    }

    public void makeScrollableY(Node node){
        node.setOnScroll(scrollEvent -> {
            node.setLayoutY(node.getLayoutY() + scrollEvent.getDeltaY());
        });
    }

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
