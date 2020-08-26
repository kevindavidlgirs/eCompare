/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.view;

import File.viewModel.ViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class FooterBoxView extends VBox{

    private final TextFlow footerStatus = new TextFlow();
    private final Button moveLeftRight = new Button("Copy Left -> Right");
    
    {
        getChildren().addAll(footerStatus, moveLeftRight);
    }
    
    public FooterBoxView(ViewModel vm){
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
        
        moveLeftRight.setPrefWidth(160);
        setMargin(moveLeftRight,new Insets(0,0,10,0));
        setAlignment(Pos.CENTER);
        
        moveLeftRight.disableProperty().bind(vm.actionEnabledProperty());
        moveLeftRight.setOnAction(e -> {
            vm.moveItems();
        });
    }
}
