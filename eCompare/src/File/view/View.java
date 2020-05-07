/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
    private final BorderPane root = new BorderPane();
    private final TextFlow footerStatus = new TextFlow();

    public View(Stage primaryStage, ViewModel vm) {
        new EditView(primaryStage, vm.getEditLeftVM(), "left");
        new EditView(primaryStage, vm.getEditRightVM(), "right");
        buttons_view = new ButtonsBoxView(vm);
        left_vbstruct_folder = new CompareBoxView(primaryStage, vm, "left");
        right_vbstruct_folder = new CompareBoxView(primaryStage, vm, "right");
        configStateLabel();
        configWindow();
        Scene scene = new Scene(root);
        primaryStage.setTitle("eCompare");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void configStateLabel(){
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

        footerStatus.getChildren().addAll(ORPHAN, SAME, PARTIAL_SAME, NEWER, OLDER);
        footerStatus.setTextAlignment(TextAlignment.CENTER);
        footerStatus.setPadding(new Insets(10));
    }

    private void configWindow(){
        root.setTop(buttons_view);
        root.setLeft(left_vbstruct_folder);
        root.setRight(right_vbstruct_folder);
        root.setBottom(footerStatus);
    }
}
