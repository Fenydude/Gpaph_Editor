package ch.makery.address.controller;


import ch.makery.address.model.Graph;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;

import java.awt.*;

public class LoopCreate implements EventHandler<MouseEvent> {

    private Graph graph;
    private Button loop;
    private Pane root;

    public LoopCreate(Graph graph, Button loop, Pane root) {
        this.graph = graph;
        this.loop = loop;
        this.root = root;
    }

    @Override
    public void handle(MouseEvent event) {
        if (loop.isDisable()) {
            Arc arc = new Arc();
            //Setting the properties of the arc
            arc.setCenterX(event.getX());
            arc.setCenterY(event.getY());
            arc.setRadiusX(20.0f);
            arc.setRadiusY(20.0f);
            arc.setStartAngle(100.0f);
            arc.setLength(150.0f);

            //Setting the type of the arc
            arc.setType(ArcType.ROUND);
            root.getChildren().add(arc);

        }
    }
}
