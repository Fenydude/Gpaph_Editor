package ch.makery.address.util;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Tutorials {
    public Tutorials(Stage stage) {
        Label buttonsLabel1 = new Label("@Circle - нарисовать вершину");
        Label buttonsLabel2 = new Label( "@Arc - нарисовать дугу");
        Label buttonsLabel3 = new Label("@UnArc - нарисовать неориентированную дуга");
        Label buttonsLabel4 = new Label("@Transform - перенести вершину или дугу в другое место, чтобы закрепить дугу на другие вершины, надо дважды кликнуть рядом с началом/концом дуги и вести к новой вершине");
        Label buttonsLabel5 = new Label( "@color - изменение цвета вершины или дуги");

        Label secondGraphLabel = new Label("@чтобы создать ещё один граф, нажмите File->New Graph, при этом появится диалоговое окно для ввода имени графа");
        Label saveOpenLabel = new Label("@чтобы сохранить граф нажмите File->Save, граф будет сохранен в файле с расширением .shelt и именем графа");
        Label saveOpenLabel2 = new Label( "@чтобы открыть файл с графом, нажмите File->Open и выберите в диалоговом окне нужный вам файл с расширением .shelt");

        Label editLabel = new Label("@чтобы получить информацию о графе, включая планарность нажмите Edit->Show Graph Info");
        Label editLabel2= new Label("@чтобы узнать мощность вершин Edit -> Show Power Vertex");
        Label editLabel3 = new Label("@чтобы подсветить пути, Edit -> show path, затем выделите две желаемые вершины(они должны загореться зеленым)");
        Label editLabel4 = new Label( "@чтобы сделать граф планарным Edit->Make Planar");
        Label editLabel5 = new Label("@чтобы проверить наличие эйлеровых циклов и вывести их в диалоговое окно, Edit-> Euler Check");
        Label editLabel6 = new Label("@чтобы найти расстояние между двумя вершинами и вывести его в диалоговое окно, Edit-> Distance");

        Label functionalLabel = new Label("@чтобы создать арку, после выбора кнопки Arc|UnArc(в зависимости от необходимого типа дуги) и выберите две вершины(!!ОБРАТИТЕ ВНИМАНИЕ, ЧТО ДУГА РИСУЕТСЯ ТОЛЬКО ПОСЛЕ НАЖАТИЯ НА ОБЕ ВЕРШИНЫ!!)");
        Label functionalLabel2 = new Label( "@чтобы удалить что-либо, выделите это с помощью  Transform и нажмите кнопку Delete на клавиатуре");
        Label functionalLabel3 = new Label( "@чтобы дать имя узлу, выделите узел и нажмите на I ");
        Label functionalLabel4 = new Label( "@чтобы создать петлю, выделите узел и нажмите L");
        VBox secondaryLayout = new VBox();
        secondaryLayout.getChildren().addAll(
                buttonsLabel1,buttonsLabel2,buttonsLabel3,buttonsLabel4,buttonsLabel5,
                secondGraphLabel, saveOpenLabel,saveOpenLabel2,
                editLabel, editLabel2,editLabel3,editLabel4,editLabel5,editLabel6,
                functionalLabel,functionalLabel2,functionalLabel3,functionalLabel4);
        Scene secondScene = new Scene(secondaryLayout, 1000, 300);
        Stage newWindow = new Stage();
        newWindow.setTitle("Tutorials");
        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(stage);
        newWindow.show();

    }

}
