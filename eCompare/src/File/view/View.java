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
import javafx.scene.control.Button;


/**
 *
 * @author 2207hembilo
 */
public class View{
    private final CompareBoxView left_vbstruct_folder;
    private final CompareBoxView right_vbstruct_folder;
    private final ButtonsBoxView buttons_view;
    //private final ButtonMoveLeftRightView button_move_left_right_View;
    private final BorderPane root = new BorderPane();
    //private final TextFlow footerStatus = new TextFlow();
    private final FooterBoxView footer_bt_text_View;

    public View(Stage primaryStage, ViewModel vm) {
        buttons_view = new ButtonsBoxView(vm);
        left_vbstruct_folder = new CompareBoxView(primaryStage, vm, "left");
        right_vbstruct_folder = new CompareBoxView(primaryStage, vm, "right");
        footer_bt_text_View = new FooterBoxView(vm);
        configWindow();
        Scene scene = new Scene(root);
        primaryStage.setTitle("eCompare");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void configWindow(){
        root.setTop(buttons_view);
        root.setLeft(left_vbstruct_folder);
        root.setRight(right_vbstruct_folder);
        root.setBottom(footer_bt_text_View);
    }
}
