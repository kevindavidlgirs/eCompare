/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 *
 * @author 2207hembilo
 */
public class Program extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        FacadeECompare fe = new FacadeECompare("TestBC", "RootBC_Left", "RootBC_Right");
        fe.compare();

        TreeTableView treeTableViewLeft = new TreeTableView(makeTreeRoot(fe.get_compared_left_struct_file()));
        TreeTableView treeTableViewRight = new TreeTableView(makeTreeRoot(fe.get_compared_right_struct_file()));

        //CREATION D'UNE METHODE OU CLASS POUR LES DEUX TREETABLECOLUMN SUIVANTS
        TreeTableColumn<File, File> nameCol = new TreeTableColumn<>("Name"),
                typeCol = new TreeTableColumn<>("Type"),
                dateModifCol = new TreeTableColumn<>("Date modif"),
                sizeCol = new TreeTableColumn<>("Size"),
                statusCol = new TreeTableColumn<>("Status");

        nameCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        typeCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        dateModifCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        sizeCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        statusCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));

        dateModifCol.setPrefWidth(150);

        nameCol.setCellFactory((param) -> {
            return new NameFileCell();
        });
        typeCol.setCellFactory((param) -> {
            return new TypeFileCell();
        });
        dateModifCol.setCellFactory((param) -> {
            return new DateFileCell();
        });

        sizeCol.setCellFactory((param) -> {
            return new SizeFileCell();
        });

        statusCol.setCellFactory((param) -> {
            return new StatusFileCell();
        });

        TreeTableColumn<File, File> nameCol1 = new TreeTableColumn<>("Name"),
                typeCol1 = new TreeTableColumn<>("Type"),
                dateModifCol1 = new TreeTableColumn<>("Date modif"),
                sizeCol1 = new TreeTableColumn<>("Size"),
                statusCol1 = new TreeTableColumn<>("Status");

        nameCol1.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        typeCol1.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        dateModifCol1.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        sizeCol1.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        statusCol1.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));

        dateModifCol1.setPrefWidth(150);

        nameCol1.setCellFactory((param) -> {
            return new NameFileCell();
        });
        typeCol1.setCellFactory((param) -> {
            return new TypeFileCell();
        });
        dateModifCol1.setCellFactory((param) -> {
            return new DateFileCell();
        });

        sizeCol1.setCellFactory((param) -> {
            return new SizeFileCell();
        });

        statusCol1.setCellFactory((param) -> {
            return new StatusFileCell();
        });
        //CREATION D'UNE METHODE OU CLASS POUR LES DEUX TREETABLECOLUMN PRECEDENTS

        treeTableViewLeft.getColumns().setAll(nameCol, typeCol, dateModifCol, sizeCol, statusCol);
        treeTableViewRight.getColumns().setAll(nameCol1, typeCol1, dateModifCol1, sizeCol1, statusCol1);

        BorderPane root = new BorderPane();
        VBox vbRight = new VBox();
        VBox vbLeft = new VBox();
        VBox footer = new VBox();
        
        vbRight.setPadding(new Insets(3));
        vbLeft.setPadding(new Insets(3));
        vbRight.setPrefWidth(600);
        vbLeft.setPrefWidth(600);
        footer.setPrefWidth(1200);

        Text labelTxtLeft = new Text(fe.get_compared_left_struct_file().getPath().toAbsolutePath().toString());
        Text labelTxtRight = new Text(fe.get_compared_right_struct_file().getPath().toAbsolutePath().toString());
        labelTxtLeft.setStyle("-fx-font-weight: bold");
        labelTxtRight.setStyle("-fx-font-weight: bold");
        
        /*
            NE FONCTIONNE PAS.
            labelTxtRight.setTextAlignment(TextAlignment.CENTER);
            labelTxtLeft.setTextAlignment(TextAlignment.CENTER);
        */
        
        Text ORPHAN = new Text("ORPHAN   ");
        Text SAME = new Text("SAME   ");
        Text PARTIAL_SAME = new Text("PARTIAL_SAME   ");
        Text NEWER = new Text("NEWER   ");
        Text OLDER = new Text("OLDER");

        ORPHAN.setFill(Color.BLUEVIOLET);
        SAME.setFill(Color.GREEN);
        PARTIAL_SAME.setFill(Color.ORANGE);
        NEWER.setFill(Color.RED);
        OLDER.setFill(Color.BROWN);

        TextFlow footerStatus = new TextFlow();
        footerStatus.getChildren().addAll(ORPHAN, SAME, PARTIAL_SAME, NEWER, OLDER);
        footerStatus.setTextAlignment(TextAlignment.CENTER);
        footerStatus.setPadding(new Insets(10));

        vbLeft.getChildren().addAll(labelTxtLeft, treeTableViewLeft);
        vbRight.getChildren().addAll(labelTxtRight, treeTableViewRight);
        root.setLeft(vbLeft);
        root.setRight(vbRight);
        root.setBottom(footerStatus);

        Scene scene = new Scene(root);

        primaryStage.setTitle("eCompare");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public TreeItem<File> makeTreeRoot(File root) {

        TreeItem<File> res = new TreeItem<>(root);
        res.setExpanded(true);

        if (root.isDirectory()) {
            root.getList().forEach(se -> {
                res.getChildren().add(makeTreeRoot(se));
            });
        }

        return res;
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
