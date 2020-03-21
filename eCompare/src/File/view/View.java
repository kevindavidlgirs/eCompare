/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.view;

import File.model.File;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import File.viewModel.ViewModel;

/**
 *
 * @author 2207hembilo
 */
public class View{
    private final CompareBoxView left_vbstruct_folder;
    private final CompareBoxView right_vbstruct_folder;
    private final ButtonsBoxView buttons_view;


    public View(Stage primaryStage, ViewModel vm) {

        BorderPane root = new BorderPane();
        buttons_view = new ButtonsBoxView(vm);

        //Nous devrions changer le paramètre name. (trouver un moyen plus propre?)
        left_vbstruct_folder = new CompareBoxView(vm.get_left_treeItem(), primaryStage, vm, "left");
        right_vbstruct_folder = new CompareBoxView(vm.get_right_treeItem(), primaryStage, vm, "right");
        
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

        root.setTop(buttons_view);
        root.setLeft(left_vbstruct_folder);
        root.setRight(right_vbstruct_folder);
        root.setBottom(footerStatus);

        Scene scene = new Scene(root);

        primaryStage.setTitle("eCompare");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
