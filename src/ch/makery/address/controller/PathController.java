package ch.makery.address.controller;

import ch.makery.address.model.Arc;
import ch.makery.address.model.Graph;
import ch.makery.address.model.Node;
import ch.makery.address.model.Vertex;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathController {

    private Vertex vertexStart;
    private Vertex vertexEnd;
    private final List<Arc> arcs = new ArrayList<>();
    private List<Vertex> path = new ArrayList<>();
    private Graph graph;

    public void setGraph(Graph graph) {
        this.graph = graph;
    }


    private LinkedList<Node>[] convert(ArrayList<ArrayList<Integer>> matrixAdjancy) {
        LinkedList<Node>[] adjList = new LinkedList[path.size()];
        for (int i = 0; i < path.size(); i++) {
            adjList[i] = new LinkedList<>();
        }
        for (int i = 0; i < matrixAdjancy.get(0).size(); i++) {
            for (int j = 0; j < matrixAdjancy.get(0).size(); j++) {
                if (matrixAdjancy.get(i).get(j) == 1) {
                    adjList[i].addLast(new Node(i, j));
                }
            }
        }

        return adjList;
    }

    List<Integer> shortest = new ArrayList<>();

    private void pathsFind(int start, int end, String path, boolean[] visited) {

        String newPath = path + "->" + start;
        visited[start] = true;
        List<Integer> indexes = new ArrayList<>();
        LinkedList<Node> list = convert(graph.getMatrixAdjancy())[start];

        for (Node node : list) {

            if (node.getDestination() != end && !visited[node.getDestination()]) {
                pathsFind(node.getDestination(), end, newPath, visited);

            } else if (node.getDestination() == end) {
                Pattern p = Pattern.compile("-?\\d+");
                Matcher m = p.matcher(newPath);
                while (m.find()) {
                    System.out.println(m.group());
                    if (graph.getVertices().get(Integer.parseInt(m.group())).getCircle().getStroke() != Color.MAROON)
                        graph.getVertices().get(Integer.parseInt(m.group())).getCircle().setStroke(Color.BLUE);
                    indexes.add(Integer.parseInt(m.group()));

                }
                System.out.println(newPath + "->" + node.getDestination());
                indexes.add(node.getDestination());

                Vertex vertex1 = null;
                Vertex vertex2 = null;

                for (int i = 0; i < indexes.size() - 1; i++) {
                    for (Vertex vertex : graph.getVertices()) {
                        if (vertex.getVertexId() == indexes.get(i))
                            vertex1 = vertex;

                    }
                    for (Vertex vertex : graph.getVertices()) {
                        if (vertex.getVertexId() == indexes.get(i + 1))
                            vertex2 = vertex;

                    }
                    assert vertex1 != null;
                    if (indexes.size() == shortestPath + 1) {
                        for (Arc arc : vertex1.getArcs()) {
                            if (arc.getBegin() == vertex1 && arc.getEnd() == vertex2) {

                              /*  graph.getVertices().get(vertexStart.getVertexId()).getCircle().setStroke(Color.MAROON);
                                graph.getVertices().get(vertexEnd.getVertexId()).getCircle().setStroke(Color.MAROON);
                                */
                                arc.setColor(Color.MAROON);
                                arc.getEnd().getCircle().setStroke(Color.MAROON);
                                arc.getBegin().getCircle().setStroke(Color.MAROON);
                            }
                        }
                        shortest.clear();
                    } else
                        for (Arc arc : vertex1.getArcs()) {
                            if (arc.getBegin() == vertex1 && arc.getEnd() == vertex2 && arc.getStroke() != Color.MAROON) {
                                arc.setColor(Color.BLUE);
                            }
                        }
                }


            }

        }
        visited[start] = false;
        visited[start] = false;
        // coloring();
    }


    public void printAllPaths(int start, int end) {

        boolean[] visited = new boolean[path.size()];

        visited[start] = true;

        pathsFind(start, end, "", visited);

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

    int shortestPath = Integer.MAX_VALUE;

    private void printSolution(ArrayList<Integer> dist, int n, Stage stage) {
        System.out.println("Vertex   Distance from Source");
        String distance;
        if (dist.get(vertexEnd.getVertexId()) != Integer.MAX_VALUE) {
            distance = String.valueOf(dist.get(vertexEnd.getVertexId()));
            shortestPath = dist.get(vertexEnd.getVertexId());
        } else {
            distance = "no way";
        }
        Label firstLabel = new Label("Расстояние между узлами: " + distance);
        VBox secondaryLayout = new VBox();
        secondaryLayout.getChildren().addAll(firstLabel);
        Pane ppane = new Pane(secondaryLayout);
        ppane.setStyle(" -fx-background-color: #1d1d1d");
        Scene secondScene = new Scene(ppane, 230, 100);
        Stage newWindow = new Stage();
        newWindow.setTitle("distance");
        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(stage);
        secondScene.getStylesheets().add(getClass().getResource("jm.css").toExternalForm());
        firstLabel.getStyleClass().add("label");
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

