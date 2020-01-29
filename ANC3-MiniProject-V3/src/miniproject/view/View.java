package miniproject.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import miniproject.mvvm.ViewModel;

public class View {

    private final ListView<String> toDoList = new ListView<>();
    private final ListView<String> doneList = new ListView<>();
    private final Label toDoLabel = new Label("À faire");
    private final Label doneLabel = new Label("C'est fait");
    private final Label addLabel = new Label("À ajouter : ");
    private final Button setDone = new Button(">>");
    private final Button setToDo = new Button("<<");
    private final Button addButton = new Button(">>");
    private final TextField addText = new TextField();
    private final VBox lBox = new VBox();
    private final VBox cBox = new VBox();
    private final VBox rBox = new VBox();
    private final VBox addBox = new VBox();
    
    
    private final ViewModel viewModel;

    public View(Stage primaryStage, ViewModel viewModel) throws Exception {
        this.viewModel = viewModel;
        configComponents();
        configBindings();
        configListeners();
        Parent root = setRoot();
        Scene scene = new Scene(root, 800, 400);
        primaryStage.setTitle("MiniProject V3");
        primaryStage.setScene(scene);
    }

    private Parent setRoot() {
        HBox root = new HBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);
        root.getChildren().addAll(addBox, lBox, cBox, rBox);
        return root;
    }

    private void configComponents() {
        lBox.getChildren().addAll(toDoLabel, toDoList);
        lBox.setAlignment(Pos.CENTER);
        lBox.setSpacing(5);
        lBox.setPrefWidth(250);
        cBox.getChildren().addAll(setDone, setToDo);
        cBox.setSpacing(20);
        cBox.setAlignment(Pos.CENTER);
        rBox.getChildren().addAll(doneLabel, doneList);
        rBox.setAlignment(Pos.CENTER);
        rBox.setSpacing(5);
        rBox.setPrefWidth(250);
        addBox.getChildren().addAll(addLabel, addText, addButton);
        addBox.setAlignment(Pos.CENTER);
        addBox.setSpacing(20);
        addBox.setPrefWidth(250);
        setDone.setPrefWidth(60);
        setToDo.setPrefWidth(60);
        addButton.setPrefWidth(60);
        setToDo.setDisable(true);
        setDone.setDisable(true);
        addButton.setDisable(true);
    }

    private void configBindings() {
        configDataBindings();
        configActionsBindings();
        configViewModelBindings();
    }

    private void configDataBindings() {
        toDoList.itemsProperty().bind(viewModel.toDoListProperty());
        doneList.itemsProperty().bind(viewModel.doneListProperty());
        addText.textProperty().bindBidirectional(viewModel.addTextProperty());
    }

    private void configActionsBindings() {    
        setDone.disableProperty().bind(viewModel.itemTransferableDoneListProperty());
        setToDo.disableProperty().bind(viewModel.itemTransferableToDoListProperty());
        addButton.disableProperty().bind(viewModel.itemAddableToDoList());
    }

    private void configViewModelBindings() {
        viewModel.numLineSelectedToDoListProperty().bind(getToDoListModel().selectedIndexProperty());
        viewModel.numLineSelectedDoneListProperty().bind(getDoneListModel().selectedIndexProperty());
    }

    

    private void configListeners() {
        //Plus besoin de récupérer l'index pour le mettre en paramètre dans la fonction"transfer",
        //Celui-ci se trouve désormais dans la fonction transfère.
        setDone.setOnAction(e -> {
            viewModel.transfer(">>");
        });

        setToDo.setOnAction(e -> {
            viewModel.transfer("<<");

        });

        toDoList.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                viewModel.transfer(">>");
            }
        });

        doneList.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                viewModel.transfer("<<");
            }
        });
        
        addButton.setOnAction(e -> {
            viewModel.addToDo(addText.getText());
        });

        addText.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                viewModel.addToDo(addText.getText());
            }
        });

        addText.focusedProperty().addListener((obs, old, act) -> {
            if (!act) {
                addText.requestFocus();
            }
        });
        
    }
    
    private SelectionModel<String> getToDoListModel() {
        return toDoList.getSelectionModel();
    }

    private SelectionModel<String> getDoneListModel() {
        return doneList.getSelectionModel();
    }
}
