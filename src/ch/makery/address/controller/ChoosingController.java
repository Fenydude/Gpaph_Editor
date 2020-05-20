package ch.makery.address.controller;

import ch.makery.address.model.Arc;
import ch.makery.address.model.Graph;
import ch.makery.address.model.Vertex;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.effect.Bloom;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.List;

import static javafx.scene.input.KeyCode.DELETE;

public class ChoosingController implements Runnable {

    private SimpleObjectProperty<EventHandler<MouseEvent>> circleOnMousePressedEventHandler;
    private List<Graph> graphs;
    private EventHandler<MouseEvent> circleOnMouseDraggedEventHandler;
    private EventHandler<MouseEvent> transLine;

    public ChoosingController(SimpleObjectProperty<EventHandler<MouseEvent>> circleOnMousePressedEventHandler,
                              List<Graph> graphs, EventHandler<MouseEvent> circleOnMouseDraggedEventHandler,
                              EventHandler<MouseEvent> transLine) {
        this.circleOnMousePressedEventHandler = circleOnMousePressedEventHandler;
        this.graphs = graphs;
        this.circleOnMouseDraggedEventHandler = circleOnMouseDraggedEventHandler;
        this.transLine = transLine;
    }

    @Override
    public void run() {
        for (Graph graph : graphs) {
            if (graph.getTab().isSelected()) {

                for (Vertex vertex : graph.getVertices()) {

                    vertex.getCircle().setOnMousePressed(circleOnMousePressedEventHandler.get());
                    vertex.getCircle().setOnMouseEntered(event -> {
                        if (!vertex.getCircle().getStroke().equals(Color.ORANGERED) &&
                                !vertex.getCircle().getStroke().equals(Color.GREEN) &&
                                !vertex.getCircle().getStroke().equals(Color.MAROON)) {
                            vertex.getCircle().setStroke(Color.DARKORANGE);

                        }
                    });
                    if (vertex.getLoop() != null) {
                        vertex.getLoop().setOnMouseEntered(event -> {
                            if (!vertex.getLoop().getStroke().equals(Color.ORANGERED))
                                vertex.getLoop().setStroke(Color.DARKORANGE);
                        });
                        vertex.getLoop().setOnMouseExited(event -> {
                            if (!vertex.getLoop().getStroke().equals(Color.ORANGERED))
                                vertex.getLoop().setStroke(Color.BLACK);
                        });
                        vertex.getLoop().setOnMousePressed(event -> {
                            vertex.getLoop().setStroke(Color.ORANGERED);
                            vertex.getLoop().getScene().setOnKeyPressed(e -> {
                                if (e.getCode() == DELETE) {
                                    graph.getMatrixAdjancy().get(vertex.getVertexId()).set(vertex.getVertexId(), 0);
                                    ((Pane) graph.getTab().getContent()).getChildren().remove(vertex.getLoop());
                                    vertex.removeLoop();
                                }
                            });
                        });
                    }
                    vertex.getCircle().setOnMouseExited(event -> {
                        if (!vertex.getCircle().getStroke().equals(Color.ORANGERED) &&
                                !vertex.getCircle().getStroke().equals(Color.GREEN) &&
                                !vertex.getCircle().getStroke().equals(Color.MAROON))
                            vertex.getCircle().setStroke(Color.BLACK);
                    });
                    vertex.getCircle().setOnMouseDragged(circleOnMouseDraggedEventHandler);

                    for (Arc arc : vertex.getArcs()) {
                        arc.setOnMouseDragged(transLine);
                        arc.setOnMouseEntered(event -> {
                            if (!arc.getStroke().equals(Color.ORANGERED))
                                arc.setColor(Color.DARKORANGE);
                        });
                        arc.setOnMousePressed(e -> {
                            arc.setColor(Color.ORANGERED);
                            graph.getVertices().forEach(vertex1 -> {
                                vertex1.getCircle().setStroke(Color.BLACK);
                                vertex1.getArcs().forEach(arc1 -> {
                                    if (arc != arc1)
                                        arc1.setColor(Color.BLACK);
                                });
                            });
                            arc.getScene().setOnKeyPressed(e1 -> {
                                if (e1.getCode() == KeyCode.DELETE) {
                                    arc.getBegin().getArcs().remove(arc);
                                    arc.getEnd().getArcs().remove(arc);
                                    graph.removeArcFromMatrix(arc);
                                    ((Pane) graph.getTab().getContent()).getChildren().removeAll(arc.getArrow());
                                    ((Pane) graph.getTab().getContent()).getChildren().remove(arc);
                                }
                            });
                        });
                        arc.setOnMouseExited(event -> {
                            if (!arc.getStroke().equals(Color.ORANGERED))
                                arc.setColor(Color.BLACK);
                        });
                    }
                }
            }
        }
    }
}
