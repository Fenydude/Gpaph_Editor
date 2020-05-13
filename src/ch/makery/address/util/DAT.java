package ch.makery.address.util;


import java.awt.*;
import java.io.Serializable;

public class DAT implements Serializable {

    private double newTranslateX;//сохраняемое положение по Х
    private double newTranslateY;//сохраняемое положение по Y
    private String text = "";//сохраняемый текст
    private int id;


    private double beginX;
    private double beginY;
    private double endX;
    private double endY;
    private Color color;
    private boolean isUnoriented;


    public DAT(double newTranslateX, double newTranslateY, int id, Color color) {
        this.newTranslateX = newTranslateX;
        this.newTranslateY = newTranslateY;
        this.id = id;
        this.color = color;
    }

    public DAT(double beginX, double beginY, double endX, double endY, Color color, boolean isUnoriented) {
        this.beginX = beginX;
        this.beginY = beginY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
        this.isUnoriented = isUnoriented;
    }

    public Color getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public boolean isUnoriented() {
        return isUnoriented;
    }

    public double getBeginX() {
        return beginX;
    }

    public double getBeginY() {
        return beginY;
    }

    public double getEndX() {
        return endX;
    }

    public double getEndY() {
        return endY;
    }


    public double getNewTranslateX() {
        return newTranslateX;
    }


    public double getNewTranslateY() {
        return newTranslateY;
    }

    public String getText() {
        return text;
    }

}
