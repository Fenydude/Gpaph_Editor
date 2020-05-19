package ch.makery.address.model;


import javafx.beans.InvalidationListener;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.sun.javafx.sg.prism.NGCanvas.LINE_WIDTH;


public class Arc extends Line implements Serializable {


    private Vertex begin;
    private Vertex end;


    private boolean isUnoriented;
    private boolean isVisited = false;
    private CubicCurve loop;

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public void setUnoriented(boolean unoriented) {
        isUnoriented = unoriented;
    }

    private List<Line> lineList = new ArrayList<>();


    private Polygon arrow;

    public Polygon getArrow() {
        return arrow;
    }



    public void setArrow(Pane pane) {
        pane.getChildren().add(arrow);

    }

    public boolean isUnoriented() {
        return isUnoriented;
    }


    public Arc(double x1, double y1, double x2, double y2) {

        super(x1, y1, x2, y2);
        this.setStrokeWidth(2);
        this.setStrokeLineCap(StrokeLineCap.ROUND);
        if (!isUnoriented)
            arrow = new Polygon();

    }

    public void setColor(Paint color) {
        this.setStroke(color);
        arrow.setFill(color);

    }


    public Vertex getBegin() {

        return begin;

    }


    public void setBegin(Vertex begin) {

        this.begin = begin;

    }


    public Vertex getEnd() {

        return end;

    }


    public void setEnd(Vertex end) {

        this.end = end;

    }


    public void configureArrow() {
        arrow.setStrokeWidth(LINE_WIDTH);

        updateArrowShape();

        begin.getCircle().centerXProperty().addListener(change -> {
            updateArrowShape();
        });
        begin.getCircle().centerYProperty().addListener(change -> {
            updateArrowShape();
        });
        end.getCircle().centerXProperty().addListener(change -> {
            updateArrowShape();
        });
        end.getCircle().centerYProperty().addListener(change -> {
            updateArrowShape();
        });

        // Arrow lightning when mouse entered

    }

    private double headX;
    private double headY;
    private double leftX;
    private double leftY;
    private double rightX;
    private double rightY;

    private double headXMod;
    private double headYMod;
    private double leftXMod;
    private double leftYMod;
    private double rightXMod;
    private double rightYMod;

    private double cos;
    private double sin;
    private static final int ARROW_SIDE_TO_HEIGHT_ANGLE = 20;
    private static final double SIN_Y = Math.sin(Math.toRadians(ARROW_SIDE_TO_HEIGHT_ANGLE));
    private static final double COS_Y = Math.cos(Math.toRadians(ARROW_SIDE_TO_HEIGHT_ANGLE));


    private void updateArrowShape() {
        updateArrowTransform();

        arrow.getPoints().clear();
        arrow.getPoints().addAll(
                headX, headY,
                leftX, leftY,
                rightX, rightY,
                headX, headY
        );
    }

    private void updateArrowTransform() {
        // cos = |endX - startX| / sqrt((endX - startX)^2 + (endY - startY)^2)
        cos = Math.abs(end.getCircle().getCenterX() - begin.getCircle().getCenterX())
                / Math.sqrt(Math.pow(end.getCircle().getCenterX() - begin.getCircle().getCenterX(), 2)
                + Math.pow(end.getCircle().getCenterY() - begin.getCircle().getCenterY(), 2));

        // sin = sqrt(1 - cos^2)
        sin = Math.sqrt(1 - Math.pow(cos, 2));


        headXMod = 10 * cos;
        headYMod = 10 * sin;

        headX = end.getCircle().getCenterX() > begin.getCircle().getCenterX() ?
                end.getCircle().getCenterX() - headXMod
                : end.getCircle().getCenterX() + headXMod;

        headY = end.getCircle().getCenterY() > begin.getCircle().getCenterY() ?
                end.getCircle().getCenterY() - headYMod
                : end.getCircle().getCenterY() + headYMod;

        // sin (90 - a - y) = cos a * COS_Y - sin a * SIN_Y &&& cos (90 - a - y) = sin a * COS_Y + cos a * SIN_Y =>>>
        rightXMod = 10 * (cos * COS_Y - sin * SIN_Y);
        rightYMod = 10 * (sin * COS_Y + cos * SIN_Y);

        rightX = end.getCircle().getCenterX() > begin.getCircle().getCenterX() ?
                headX - rightXMod
                : headX + rightXMod;
        rightY = end.getCircle().getCenterY() > begin.getCircle().getCenterY() ?
                headY - rightYMod
                : headY + rightYMod;

        //  cos (a - y) = cos a * COS_Y + sin a * SIN_Y &&& sin (a - y) = sin a * COS_Y - cos a * SIN_Y
        leftXMod = 10 * (cos * COS_Y + sin * SIN_Y);
        leftYMod = 10 * (sin * COS_Y - cos * SIN_Y);

        leftX = end.getCircle().getCenterX() > begin.getCircle().getCenterX() ?
                headX - leftXMod
                : headX + leftXMod;
        leftY = end.getCircle().getCenterY() > begin.getCircle().getCenterY() ?
                headY - leftYMod
                : headY + leftYMod;
    }


}