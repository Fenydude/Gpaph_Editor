package ch.makery.address.controller;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyApplication extends Application {

    public static Scene scene;
    public static Pane pane;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Read file fxml and draw interface.
            Parent root = FXMLLoader.load(getClass()
                    .getResource("/ch/makery/address/view/PersonOverview.fxml"));
            pane = new Pane();
            pane.getChildren().add(root);
            scene = new Scene(pane);
            primaryStage.setTitle("My Application");
            primaryStage.setScene(scene);
            primaryStage.show();
            MyControler.stage = primaryStage;
            //Creating the mouse event handler

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

}
