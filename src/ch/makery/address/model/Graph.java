package ch.makery.address.model;


import javafx.event.ActionEvent;

import ch.makery.address.controller.EulerCycle;
import ch.makery.address.controller.PlanarityController;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Graph extends Group implements Serializable {

    private ArrayList<ArrayList<Integer>> matrixAdjancy;
    private List<Vertex> vertices = new ArrayList<>();
    private List<Arc> arcs = new ArrayList<>();
    private transient Tab tab;
    private LinkedList<Integer> adj[];


    public ArrayList<ArrayList<Integer>> getMatrixAdjancy() {
        return matrixAdjancy;
    }

    public void removeVertex(Vertex vertex) {
        vertices.remove(vertex);

        for (ArrayList<Integer> integers : matrixAdjancy) {
            integers.remove(vertex.getVertexId());
        }

        for (int i = vertex.getVertexId() + 1; i < vertices.size(); i++) {
            vertices.get(i).setVertexId(vertices.get(i).getVertexId() - 1);
        }
        matrixAdjancy.remove(vertex.getVertexId());
    }

    public void removeArcFromMatrix(Arc arc) {
        matrixAdjancy.get(arc.getBegin().getVertexId()).set(arc.getEnd().getVertexId(), 0);

        matrixAdjancy.get(arc.getEnd().getVertexId()).set(arc.getBegin().getVertexId(), 0);

    }

    public Tab getTab() {
        return tab;
    }


    public Graph(Tab tab) {
        this.tab = tab;
        matrixAdjancy = new ArrayList<>();

    }


    public void addVertex() {

        for (int i = matrixAdjancy.size(); i < vertices.size(); i++) {

            matrixAdjancy.add(new ArrayList<>());

            for (int j = 0; j < vertices.size(); j++) {

                matrixAdjancy.get(i).add(0);


            }


            for (int k = 0; k < vertices.size() - 1; k++) {

                matrixAdjancy.get(k).add(0);

            }

        }

    }


    public void addArc(Arc arc) {

        if (arc.isUnoriented()) {
            matrixAdjancy.get(arc.getBegin().getVertexId()).set(arc.getEnd().getVertexId(), 1);

            matrixAdjancy.get(arc.getEnd().getVertexId()).set(arc.getBegin().getVertexId(), 1);
            arcs.add(arc);
        } else {
            matrixAdjancy.get(arc.getBegin().getVertexId()).set(arc.getEnd().getVertexId(), 1);
            arcs.add(arc);
        }
    }



    public void showMultipleArcs() {

        for (int first = 0; first < vertices.size(); first ++){


            for (int i = 0; i< vertices.get(first).getArcs().size(); i++){

                for (int j = 1; j<vertices.get(first).getArcs().size(); j++){
                    if(i != j ){
                        if (vertices.get(first).getArcs().get(i).getEnd().getVertexId() == vertices.get(first).getArcs().get(j).getEnd().getVertexId() && vertices.get(first).getArcs().get(i).getBegin().getVertexId() == vertices.get(first).getArcs().get(j).getBegin().getVertexId() ) {
                            vertices.get(first).getArcs().get(i).setColor(Color.PURPLE);
                            vertices.get(first).getArcs().get(j).setColor(Color.PURPLE);
                        }
                    }
                }
            }
        }
    }

    void DFSUtil(int v,boolean visited[])
    {
        // Mark the current node as visited and print it
        visited[v] = true;
        System.out.print(v+" ");

        // Recur for all the vertices adjacent to this vertex
        Iterator<Integer> i = adj[v].listIterator();
        while (i.hasNext())
        {
            int n = i.next();
            if (!visited[n])
                DFSUtil(n, visited);
        }
    }

    public boolean checkForEulerPath() {
        adj = new LinkedList[vertices.size()];
        for (int i=0; i<vertices.size(); ++i) {
            adj[i] = new LinkedList();
        }
        int oddVertex = 0;
        for (Vertex vertex :vertices){
            if(vertex.getArcs().size()%2 == 1){
                oddVertex++;
            }
        }
        if (oddVertex>2){
            return false;
        }
        boolean[] visited = {false,false,false,false,false};

        for (Vertex vertex : vertices){
            if (vertex.getArcs().size()>0){
                DFSUtil(vertex.getVertexId(), visited);
                break;
            }
        }
        for (Vertex vertex :vertices){
            if (vertex.getArcs().size()>0 && !visited[vertex.getVertexId()]){
                return false;
            }
        }
        return true;
    }


    public void showMatrix() {

        for (ArrayList<Integer> integers : matrixAdjancy) {

            for (Integer integer : integers) {

                System.out.print(integer + " ");

            }

            System.out.println();

        }

        System.out.println();
    }


    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(Vertex vertex) {
        this.vertices.add(vertex);
    }

    public void showMultipleArc(ArrayList<Arc> arcs) {

    }

    public void showInfo(Stage stage) {
        Label firstLabel = new Label("Колличество вершин в графе");
        Label verticesInfo = new Label(String.valueOf(getVertices().size()));
        Label secondLabel = new Label("Колличество дуг в граффе");
        int arcsNum = 0;
        for (Vertex vertex : vertices) {
            for (Arc arc : vertex.getArcs()) {
                arcsNum++;
            }
        }
        Label arcsInfo = new Label(String.valueOf(arcsNum / 2));
        Label isPlanarGraph = new Label("Граф является связным");

        VBox secondaryLayout = new VBox();
        secondaryLayout.getChildren().addAll(firstLabel, verticesInfo, secondLabel, arcsInfo, isPlanarGraph);
        for (int i = 0; i <matrixAdjancy.get(0).size() ; i++) {
            secondaryLayout.getChildren().add(new Label(matrixAdjancy.get(i).toString()));
        }
        Pane ppane = new Pane(secondaryLayout);
        ppane.setStyle(" -fx-background-color: #1d1d1d");

        Scene secondScene = new Scene(ppane, 230, 100);

        Stage newWindow = new Stage();
        newWindow.setTitle("Информация о графе");
        newWindow.setScene(secondScene);

        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(stage);
        newWindow.setX(stage.getX() + 100);
        newWindow.setY(stage.getY() + 100);
        secondScene.getStylesheets().add(getClass().getResource("graph.css").toExternalForm());
        secondLabel.getStyleClass().add("label");
        newWindow.show();
    }


    public void setMatrixAdjancy(ArrayList<ArrayList<Integer>> matrixAdjancy) {
        this.matrixAdjancy = matrixAdjancy;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }


    public void checkEuler(Stage stage) {
        EulerCycle eulerCycle = new EulerCycle();
        System.out.println(eulerCycle.checkForEiler(matrixAdjancy));
        ArrayList<Integer> v = new ArrayList<>();
        StringBuilder path = new StringBuilder();
        if (eulerCycle.checkForEiler(matrixAdjancy)) {
            eulerCycle.findEiler(0, v, matrixAdjancy);
            for (int i = 0; i < v.size(); i++) {
                //System.out.print(v.get(i));
                path.append(v.get(i).toString());
                if (i != v.size() - 1) {
                    path.append(" ->");
                    //System.out.print("->");
                }
            }
        } else {
            path.append("Эйлерова цикла нет");
        }
        Label firstLabel = new Label(path.toString());

        VBox secondaryLayout = new VBox();
        secondaryLayout.getChildren().addAll(firstLabel);
        Pane ppane = new Pane(secondaryLayout);
        ppane.setStyle(" -fx-background-color: #1d1d1d");
        Scene secondScene = new Scene(ppane, 230, 100);
        Stage newWindow = new Stage();
        newWindow.setTitle("Эйлеров цикл");
        newWindow.setScene(secondScene);
        secondScene.getStylesheets().add(getClass().getResource("graph.css").toExternalForm());
        firstLabel.getStyleClass().add("label");
        newWindow.initModality(Modality.NONE);
        newWindow.initOwner(stage);
        newWindow.setX(stage.getX() + 100);
        newWindow.setY(stage.getY() + 100);
        newWindow.show();


    }

    public Graph() {
    }

    public boolean isPlanar() {
        return new PlanarityController(this).verify();
    }


}