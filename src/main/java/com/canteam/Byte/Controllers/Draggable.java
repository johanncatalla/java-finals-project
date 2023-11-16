package com.canteam.Byte.Controllers;

import javafx.scene.Node;

public class Draggable {
    private double mouseAnchorX;

    public void makeDraggable(Node node){
        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getSceneX();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
        });
    }

}
