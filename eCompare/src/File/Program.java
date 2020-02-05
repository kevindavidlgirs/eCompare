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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author 2207hembilo
 */
public class Program extends Application{
    
    public void start(Stage primaryStage) throws IOException {
        
        FacadeECompare fe = new FacadeECompare("TestBC","RootBC_Left","RootBC_Right");
        fe.compare();
        
        /*
        * Méthode get pour la récupération des sytèmes de fichier.
        * Je me dis pourquoi pas tenter d'inserer ces méthodes dans treeTableViewLeft et treeTableViewRight;
        * A toi de voir Kévin. Enjoy !!!
        */
        fe.get_compared_left_struct_file();
        fe.get_compared_right_struct_file();
        
        //Nom,type de fichier, date de modif,taille, status
        TreeTableView treeTableViewLeft = new TreeTableView();
        TreeTableView treeTableViewRight = new TreeTableView();
        
        TreeTableColumn<File, File>
        nameCol = new TreeTableColumn<>("Name"),
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
        
        treeTableViewLeft.getColumns().setAll(nameCol, typeCol, dateModifCol, sizeCol, statusCol);
        treeTableViewRight.getColumns().setAll(nameCol, typeCol, dateModifCol, sizeCol, statusCol);
        
        Group root = new Group();
        BorderPane mainContainer = new BorderPane();
        StackPane leftPane = new StackPane();
        StackPane rightPane = new StackPane();
        
        leftPane.getChildren().add(treeTableViewLeft);
        rightPane.getChildren().add(treeTableViewRight);
         
        leftPane.setPrefSize(475, 450);
        rightPane.setPrefSize(475, 450);
        
        leftPane.setPadding(new Insets(5,5,5,5));
        rightPane.setPadding(new Insets(5,5,5,5));
        
        mainContainer.setLeft(leftPane);
        mainContainer.setRight(rightPane);
        
        Scene scene = new Scene(root, 950, 450);
        
        primaryStage.setTitle("eCompare");
        primaryStage.setScene(scene);
        root.getChildren().add(mainContainer);
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
