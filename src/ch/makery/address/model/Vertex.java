package ch.makery.address.model;

import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Vertex extends Group implements Serializable {
    private Circle circle;
    private int vertexId;
    private Text text = new Text();
    private Text power = new Text("0");
    private int intpower = 0;
    private List<Arc> arcs = new ArrayList<>();
    private final float CIRCLE_RADIUS = 10.0f;
    private double vertexTransX, vertexTransY;


    public Vertex(double vertexTransX, double vertexTransY, Pane pane) {
        circle = new Circle();
        setVertexTransX(vertexTransX);
        setVertexTransY(vertexTransY);
        circle.setCenterX(getVertexTransX());
        circle.setStrokeWidth(2);
        circle.setCenterY(getVertexTransY());

        circle.setRadius(CIRCLE_RADIUS);

        circle.setStroke(Color.BLACK);

        circle.setPickOnBounds(true);
        circle.setFill(Color.WHITE);
        pane.getChildren().add(circle);

    }




    public Text getText() {

        return text;

    }

    public double getVertexTransX() {
        return vertexTransX;
    }

    public void setVertexTransX(double vertexTransX) {
        this.vertexTransX = vertexTransX;
    }

    public double getVertexTransY() {
        return vertexTransY;
    }

    public void setVertexTransY(double vertexTransY) {
        this.vertexTransY = vertexTransY;
    }

    public void setTextInPane(Pane pane) {

        pane.getChildren().add(text);

    }


    public void setText(Text text) {

        this.text = text;

    }

    public int getVertexId() {
        return vertexId;
    }

    public void setVertexId(int vertexId) {
        this.vertexId = vertexId;
    }

    public void removeArc(Arc arc) {
        arcs.remove(arc);
    }

    public void addArc(Arc arc) {
        arcs.add(arc);
    }

    public List<Arc> getArcs() {
        return arcs;
    }



    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public void setArcs(List<Arc> arcs) {
        this.arcs = arcs;
    }

    public void updatePower() {
        power.setText(Integer.toString(arcs.size()));
    }

    public void setPowerInPane(Pane pane) {
        pane.getChildren().add(power);
    }

    public Text getPower() {
        power.setFill(Color.CORNFLOWERBLUE);
        return power;
    }

}
