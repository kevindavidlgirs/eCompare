/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.view;
import File.viewModel.ViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import File.model.File;
import File.model.Status;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.HBox;


/**
 *
 * @author herve
 */
public class CompareBoxView extends VBox{
    private final TreeTableColumn<File, File> nameCol = new TreeTableColumn<>("Name");
    private final TreeTableColumn<File, File> typeCol = new TreeTableColumn<>("Type");
    private final TreeTableColumn<File, File> dateModifCol = new TreeTableColumn<>("Date modif");
    private final TreeTableColumn<File, File> sizeCol = new TreeTableColumn<>("Size");
    private final TreeTableColumn<File, File> statusCol = new TreeTableColumn<>("Status");
    private final TreeTableView<File> treeTableViews = new TreeTableView();
    private final Text labelPathText = new Text();
    private final Button directoryButton = new Button();
    private final HBox label_path_and_dir = new HBox();
    private final String side;

    public CompareBoxView(Stage primaryStage, ViewModel vm, String side){
        this.side = side;
        createTreeTableView(vm);
        createCells();
        configTreeTableView();
        createLabelPath(vm);
        createDirectorychooser();
        setBindingAndListeners(vm);
        configWindow();

        directoryButton.setOnAction(e -> {
            try {
                vm.set_treeItem(DirChooser.selectDirectory(primaryStage), side);
            } catch (IOException | NullPointerException ex) {

            }
        });
         new EditView(primaryStage, vm.getEditVM(side), side);
    }

    private void configWindow(){
        label_path_and_dir.getChildren().addAll(labelPathText, directoryButton);
        label_path_and_dir.setMargin(directoryButton,new Insets(0,0,10,10));
        label_path_and_dir.setMargin(labelPathText,new Insets(10,0,0,0));
        getChildren().addAll(label_path_and_dir,treeTableViews);
    }

    private void createDirectorychooser() {
        Image img = new Image(String.valueOf(getClass().getResource("img/folder.png")));
        directoryButton.setGraphic(new ImageView(img));
    }

    private void createLabelPath(ViewModel vm) {
        labelPathText.textProperty().bind(vm.getTreeItem(side).labelPathTextProperty());
        labelPathText.setWrappingWidth(500);
        labelPathText.setStyle("-fx-font-weight: bold");
    }

    private void configTreeTableView() {
        treeTableViews.getColumns().setAll(nameCol, typeCol, dateModifCol, sizeCol, statusCol);
        setPadding(new Insets(3));
        setPrefWidth(550);
    }

    private void createCells() {
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
    }


    private void createTreeTableView(ViewModel vm){
        treeTableViews.rootProperty().bind(vm.getTreeItem(side).root_property());
        treeTableViews.setShowRoot(false);
    }
    
    
    private void setBindingAndListeners(ViewModel vm) {
        treeTableViews.rootProperty().bind(vm.getTreeItem(side).root_property());
        treeTableViews.setOnMousePressed(e -> {
            vm.getTreeItem(side).selected_file_property().setValue(treeTableViews.getSelectionModel().selectedItemProperty().get());
            if (e.getClickCount() == 2) {
                vm.getTreeItem(side).openSelectedFile();
            }else if(e.isAltDown()){
                vm.getTreeItem(side).deleteSelectedFile();
            }
        });
    }
}
