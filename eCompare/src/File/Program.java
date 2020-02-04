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
    
    public void start(Stage primaryStage) {
        TreeTableView treeTableViewLeft = new TreeTableView();
        TreeTableView treeTableViewRight = new TreeTableView();
        
        TreeTableColumn<File, File>
        nameCol = new TreeTableColumn<>("Name"),
        sizeCol = new TreeTableColumn<>("Size");
    
        nameCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        sizeCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        
        nameCol.setPrefWidth(250);
        
        treeTableViewLeft.getColumns().setAll(nameCol, sizeCol);
        treeTableViewRight.getColumns().setAll(nameCol, sizeCol);
        
        Group root = new Group();
        BorderPane mainContainer = new BorderPane();
        StackPane leftPane = new StackPane();
        StackPane rightPane = new StackPane();
        
        leftPane.getChildren().add(treeTableViewLeft);
        rightPane.getChildren().add(treeTableViewRight);
         
        mainContainer.setLeft(leftPane);
        mainContainer.setRight(rightPane);
        
        Scene scene = new Scene(root, 750, 450);
        
        primaryStage.setTitle("eCompare");
        primaryStage.setScene(scene);
        root.getChildren().add(mainContainer);
        primaryStage.show();
    }
    
    public static void main(String[] args) throws IOException {
       /* String r = "TestBC";
        String s = "RootBC_Left";
        String s1 = "RootBC_Right";
        
        FacadeECompare Fe = new FacadeECompare(r,s, s1);
        Fe.compare();*/
        launch(args);
    }
}
