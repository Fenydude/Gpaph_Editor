package ch.makery.address.model;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Graph extends Group implements Serializable {

    private ArrayList<ArrayList<Integer>> matrixAdjancy;
    private List<Vertex> vertices = new ArrayList<>();
    private transient Tab tab;


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
        } else {
            matrixAdjancy.get(arc.getBegin().getVertexId()).set(arc.getEnd().getVertexId(), 1);
        }
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
        Label firstLabel = new Label("Vertices");
        Label verticesInfo = new Label(String.valueOf(getVertices().size()));
        Label secondLabel = new Label("Arcs");
        int arcsNum=0;
        for (Vertex vertex : vertices) {
            for(Arc arc:vertex.getArcs()){
                arcsNum++;
            }
        }
        Label arcsInfo = new Label(String.valueOf(arcsNum/2));
        VBox secondaryLayout = new VBox();
        secondaryLayout.getChildren().addAll(firstLabel,verticesInfo,secondLabel,arcsInfo);
        Scene secondScene = new Scene(secondaryLayout, 230, 100);
        Stage newWindow = new Stage();
        newWindow.setTitle("Enter name");
        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(stage);
        newWindow.setX(stage.getX() + 100);
        newWindow.setY(stage.getY() + 100);
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
}