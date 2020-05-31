package ch.makery.address.controller;


import ch.makery.address.model.Arc;
import ch.makery.address.model.Graph;
import ch.makery.address.model.Vertex;
import ch.makery.address.util.Tutorials;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static javafx.scene.input.KeyCode.DELETE;


public class MyControler implements Initializable {

    @FXML
    public MenuItem showDistance = new MenuItem();
    @FXML
    public MenuItem makePlanar = new MenuItem();
    @FXML
    public MenuItem checkForEuler = new MenuItem();
    @FXML
    public MenuItem tutorial = new MenuItem();
    @FXML
    public MenuItem clear = new MenuItem();
    @FXML
    public MenuItem findPaths = new MenuItem();
    @FXML
    public MenuItem findVertex = new MenuItem();
    @FXML
    public MenuItem content = new MenuItem();
    private Translate translate = new Translate();


    private String color = "transparent";


    public int countCircle = 0;

    private Graph graph;
    private Pane pane;


    public static Stage stage;


    private double x1, x2 = 0;

    private double y1, y2 = 0;
    @FXML

    private Button penCircle = new Button();


    @FXML
    private MenuItem save;

    @FXML
    private MenuItem open;


    @FXML

    private MenuItem newPane = new MenuItem();


    @FXML

    private Button penLine = new Button();


    @FXML

    private Button transform = new Button();

@FXML
private Button newCir = new Button();
    @FXML

    private Button unorientedArc = new Button();

@FXML
private Menu menu = new Menu();

    @FXML
    private  MenuBar menuBar = new MenuBar();
    @FXML
    private TabPane tabPane = new TabPane();

    @FXML

    private Button loop = new Button();

    @FXML

    private Tab tab = new Tab();
    @FXML

    public Button colorChange = new Button();

    @FXML

    private MenuItem showPowerVert = new MenuItem();

    @FXML
    public MenuItem showGraphInfo = new MenuItem();

    private List<Graph> graphs = new ArrayList<>();

    private List<Pane> panes = new ArrayList<>();

    private ArrayList<Circle> circleArray = new ArrayList<>();
    private List<Button> buttons = new ArrayList<>();
    private DoubleProperty fontSize = new SimpleDoubleProperty(10);
    private IntegerProperty blues = new SimpleIntegerProperty(50);


    private ObservableList<Object> langs = FXCollections.observableArrayList(penCircle);


    private void setGraphName(Tab tab) {
        Label secondLabel = new Label("Ввдите имя графа");
        TextField textField = new TextField("Введите имя графа");
        textField.setMinWidth(120);
        Button button1 = new Button("Выполнить");
        VBox secondaryLayout = new VBox();
        secondaryLayout.getChildren().addAll(secondLabel, textField, button1);

        Pane ppane = new Pane(secondaryLayout);
        ppane.setStyle(" -fx-background-color: #1d1d1d");
        Scene secondScene = new Scene(ppane, 230, 100);

        Stage newWindow = new Stage();
        newWindow.setTitle("Введите имя графа");
        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(stage);
        secondScene.getStylesheets().add(getClass().getResource("jm.css").toExternalForm());
        button1.getStyleClass().add("button");
        secondLabel.getStyleClass().add("label");
        button1.setOnAction(actionEvent -> {
            tab.setText(textField.getText());
            newWindow.close();

        });
        newWindow.show();
    }

    Image imagecirc = new Image(getClass().getResourceAsStream("circ.png"));
    Image imagenewcirc = new Image(getClass().getResourceAsStream("circ181.png"));
    Image imagenearrow = new Image(getClass().getResourceAsStream("arrow.png"));
    Image imageneline = new Image(getClass().getResourceAsStream("line.png"));
    Image imagenedrag = new Image(getClass().getResourceAsStream("drag.png"));
    Image imagenefill = new Image(getClass().getResourceAsStream("fill.png"));

    @FXML
    private ToolBar toolb = new ToolBar();


    @Override

    public void initialize(URL location, ResourceBundle resources) {
        buttons.add(colorChange);
        buttons.add(transform);
        buttons.add(penCircle);
        buttons.add(unorientedArc);
        buttons.add(penLine);
        buttons.add(loop);
        buttons.add(newCir);

        penCircle.graphicProperty().setValue(new ImageView(imagecirc));
        newCir.graphicProperty().setValue(new ImageView(imagenewcirc));
        penLine.graphicProperty().setValue(new ImageView(imagenearrow));
        unorientedArc.graphicProperty().setValue(new ImageView(imageneline));
        transform.graphicProperty().setValue(new ImageView(imagenedrag));
        colorChange.graphicProperty().setValue(new ImageView(imagenefill));

        //toolb.getStyleClass().add("bbb");
        //tab.getStyleClass().add("bbb");
        pane = new Pane();
        setGraphName(tabPane.getTabs().get(0));
        tabPane.getTabs().get(0).setContent(pane);
        pane.setBackground(new Background(new BackgroundFill(Color.SNOW, CornerRadii.EMPTY, new Insets(10, 10, 0, 10))));
        graph = new Graph(tabPane.getTabs().get(0));
        graphs.add(graph);
        panes.add(pane);


    }


    public void newPaneAction(ActionEvent event) {
        Tab tab1 = new Tab();
        //tab1.getStyleClass().add("bbb");
        setGraphName(tab1);

        Label label = new Label("This is newTab ");

        tab1.setContent(label);

        tabPane.getTabs().add(tab1);

        Pane pane = new Pane();
        pane.setBackground(new Background(new BackgroundFill(Color.SNOW, CornerRadii.EMPTY, new Insets(10, 10, 0, 10))));
        graphs.add(new Graph(tab1));
        panes.add(pane);
        circleArray = new ArrayList<>();
        tabPane.getTabs().get(tabPane.getTabs().size() - 1).setContent(pane);

    }

    private final FileWorkController fileWorkController = new FileWorkController();

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

    public void openAction(ActionEvent actionEvent) {
        for (Graph graph : graphs) {
            if (graph.getTab().isSelected()) {
                try {
                    graph = fileWorkController.openNode(graph.getTab(), graph);
                    for (Vertex vertex : graph.getVertices()) {
                        circleArray.add(vertex.getCircle());
                        for (Arc arc : vertex.getArcs()) {
                            if (!arc.isUnoriented())
                                arc.configureArrow();
                            arc.toBack();
                        }
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
            button.setDisable(button.getText().equals("c"));
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
                button.setDisable(button.getText().equals("l"));
            }
            tab1.getContent().addEventFilter(MouseEvent.MOUSE_RELEASED, arcAndCircleColorChange);


        }
    }

    //Ивент нажатия кнопки

    public void penLineAction(ActionEvent event) {

        MyApplication.scene.setCursor(Cursor.CROSSHAIR);

        for (Button button : buttons) {
            button.setDisable(button.getText().equals("a"));
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

    public void transformAction(ActionEvent event) {

        MyApplication.scene.setCursor(Cursor.DEFAULT);
        for (Button button : buttons) {
            button.setDisable(button.getText().equals("t"));
        }
        ChoosingController choosingController = new ChoosingController(circleOnMousePressedEventHandler, graphs,
                circleOnMouseDraggedEventHandler, transLine);
        choosingController.run();
    }


    public void unorientedArcAction(ActionEvent actionEvent) {
        MyApplication.scene.setCursor(Cursor.CROSSHAIR);
        for (Button button : buttons) {
            button.setDisable(button.getText().equals("b"));
        }


        for (Circle circle : circleArray) {

            circle.setOnMousePressed(null);

            circle.setOnMouseDragged(null);

        }


        for (Circle circle : circleArray) {

            circle.addEventFilter(MouseEvent.MOUSE_CLICKED, lineDrawEvent);

        }

    }

    //Ивент рисования круга
    EventHandler<MouseEvent> drawCircle = new EventHandler<MouseEvent>() {

        @Override

        public void handle(MouseEvent e) {


            if (penCircle.isDisable() && e.getX() < 950 && e.getY() < 600) {
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


            }if(newCir.isDisable() && e.getX() < 550 && e.getY() < 400){
                for (Graph graph : graphs) {
                    if (graph.getTab().isSelected()) {


                        Pane pane = (Pane) graph.getTab().getContent();
                        Vertex vertex = new Vertex(e.getX(), e.getY(), pane);
                        vertex.getCircle().setStyle("-fx-fill:linear-gradient( from 100.0% 100.0% to 0.0%  100.0%, BLACK 0.5," + color +" 0.5);");
                        graph.getVertices().add(vertex);
                        circleArray.add(vertex.getCircle());
                        vertex.setTextInPane((Pane) graph.getTab().getContent());
                        vertex.setVertexId(graph.getVertices().size() - 1);
                        vertex.getText().setX(vertex.getCircle().getCenterX() + 10);
                        vertex.getText().setY(vertex.getCircle().getCenterY() + 10);
                        graph.addVertex();



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
                        if (vertex.getLoop() != null)
                            vertex.getLoop().setOnMouseReleased(e -> {
                                vertex.getLoop().setStroke(Color.ORANGERED);
                                final ColorPicker colorPicker = new ColorPicker();
                                colorPicker.setValue(Color.RED);
                                Pane pane = (Pane) graph.getTab().getContent();
                                pane.getChildren().add(colorPicker);
                                colorPicker.setOnAction(event11 -> {
                                    vertex.getLoop().setStroke(colorPicker.getValue());
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
                                        Label secondLabel = new Label("Введите название вершины");
                                        TextField textField = new TextField("Название вершины");
                                        textField.setMinWidth(120);
                                        Button button1 = new Button("Выполнить");
                                        VBox secondaryLayout = new VBox();
                                        secondaryLayout.getChildren().addAll(secondLabel, textField, button1);
                                        Pane ppane = new Pane(secondaryLayout);
                                        ppane.setStyle(" -fx-background-color: #1d1d1d");
                                        Scene secondScene = new Scene(ppane, 230, 100);
                                        // New window (Stage)
                                        Stage newWindow = new Stage();
                                        newWindow.setTitle("Введите название вершины");
                                        newWindow.setScene(secondScene);

                                        secondScene.getStylesheets().add(getClass().getResource("jm.css").toExternalForm());
                                        button1.getStyleClass().add("button");
                                        secondLabel.getStyleClass().add("label");


                                        // Specifies the modality for new window.
                                        newWindow.initModality(Modality.WINDOW_MODAL);
                                        // Specifies the owner Window (parent) for new window
                                        newWindow.initOwner(stage);
                                        // Set position of second window, related to primary window.
                                        newWindow.setX(stage.getX() + 100);

                                        newWindow.setY(stage.getY() + 100);


                                        button1.setOnAction(actionEvent -> {

                                            vertex.getText().setText(textField.getText());
                                            newWindow.close();

                                        });
                                        newWindow.show();
                                    } else if (e.getCode() == DELETE) {
                                        Pane pane = (Pane) graph.getTab().getContent();
                                        try {
                                            circleArray.remove(vertex.getCircle());
                                            vertex.getText().setText("");
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
                                    } else if (e.getCode() == KeyCode.L) {
                                        vertex.setLoop((Pane) graph.getTab().getContent());
                                        graph.getMatrixAdjancy().get(vertex.getVertexId()).set(vertex.getVertexId(), 1);
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

                                            arc.setStartY(circle.getCenterY() );

                                        } else if (arc.getEnd().getCircle() == vertex.getCircle()) {

                                            arc.setEndX(circle.getCenterX());

                                            arc.setEndY(circle.getCenterY());

                                        }

                                        arc.configureArrow();

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

                                    vertex.addArc(arc);

                                    arc.setEnd(vertex);

                                    arc.setEndX(vertex.getCircle().getCenterX());

                                    arc.setEndY(vertex.getCircle().getCenterY());
                                    //vertex.getArcs().add(arc);
                                    Pane pane = (Pane) graph.getTab().getContent();
                                    pane.getChildren().add(arc);
                                    if (penLine.isDisable()) {
                                        arc.setUnoriented(false);
                                        arc.setArrow(pane);
                                        arc.configureArrow();
                                    } else if (unorientedArc.isDisable()) {
                                        arc.setUnoriented(true);

                                    }


                                    // panes.get(0).getChildren().add(arc);

                                    x1 = 0;

                                    x2 = 0;

                                    y1 = 0;

                                    y2 = 0;
                                    graph.addArc(arc);
                                    //  arc.updateUnorientedArrow();
                                    arc.toBack();

                                    this.arc = new Arc(x1, y1, x2, y2);

                                }

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


                        arc.configureArrow();
                        graph.addArc(arc);
                    }
                }
            };


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
                    vertex.updatePower();
                    KeyValue kvy = new KeyValue(vertex.getPower().yProperty(), vertex.getCircle().getCenterY() - 15);
                    KeyFrame kfy = new KeyFrame(duration, kvy);
                    timeline.getKeyFrames().add(kfy);
                    timeline.play();
                    timeline.setOnFinished(event -> {
                        Timeline timeline1 = new Timeline();
                        timeline1.setCycleCount(1);
                        Duration duration1 = new Duration(5000);

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
                            timeline2.setOnFinished(even -> ((Pane) graph.getTab().getContent()).getChildren().remove(vertex.getPower()));
                        });
                    });
                }
            }
        }
    }

    public void showGraphInfo(ActionEvent event) {
        for (Graph graph : graphs) {
            if (graph.getTab().isSelected()) {
                graph.showInfo(stage);

            }
        }
    }


    public void showDistance(ActionEvent event) {
        for (Button button : buttons) {
            button.setDisable(false);
        }
        MyApplication.scene.setCursor(Cursor.DEFAULT);
        for (Graph graph : graphs) {
            if (graph.getTab().isSelected()) {
                PathController pathController = new PathController();
                graph.getVertices().forEach(vertex -> vertex.getCircle().setOnMousePressed(event1 -> {
                    if (pathController.getVertexStart() == null) {
                        pathController.setVertexStart(vertex);
                        pathController.getVertexStart().getCircle().setStroke(Color.GREEN);

                    } else {

                        pathController.setVertexEnd(vertex);
                        pathController.getVertexEnd().getCircle().setStroke(Color.GREEN);
                    }

                    if (pathController.getVertexStart() != null && pathController.getVertexEnd() != null) {
                        pathController.setPath(graph.getVertices());
                        pathController.dijkstra(graph.getMatrixAdjancy(), pathController.getVertexStart().getVertexId(), stage);
                    }
                }));
            }
        }
    }

    public void makePlanar(ActionEvent event) {
        for (Graph graph : graphs) {
            if (graph.getTab().isSelected()) {
                PlanarityController planarityController = new PlanarityController(graph);
                graph = planarityController.makePlanar(graph);

            }

        }
    }

    public void checkEuler(ActionEvent event) {
        for (Graph graph : graphs) {
            if (graph.getTab().isSelected()) {
                graph.checkEuler(stage);

            }

        }

    }




    public void newCirAction(ActionEvent actionEvent) {
        MyApplication.scene.setCursor(Cursor.DEFAULT);

        for (Button button : buttons) {
            button.setDisable(button.getText().equals(""));
        }

        for (Tab tab1 : tabPane.getTabs()) {

            tab1.getContent().addEventFilter(MouseEvent.MOUSE_CLICKED, drawCircle);


        }

        for (Circle circle : circleArray) {

            circle.setOnMousePressed(null);

            circle.setOnMouseDragged(null);

        }}
        
    public void tutorialAction(ActionEvent event) {
        new Tutorials(stage);

    }

    public void clearAction(ActionEvent event) {
        for (Graph graph : graphs) {
            if (graph.getTab().isSelected()) {
                for (Vertex vertex : graph.getVertices()) {
                    vertex.getArcs().clear();
                }
                graph.getVertices().clear();
                graph.getMatrixAdjancy().clear();
                ((Pane) graph.getTab().getContent()).getChildren().clear();
            }

        }

    }

    public void pathFinding(ActionEvent event) {
        MyApplication.scene.setCursor(Cursor.DEFAULT);
        for (Graph graph : graphs) {
            if (graph.getTab().isSelected()) {
                PathController pathController = new PathController();
                graph.getVertices().forEach(vertex -> vertex.getCircle().setOnMousePressed(event1 -> {
                    if (pathController.getVertexStart() == null) {
                        pathController.setVertexStart(vertex);
                        pathController.getVertexStart().getCircle().setStroke(Color.GREEN);

                    } else {

                        pathController.setVertexEnd(vertex);
                        pathController.getVertexEnd().getCircle().setStroke(Color.GREEN);
                    }

                    if (pathController.getVertexStart() != null && pathController.getVertexEnd() != null) {
                        // pathController.getPath(pathController.getVertexStart());
                        pathController.setPath(graph.getVertices());
                        pathController.setGraph(graph);
                        pathController.dijkstra(graph.getMatrixAdjancy(), pathController.getVertexStart().getVertexId(), stage);
                        pathController.printAllPaths(pathController.getVertexStart().getVertexId(), pathController.getVertexEnd().getVertexId());
                    }

                }));
            }
        }
    }

    public void findVertex(ActionEvent event) {
        for (Graph graph : graphs) {
            if (graph.getTab().isSelected()) {
                Label secondLabel = new Label("");
                TextField textField = new TextField("Enter data");
                textField.setMinWidth(120);
                Button button1 = new Button("enter");
                VBox secondaryLayout = new VBox();
                secondaryLayout.getChildren().addAll(secondLabel, textField, button1);
                Scene secondScene = new Scene(secondaryLayout, 230, 100);
                Stage newWindow = new Stage();
                newWindow.setTitle("Enter content");
                newWindow.setScene(secondScene);
                newWindow.initModality(Modality.WINDOW_MODAL);
                newWindow.initOwner(stage);
                button1.setOnAction(actionEvent -> {
                    for (Vertex vertex : graph.getVertices()) {
                        if (vertex.getContent().equals(textField.getText())) {
                            vertex.getCircle().setStroke(Color.BLUE);
                        }
                    }
                    newWindow.close();

                });
                newWindow.show();
            }
        }

    }

    public void addContent(ActionEvent actionEvent) {
        for (Graph graph : graphs) {
            if (graph.getTab().isSelected()) {
                Label secondLabel = new Label("");
                TextField textField = new TextField("Enter data");
                textField.setMinWidth(120);
                Button button1 = new Button("enter");
                VBox secondaryLayout = new VBox();
                secondaryLayout.getChildren().addAll(secondLabel, textField, button1);
                Scene secondScene = new Scene(secondaryLayout, 230, 100);
                Stage newWindow = new Stage();
                newWindow.setTitle("Enter content");
                newWindow.setScene(secondScene);
                newWindow.initModality(Modality.WINDOW_MODAL);
                newWindow.initOwner(stage);
                button1.setOnAction(actionEvent1 -> {
                    for (Vertex vertex : graph.getVertices()) {
                        if (vertex.getCircle().getStroke().equals(Color.ORANGERED)) {
                            vertex.setContent(textField.getText());
                        }
                    }
                    newWindow.close();

                });
                newWindow.show();
            }
        }
    }

    public void loop(ActionEvent actionEvent) {
    }
}