package ch.makery.address.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class MyApplication extends Application {

    public static Scene scene;
    public static Pane pane;

    @Override
    public void start(Stage primaryStage) {
        try {


            // Read file fxml and draw interface.
           Parent root = FXMLLoader.load(getClass()
                    .getResource("../sample/view/PersonOverview.fxml"));
            //root.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            pane = new Pane();
            pane.getChildren().add(root);
           //pane.setStyle(" -fx-background-color: #1d1d1d");
            //pane.getStylesheets().add("style.css");



            scene = new Scene(pane);

            MyControler.stage = primaryStage;
            primaryStage.setTitle("My Application");

            primaryStage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());;
            primaryStage.show();

            System.out.println("");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

}
