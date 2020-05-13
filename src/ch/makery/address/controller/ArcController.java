package ch.makery.address.controller;

import ch.makery.address.model.Graph;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ArcController implements EventHandler<MouseEvent> {

    private Graph graph;
    private Button loop;
    private Pane root;

    public ArcController(Graph graph, Button loop, Pane root) {
        this.graph = graph;
        this.loop = loop;
        this.root = root;
    }

    @Override
    public void handle(MouseEvent event) {

    }


}
