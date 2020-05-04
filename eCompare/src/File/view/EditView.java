/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.view;

import File.model.Model;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import File.viewModel.EditVM;
import javafx.beans.value.ObservableObjectValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 *
 * @author herve
 */
public class EditView extends Stage {
    private final Button addButton = new Button("Save");
    public EditView(Stage primaryStage, EditVM editVM) {
        BorderPane root = new BorderPane();
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        Button saveButton = new Button("Save");
        
        textArea.textProperty().bindBidirectional(editVM.textProperty());
        StackPane stackPane = new StackPane(textArea);
        
        TextFlow footerStatus = new TextFlow(saveButton);
        footerStatus.setTextAlignment(TextAlignment.CENTER);
        footerStatus.setPadding(new Insets(10));

        
        root.setCenter(stackPane);
        root.setBottom(footerStatus);

        setOnHiding((e) -> editVM.setVisible(false));
        editVM.showingProperty().addListener((obj, old, act) -> {
            if(act) showAndWait();
        });
        
        saveButton.setOnAction(e -> {
            editVM.update();
        });
         
        Scene scene = new Scene(root, 600, 400);
        setScene(scene);
        
        titleProperty().bind(editVM.selected_file_name().
                concat(" : ").
                concat(editVM.textLengthProperty()).
                concat(" octets")
        );
    }
    
}
