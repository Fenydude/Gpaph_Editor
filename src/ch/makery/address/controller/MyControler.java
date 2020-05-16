package ch.makery.address.controller;


import java.io.IOException;

import java.net.URL;

import java.util.*;
import java.util.List;


import ch.makery.address.model.Arc;

import ch.makery.address.model.Graph;

import ch.makery.address.model.Vertex;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

import javafx.animation.Transition;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.ActionEvent;

import javafx.event.Event;
import javafx.event.EventHandler;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.Cursor;

import javafx.scene.Scene;

import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import javafx.scene.input.*;

import javafx.scene.layout.Pane;

import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;

import javafx.scene.shape.Circle;

import javafx.scene.transform.Translate;

import javafx.stage.Modality;

import javafx.stage.Stage;
import javafx.util.Duration;

import static javafx.scene.input.KeyCode.D;
import static javafx.scene.input.KeyCode.DELETE;


public class MyControler implements Initializable {


    private Translate translate = new Translate();

    public int countCircle = 0;

    private Graph graph;
    private Pane pane;


    public static Stage stage;


    private double x1, x2 = 0;

    private double y1, y2 = 0;


    double orgSceneX, orgSceneY;

    double orgTranslateX, orgTranslateY;


    @FXML

    private Button penCircle = new Button();

    @FXML
    private MenuItem save;

    @FXML
    private MenuItem open;


    @FXML

    private MenuItem showMultipleArcBut = new MenuItem();

    @FXML

    private MenuItem showPowerVert = new MenuItem();

    @FXML

    private MenuItem newPane = new MenuItem();


    @FXML

    private Button penLine = new Button();


    @FXML

    private Button transform = new Button();


    @FXML

    private Button unorientedArc = new Button();


    @FXML

    private TabPane tabPane = new TabPane();

    @FXML

    private Button loop = new Button();

    @FXML

    private Tab tab = new Tab();
    @FXML

    public Button colorChange = new Button();

    @FXML
    public MenuItem showGraphInfo = new MenuItem();

    private List<Graph> graphs = new ArrayList<>();

    private List<Pane> panes = new ArrayList<>();

    private ArrayList<Circle> circleArray = new ArrayList<>();
    private List<Button> buttons = new ArrayList<>();
    private DoubleProperty fontSize = new SimpleDoubleProperty(10);
    private IntegerProperty blues = new SimpleIntegerProperty(50);


    private void setGraphName(Tab tab) {
        Label secondLabel = new Label("Enter graph name");
        TextField textField = new TextField("Enter name");
        textField.setMinWidth(120);
        Button button1 = new Button("Button with Text");
        VBox secondaryLayout = new VBox();
        secondaryLayout.getChildren().addAll(secondLabel, textField, button1);
        Scene secondScene = new Scene(secondaryLayout, 230, 100);
        Stage newWindow = new Stage();
        newWindow.setTitle("Enter name");
        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(stage);
        button1.setOnAction(actionEvent -> {
            tab.setText(textField.getText());
            newWindow.close();

        });
        newWindow.show();
    }

    public void showPowerVertAction(ActionEvent actionEvent) throws InterruptedException {
        for (Graph graph : graphs) {
            if (graph.getTab().isSelected()) {
                for (Vertex vertex : graph.getVertices()) {

                    fontSize.bind(pane.widthProperty().add(pane.heightProperty()).divide(50));
                    vertex.getPower().styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"
                            , "-fx-base: rgb(100,100,", blues.asString(), ");"));

                    vertex.setPowerInPane((Pane) graph.getTab().getContent());
                    vertex.getPower().setX(vertex.getCircle().getCenterX() - 4);
                    vertex.getPower().setY(vertex.getCircle().getCenterY() + 4);

                    Timeline timeline = new Timeline();
                    timeline.setCycleCount(1);
                    Duration duration = new Duration(1000);
                    KeyValue kvy = new KeyValue(vertex.getPower().yProperty(), vertex.getCircle().getCenterY() - 15);
                    KeyFrame kfy = new KeyFrame(duration, kvy);
                    timeline.getKeyFrames().add(kfy);
                    timeline.play();
                    timeline.setOnFinished(event -> {
                        Timeline timeline1 = new Timeline();
                        timeline1.setCycleCount(1);
                        Duration duration1 = new Duration(10000);

                        KeyFrame kfy1 = new KeyFrame(duration1);
                        timeline1.getKeyFrames().add(kfy1);
                        timeline1.play();
                        timeline1.setOnFinished(eventt -> {
                            Timeline timeline2 = new Timeline();
                            timeline2.setCycleCount(1);
                            Duration duration2 = new Duration(900);
                            KeyValue kvy2 = new KeyValue(vertex.getPower().yProperty(), vertex.getCircle().getCenterY());
                            KeyFrame kfy2 = new KeyFrame(duration2, kvy2);
                            timeline2.getKeyFrames().add(kfy2);
                            timeline2.play();
                            timeline2.setOnFinished(even -> pane.getChildren().remove(vertex.getPower()));
                        });
                    });


                }
            }
        }
    }


    @Override

    public void initialize(URL location, ResourceBundle resources) {
        buttons.add(colorChange);
        buttons.add(transform);
        buttons.add(penCircle);
        buttons.add(unorientedArc);
        buttons.add(penLine);
        buttons.add(loop);


        pane = new Pane();
        setGraphName(tabPane.getTabs().get(0));
        tabPane.getTabs().get(0).setContent(pane);
        graph = new Graph(tabPane.getTabs().get(0));
        graphs.add(graph);
        panes.add(pane);


    }


    public void newPaneAction(ActionEvent event) {
        Tab tab1 = new Tab();
        setGraphName(tab1);

        Label label = new Label("This is newTab ");

        tab1.setContent(label);

        tabPane.getTabs().add(tab1);

        Pane pane = new Pane();

        graphs.add(new Graph(tab1));
        panes.add(pane);
        circleArray = new ArrayList<>();
        tabPane.getTabs().get(tabPane.getTabs().size() - 1).setContent(pane);

    }

    private FileWorkController fileWorkController = new FileWorkController();

    public void saveAction(ActionEvent actionEvent) {
        for (Graph graph : graphs) {
            if (graph.getTab().isSelected()) {
                try {
                    Set<Arc> arcs = new HashSet<>();
                    for (Vertex vertex : graph.getVertices()) {
                        arcs.addAll(vertex.getArcs());
                    }
                    fileWorkController.saveNode(graph.getVertices(), arcs, graph.getTab().getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void saveAsAction(ActionEvent actionEvent) {

    }

    public void openAction(ActionEvent actionEvent) {
        for (Graph graph : graphs) {
            if (graph.getTab().isSelected()) {
                try {
                    graph = fileWorkController.openNode(graph.getTab(), graph);
                    for (Vertex vertex : graph.getVertices()) {
                        circleArray.add(vertex.getCircle());
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    public void showMultipleArc(ActionEvent event) {

        for (Graph graph : graphs) {
            if (graph.getTab().isSelected()) {
                   graph.showMultipleArcs();

            }
        }


    }


    // Ивент нажатия кнопки

    public void penCircleAction(ActionEvent event) {

        MyApplication.scene.setCursor(Cursor.DEFAULT);

        for (Button button : buttons) {
            button.setDisable(button.getText().equals("Circle"));
        }

        for (Tab tab1 : tabPane.getTabs()) {

            tab1.getContent().addEventFilter(MouseEvent.MOUSE_CLICKED, drawCircle);


        }

        for (Circle circle : circleArray) {

            circle.setOnMousePressed(null);

            circle.setOnMouseDragged(null);

        }


    }

    public void colorChange(ActionEvent event) {

        for (Tab tab1 : tabPane.getTabs()) {
            MyApplication.scene.setCursor(Cursor.DEFAULT);
            for (Button button : buttons) {
                button.setDisable(button.getText().equals("color"));
            }
            tab1.getContent().addEventFilter(MouseEvent.MOUSE_RELEASED, arcAndCircleColorChange);


        }
    }

    public void loop(ActionEvent event) {

        for (Tab tab1 : tabPane.getTabs()) {
            MyApplication.scene.setCursor(Cursor.CROSSHAIR);
            for (Button button : buttons) {
                button.setDisable(button.getText().equals("loop"));
            }
            for (Graph graph : graphs) {
                if (graph.getTab().isSelected()) {
                    tab1.getContent().addEventFilter(MouseEvent.MOUSE_RELEASED, new LoopCreate(graph, loop, pane));
                }
            }

        }
    }

    //Ивент нажатия кнопки

    public void penLineAction(ActionEvent event) {

        MyApplication.scene.setCursor(Cursor.CROSSHAIR);

        for (Button button : buttons) {
            button.setDisable(button.getText().equals("Arc"));
        }

        for (Circle circle : circleArray) {

            circle.setOnMousePressed(null);

            circle.setOnMouseDragged(null);

        }

        for (Graph graph : graphs) {
            if (graph.getTab().isSelected()) {
                graph.showMatrix();

            }
        }

        for (Circle circle : circleArray) {

            circle.addEventFilter(MouseEvent.MOUSE_CLICKED, lineDrawEvent);

        }

    }


    Runnable choosing = new Runnable() {

        @Override

        public void run() {

            for (Graph graph : graphs) {
                if (graph.getTab().isSelected()) {

                    for (Vertex vertex : graph.getVertices()) {

                        vertex.getCircle().setOnMousePressed(circleOnMousePressedEventHandler.get());
                        vertex.getCircle().setOnMouseEntered(event -> {
                            if (!vertex.getCircle().getStroke().equals(Color.ORANGERED))
                                vertex.getCircle().setStroke(Color.DARKORANGE);
                        });
                        vertex.getCircle().setOnMouseExited(event -> {
                            if (!vertex.getCircle().getStroke().equals(Color.ORANGERED))
                                vertex.getCircle().setStroke(Color.BLACK);
                        });
                        vertex.getCircle().setOnMouseDragged(circleOnMouseDraggedEventHandler);

                        for (Arc arc : vertex.getArcs()) {
                            arc.setOnMouseDragged(transLine);
                            arc.setOnMouseEntered(event -> {
                                if (!arc.getStroke().equals(Color.ORANGERED))
                                    arc.setColor(Color.DARKORANGE);
                            });
                            arc.setOnMousePressed(e -> {
                                arc.setColor(Color.ORANGERED);
                                graph.getVertices().forEach(vertex1 -> {
                                    vertex1.getCircle().setStroke(Color.BLACK);
                                    vertex1.getArcs().forEach(arc1 -> {
                                        if (arc != arc1)
                                            arc1.setColor(Color.BLACK);
                                    });
                                });
                                arc.getScene().setOnKeyPressed(e1 -> {
                                    if (e1.getCode() == KeyCode.DELETE) {
                                        arc.getBegin().getArcs().remove(arc);
                                        arc.getEnd().getArcs().remove(arc);
                                        pane.getChildren().removeAll(arc.getArrow());
                                        pane.getChildren().remove(arc);
                                    }
                                });
                            });
                            arc.setOnMouseExited(event -> {
                                if (!arc.getStroke().equals(Color.ORANGERED))
                                    arc.setColor(Color.BLACK);
                            });
                        }
                    }
                }
            }
        }
    };

    public void transformAction(ActionEvent event) {

        MyApplication.scene.setCursor(Cursor.DEFAULT);
        for (Button button : buttons) {
            button.setDisable(button.getText().equals("Transform"));
        }
        choosing.run();
    }


    public void unorientedArcAction(ActionEvent actionEvent) {
        MyApplication.scene.setCursor(Cursor.CROSSHAIR);
        for (Button button : buttons) {
            button.setDisable(button.getText().equals("BiArc"));
        }


        for (Circle circle : circleArray) {

            circle.setOnMousePressed(null);

            circle.setOnMouseDragged(null);

        }


        for (Circle circle : circleArray) {

            circle.addEventFilter(MouseEvent.MOUSE_CLICKED, lineDrawEvent);

        }

    }


    EventHandler<MouseEvent> trans = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {
            Dragboard db = circleArray.get(0).startDragAndDrop(TransferMode.ANY);
        }
    };


    //Ивент рисования круга
    EventHandler<MouseEvent> drawCircle = new EventHandler<MouseEvent>() {

        @Override

        public void handle(MouseEvent e) {


            if (penCircle.isDisable() && e.getX() < 550 && e.getY() < 400) {
                for (Graph graph : graphs) {
                    if (graph.getTab().isSelected()) {


                        Pane pane = (Pane) graph.getTab().getContent();
                        Vertex vertex = new Vertex(e.getX(), e.getY(), pane);
                        graph.getVertices().add(vertex);
                        circleArray.add(vertex.getCircle());
                        vertex.setTextInPane((Pane) graph.getTab().getContent());
                        vertex.setVertexId(graph.getVertices().size() - 1);
                        vertex.getText().setX(vertex.getCircle().getCenterX() + 10);
                        vertex.getText().setY(vertex.getCircle().getCenterY() + 10);


                        graph.addVertex();

                        // panes.get(1).getChildren().add(circle);


                    }
                }


            }
        }

    };
    EventHandler<MouseEvent> arcAndCircleColorChange = event -> {
        if (colorChange.isDisable()) {
            for (Graph graph : graphs) {
                if (graph.getTab().isSelected()) {
                    for (Vertex vertex : graph.getVertices()) {

                        for (Arc arc : vertex.getArcs()) {

                            arc.setOnMouseReleased(event1 -> {
                                // arc.setStroke(Color.DARKORANGE);
                                arc.setColor(Color.ORANGERED);
                                final ColorPicker colorPicker = new ColorPicker();
                                colorPicker.setValue(Color.RED);
                                Pane pane = (Pane) graph.getTab().getContent();
                                pane.getChildren().add(colorPicker);
                                colorPicker.setOnAction(event11 -> {
                                    arc.setColor(colorPicker.getValue());
                                    pane.getChildren().remove(colorPicker);
                                });

                            });

                        }
                        vertex.getCircle().setOnMouseReleased(e -> {
                            vertex.getCircle().setStroke(Color.ORANGERED);
                            final ColorPicker colorPicker = new ColorPicker();
                            colorPicker.setValue(Color.RED);
                            Pane pane = (Pane) graph.getTab().getContent();
                            pane.getChildren().add(colorPicker);
                            colorPicker.setOnAction(event11 -> {
                                vertex.getCircle().setFill(colorPicker.getValue());
                                pane.getChildren().remove(colorPicker);
                                vertex.getCircle().setStroke(Color.BLACK);
                            });
                        });
                    }
                }
            }
        }
    };


    SimpleObjectProperty<EventHandler<MouseEvent>> circleOnMousePressedEventHandler

            = new SimpleObjectProperty<>(this, "circleOnMousePressedEventHandler", new EventHandler<MouseEvent>() {


        @Override

        public void handle(MouseEvent t) {

            if (transform.isDisable()) {
                for (Graph graph : graphs) {
                    if (graph.getTab().isSelected()) {
                        Circle circle = (Circle) t.getSource();
                        graph.getVertices().forEach(vertex -> {
                            vertex.getArcs().forEach(arc1 -> {
                                arc1.setColor(Color.BLACK);
                            });
                        });
                        for (Vertex vertex : graph.getVertices()) {

                            if (vertex.getCircle() == circle) {
                                //vertex.getCircle().setFill(Color.GREEN);

                                vertex.getCircle().setStroke(Color.ORANGERED);
                                vertex.getCircle().getScene().setOnKeyPressed(e -> {
                                    if (e.getCode() == KeyCode.I) {
                                        Label secondLabel = new Label("Enter name vertex");
                                        TextField textField = new TextField("Enter name");
                                        textField.setMinWidth(120);
                                        Button button1 = new Button("Button with Text");
                                        VBox secondaryLayout = new VBox();
                                        secondaryLayout.getChildren().addAll(secondLabel, textField, button1);
                                        Scene secondScene = new Scene(secondaryLayout, 230, 100);
                                        // New window (Stage)
                                        Stage newWindow = new Stage();
                                        newWindow.setTitle("Enter name");
                                        newWindow.setScene(secondScene);
                                        // Specifies the modality for new window.
                                        newWindow.initModality(Modality.WINDOW_MODAL);
                                        // Specifies the owner Window (parent) for new window
                                        newWindow.initOwner(stage);
                                        // Set position of second window, related to primary window.
                                        newWindow.setX(stage.getX() + 100);

                                        newWindow.setY(stage.getY() + 100);


                                        button1.setOnAction(actionEvent -> {

                                            vertex.getText().setText(textField.getText());

                                            vertex.getText().styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"
                                                    , "-fx-base: rgb(100,100,", blues.asString(), ");"));


                                            newWindow.close();

                                        });
                                        newWindow.show();
                                    } else if (e.getCode() == DELETE) {
                                        Pane pane = (Pane) graph.getTab().getContent();
                                        try {
                                            circleArray.remove(vertex.getCircle());
                                            pane.getChildren().remove(vertex.getCircle());
                                            pane.getChildren().removeAll(vertex.getArcs());
                                            if (!vertex.getArcs().isEmpty()) {
                                                for (Arc arc : vertex.getArcs()) {
                                                    pane.getChildren().removeAll(arc.getArrow());
                                                }
                                                vertex.getArcs().clear();

                                                graph.removeVertex(vertex);
                                            }
                                        } catch (Exception exception) {
                                            exception.printStackTrace();
                                        }
                                    }

                                });
                            } else {
                                vertex.getCircle().setStroke(Color.BLACK);
                            }


                        }

                    }
                }
            }

        }

    });


    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {

                    for (Graph graph : graphs) {

                        if (graph.getTab().isSelected()) {


                            Circle circle = (Circle) t.getSource();

                            circle.setCenterX(t.getX());

                            circle.setCenterY(t.getY());


                            for (Vertex vertex : graph.getVertices()) {
                                if (vertex.getCircle() == circle) {
                                    vertex.setVertexTransX(t.getX());
                                    vertex.setVertexTransY(t.getY());
                                }

                                vertex.getText().setX(vertex.getCircle().getCenterX() + 10);
                                vertex.getText().setY(vertex.getCircle().getCenterY() + 10);




                                vertex.updatePower();
                                vertex.getPower().setX(vertex.getCircle().getCenterX() - 4);
                                vertex.getPower().setY(vertex.getCircle().getCenterY() - 15);
                                if (vertex.getCircle() == circle && vertex.getArcs() != null) {

                                    for (Arc arc : vertex.getArcs()) {

                                        if (arc.getBegin().getCircle() == vertex.getCircle()) {

                                            arc.setStartX(circle.getCenterX());

                                            arc.setStartY(circle.getCenterY());

                                        } else if (arc.getEnd().getCircle() == vertex.getCircle()) {

                                            arc.setEndX(circle.getCenterX());

                                            arc.setEndY(circle.getCenterY());

                                        }

                                        arc.updateArrow();

                                    }

                                }
                                if (vertex.getCircle() != circle) {
                                    vertex.getCircle().setStroke(Color.BLACK);

                                }

                            }
                        }
                    }
                }

            };


    EventHandler<MouseEvent> lineDrawEvent = new EventHandler<MouseEvent>() {
        Arc arc = new Arc(0, 0, 0, 0);

        @Override
        public void handle(MouseEvent t) {
            if (penLine.isDisable() || unorientedArc.isDisable()) {
                for (Graph graph : graphs) {
                    if (graph.getTab().isSelected()) {

                        if (x1 == 0 && y1 == 0) {

                            Circle circle = (Circle) t.getSource();

                            for (Vertex vertex : graph.getVertices()) {

                                if (vertex.getCircle() == circle) {

                                    x1 = t.getX();

                                    y1 = t.getY();

                                    arc.setBegin(vertex);

                                    arc.setStartX(vertex.getCircle().getCenterX());

                                    arc.setStartY(vertex.getCircle().getCenterY());

                                    vertex.addArc(arc);
                                }
                            }
                        } else if (x2 == 0 && y2 == 0) {

                            x2 = t.getX();

                            y2 = t.getY();

                            Circle circle = (Circle) (t.getSource());

                            for (Vertex vertex : graph.getVertices()) {

                                if (vertex.getCircle() == circle) {


                                    arc.setEnd(vertex);
                                    vertex.addArc(arc);
                                    arc.setEndX(vertex.getCircle().getCenterX());

                                    arc.setEndY(vertex.getCircle().getCenterY());
                                    //vertex.getArcs().add(arc);
                                    Pane pane = (Pane) graph.getTab().getContent();
                                    pane.getChildren().add(arc);
                                    if (penLine.isDisable()) {
                                        arc.setArrow(pane);
                                    } else if (unorientedArc.isDisable()) {
                                        arc.setUnorientedArrow(pane);
                                        arc.updateUnorientedArrow();
                                    }


                                    // panes.get(0).getChildren().add(arc);

                                    x1 = 0;

                                    x2 = 0;

                                    y1 = 0;

                                    y2 = 0;
                                    arc.updateArrow();
                                    graph.addArc(arc);
                                    //  arc.updateUnorientedArrow();

                                   
                                    this.arc = new Arc(x1, y1, x2, y2);

                                }
                                vertex.updatePower();

                            }

                        }


                    }
                }
            }

        }

    };


    EventHandler<MouseEvent> transLine =

            new EventHandler<MouseEvent>() {


                @Override

                public void handle(MouseEvent t) {

                    for (Graph graph : graphs) {


                        Arc arc = (Arc) t.getSource();
                        arc.setColor(Color.DARKORANGE);

                        if (Math.abs(t.getX() - arc.getStartX()) < 25 && Math.abs(t.getY() - arc.getStartY()) < 25) {

                            arc.setStartX(t.getX());

                            arc.setStartY(t.getY());
                            graph.removeArcFromMatrix(arc);
                            arc.getBegin().removeArc(arc);
                            for (Vertex vertex : graph.getVertices()) {

                                if (Math.abs(arc.getStartX() - vertex.getCircle().getCenterX()) < 15 && Math.abs(arc.getStartY() - vertex.getCircle().getCenterY()) < 15) {

                                    arc.setStartX(vertex.getCircle().getCenterX());

                                    arc.setStartY(vertex.getCircle().getCenterY());

                                    arc.setBegin(vertex);

                                    vertex.addArc(arc);

                                }

                            }

                        } else if (Math.abs(t.getX() - arc.getEndX()) < 25 && Math.abs(t.getY() - arc.getEndY()) < 25) {

                            arc.setEndX(t.getX());

                            arc.setEndY(t.getY());
                            graph.removeArcFromMatrix(arc);
                            arc.getEnd().removeArc(arc);
                            for (Vertex vertex : graph.getVertices()) {

                                if (Math.abs(arc.getEndX() - vertex.getCircle().getCenterX()) < 15 && Math.abs(arc.getEndY() - vertex.getCircle().getCenterY()) < 15) {

                                    arc.setEndX(vertex.getCircle().getCenterX());

                                    arc.setEndY(vertex.getCircle().getCenterY());

                                    arc.setEnd(vertex);

                                    vertex.addArc(arc);


                                }

                            }

                        }
                        for (Vertex vertex : graph.getVertices()) {
                            vertex.updatePower();
                        }

                        arc.updateArrow();
                        graph.addArc(arc);
                    }
                }
            };







    public void showGraphInfo(ActionEvent event) {
        for (Graph graph : graphs) {
            if (graph.getTab().isSelected()) {
                graph.showInfo(stage);
            }
        }


    }


    public void eilerWay(ActionEvent actionEvent) {
        for (Graph graph : graphs){
            if(graph.getTab().isSelected()){
              if(graph.checkForEulerPath()){
                  System.out.println("Krasava");
              }

            }
        }
    }
}
