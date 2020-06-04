/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.view;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import File.viewModel.EditVM;
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
    private final Button saveButton = new Button("Save");
    private final TextArea textArea = new TextArea();
    private final BorderPane root = new BorderPane();
    private final TextFlow footerStatus = new TextFlow(saveButton);
    private final StackPane stackPane = new StackPane(textArea);
    private final EditVM editVM;
    private final String side;

    public EditView(Stage primaryStage, EditVM editVM, String side) {

        this.editVM = editVM;
        this.side = side;
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        configTextArea();
        configFooter();
        configWindow();
        configBinding();
        Scene scene = new Scene(root, 600, 400);
        setScene(scene);

        setOnHiding((e) -> editVM.setVisible(false));
        editVM.showingProperty().addListener((obj, old, act) -> {
            if(act) {
                saveButton.disableProperty().setValue(true);
                showAndWait();
            }
        });
        
        saveButton.setOnAction(e -> {
            editVM.update(side);
            saveButton.disableProperty().setValue(true);
        });
        
        textArea.textProperty().addListener((obj, old, act) -> {
            saveButton.disableProperty().setValue(false);
        });
    }

    private void configTextArea(){
        textArea.setWrapText(true);
        textArea.textProperty().bindBidirectional(editVM.textProperty());
    }

    private void configFooter(){
        footerStatus.setTextAlignment(TextAlignment.CENTER);
        footerStatus.setPadding(new Insets(10));
    }

    private void configWindow(){
        root.setCenter(stackPane);
        root.setBottom(footerStatus);
    }

    private void configBinding(){
        titleProperty().bind(editVM.selected_file_name().
                concat(" : ").
                concat(editVM.textLengthProperty()).
                concat(" octets")
        );
    }
}
