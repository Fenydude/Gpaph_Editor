package ch.makery.address.controller;

import ch.makery.address.model.Arc;
import ch.makery.address.model.Graph;
import ch.makery.address.model.Vertex;
import ch.makery.address.util.ColorUtil;
import ch.makery.address.util.DAT;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileWorkController {


    //метод для сохранения объектов
    public void saveNode(List<Vertex> vertecies, Set<Arc> arcs, String fileName) throws IOException {
        Set<DAT> dBList1 = new HashSet<>();
        for (Vertex vertex : vertecies) {
            dBList1.add(new DAT(
                    vertex.getVertexTransX(),
                    vertex.getVertexTransY(),
                    vertex.getVertexId(),
                    ColorUtil.fxToAwt((Color) vertex.getCircle().getFill())
            ));
        }
        for (Arc arc : arcs) {
            dBList1.add(new DAT(
                    arc.getBegin().getVertexTransX(),
                    arc.getBegin().getVertexTransY(),
                    arc.getEnd().getVertexTransX(),
                    arc.getEnd().getVertexTransY(),
                    ColorUtil.fxToAwt((Color) arc.getStroke()),
                    arc.isUnoriented()
            ));
        }
        try (ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(fileName + ".shelt"))) {
            ous.writeObject(dBList1);//сохраняем объект с данными о Node

        }
    }

    //метод для восстановления объектов
    public Graph openNode(Tab tab, Graph graph) throws IOException, ClassNotFoundException {
        final FileChooser fileChooser = new FileChooser();
        Pane root = new Pane();
        tab.setContent(root);
        Set<Vertex> dragList1 = new HashSet<>();
        Set<Arc> dragList2 = new HashSet<>();
        Set<DAT> datList;
        File file = fileChooser.showOpenDialog(new Stage());
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            datList = (HashSet<DAT>) ois.readObject();
        }
        for (DAT dat : datList) {
            if (dat.getNewTranslateX() != 0) {
                Vertex vertex = new Vertex(dat.getNewTranslateX(), dat.getNewTranslateY(), root);
                vertex.setVertexId(dat.getId());
                vertex.getCircle().setFill(ColorUtil.awtToFx(dat.getColor()));
                dragList1.add(vertex);
            } else if (dat.getBeginX() != 0) {
                Arc arc = new Arc(dat.getBeginX(), dat.getBeginY(), dat.getEndX(), dat.getEndY());
                arc.setUnoriented(dat.isUnoriented());
                arc.setColor(ColorUtil.awtToFx(dat.getColor()));
                dragList2.add(arc);
            }
        }
        for (Vertex vertex : dragList1) {
            graph.getVertices().add(vertex);
            graph.addVertex();
            for (Arc arc : dragList2) {
                if (vertex.getCircle().getCenterX() == arc.getStartX() && vertex.getCircle().getCenterY() == arc.getStartY()) {
                    vertex.addArc(arc);
                    arc.setBegin(vertex);
                } else if (vertex.getCircle().getCenterX() == arc.getEndX() && vertex.getCircle().getCenterY() == arc.getEndY()) {
                    arc.setEnd(vertex);
                    if (!arc.isUnoriented()) {
                        arc.setArrow(root);
                    }
                    arc.toBack();
                    vertex.addArc(arc);
                }
            }
        }
        dragList1.removeIf(e -> {
            for (Arc arc : e.getArcs()) {
                return arc.getEnd() == null;
            }
            return false;
        });

        graph.getVertices().removeIf(e -> {
            for (Arc arc : e.getArcs()) {
                return arc.getEnd() == null;
            }
            return false;
        });
        dragList2.removeIf(e -> e.getEnd() == null);
        dragList2.forEach(graph::addArc);
        root.setBackground(new Background(new BackgroundFill(Color.SNOW, CornerRadii.EMPTY, new Insets(10, 10, 0, 10))));

        root.getChildren().removeAll(datList);
        root.getChildren().addAll(dragList1);
        root.getChildren().addAll(dragList2);
        return graph;
    }

}
