package ch.makery.address.controller;

import ch.makery.address.model.Arc;
import ch.makery.address.model.Graph;
import ch.makery.address.model.Vertex;
import com.sun.prism.impl.VertexBuffer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;

public class PathController {

    private Vertex vertexStart;
    private Vertex vertexEnd;
    private final List<Arc> arcs = new ArrayList<>();
    private List<Vertex> path = new ArrayList<>();

     public void getPath(Vertex vertexStart) {
         for (Arc arc : vertexStart.getArcs()) {
             if (arc.getBegin() == vertexStart) {
                 vertexStart.getCircle().setStroke(Color.GREEN);
                 arcs.add(arc);
                 arc.setColor(Color.GREEN);
                 path.add(vertexStart);
                 if (arc.getEnd() == vertexEnd) {
                     arc.getEnd().getCircle().setStroke(Color.GREEN);
                     return;
                 }
                 getPath(arc.getEnd());
             }
         }

     }



     public void pathColor(Vertex vertex) {

         for (Arc arc : vertex.getArcs()) {
             if (arc.getStroke().equals(Color.GREEN)) {
                 if (vertex.getCircle().getStroke().equals(Color.BLACK)) {
                     if (vertex != vertexStart && vertex != vertexEnd) {
                         arc.setColor(Color.BLACK);
                         arc.getBegin().getCircle().setStroke(Color.BLACK);
                         arc.getEnd().getCircle().setStroke(Color.BLACK);
                         pathColor(arc.getBegin());
                     }
                 } else if (vertex == vertexEnd || vertex == vertexStart) {
                     if (arc.getBegin() == vertexStart) {
                         arc.getEnd().getCircle().setStroke(Color.BLACK);
                         pathColor(arc.getEnd());

                     } else {
                         arc.getEnd().getCircle().setStroke(Color.BLACK);
                         pathColor(arc.getBegin());
                     }
                 }
             }
         }
     }

    public void setPath(List<Vertex> path) {
        this.path = path;
    }

    public Vertex getVertexStart() {
        return vertexStart;
    }

    public void setVertexStart(Vertex vertexStart) {
        this.vertexStart = vertexStart;
    }

    public Vertex getVertexEnd() {
        return vertexEnd;
    }

    public void setVertexEnd(Vertex vertexEnd) {
        this.vertexEnd = vertexEnd;
    }


    private int minDistance(ArrayList<Integer> dist, Boolean[] sptSet) {
        int min = Integer.MAX_VALUE, min_index = -1;
        for (int v = 0; v < path.size(); v++)
            if (!sptSet[v] && dist.get(v) <= min) {
                min = dist.get(v);
                min_index = v;
            }

        return min_index;
    }

    private void printSolution(ArrayList<Integer> dist, int n, Stage stage) {
        System.out.println("Vertex   Distance from Source");
        String distance;
        if (dist.get(vertexEnd.getVertexId()) != Integer.MAX_VALUE) {
            distance = String.valueOf(dist.get(vertexEnd.getVertexId()));
        } else {
            distance = "no way";
        }
        Label firstLabel = new Label("distance between this nodes is " + distance);
        VBox secondaryLayout = new VBox();
        secondaryLayout.getChildren().addAll(firstLabel);
        Scene secondScene = new Scene(secondaryLayout, 230, 100);
        Stage newWindow = new Stage();
        newWindow.setTitle("Enter name");
        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(stage);
        newWindow.setX(stage.getX() + 100);
        newWindow.setY(stage.getY() + 100);
        newWindow.show();
        System.out.println(vertexStart.getVertexId() + " tt " + dist.get(vertexEnd.getVertexId()));
    }

    public void dijkstra(ArrayList<ArrayList<Integer>> graph, int src, Stage stage) {
        ArrayList<Integer> dist = new ArrayList<>();
        Boolean[] sptSet = new Boolean[path.size()];
        for (int i = 0; i < path.size(); i++) {
            dist.add(i, Integer.MAX_VALUE);
            sptSet[i] = false;
        }
        dist.add(src, 0);
        for (int count = 0; count < path.size() - 1; count++) {
            int u = minDistance(dist, sptSet);
            sptSet[u] = true;

            for (int v = 0; v < path.size(); v++)

                if (!sptSet[v] && graph.get(u).get(v) != 0 &&
                        dist.get(u) != Integer.MAX_VALUE && dist.get(u) + graph.get(u).get(v) < dist.get(v))
                    dist.set(v, dist.get(u) + graph.get(u).get(v));
        }
        printSolution(dist, path.size(), stage);
    }





}
