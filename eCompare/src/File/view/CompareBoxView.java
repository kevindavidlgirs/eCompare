/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.view;
import File.viewModel.ViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeItem;
import File.model.File;
import File.model.SimpleFile;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
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
    private final BooleanProperty struct_folders_has_changed = new SimpleBooleanProperty(false);
    private final Button directoryButton = new Button();
    private TreeTableView<File> treeTableViews;
    private Text labelPathText;
    HBox label_paht_and_dir = new HBox();
    //A changer il me semble que ce n'est pas très propre.
    private String name;

    //A changer il me semble que ce n'est pas très propre car item et vm sont liés.
    public CompareBoxView(TreeItem<File> item, Stage primaryStage, ViewModel vm, String name){
        createTreeTableView(item, name);
        createCells();
        configTreeTableView();
        createLabelPath(item);
        createDirectorychooser();
        setBindingAndListeners(vm);
        

        label_paht_and_dir.getChildren().addAll(labelPathText, directoryButton);
        label_paht_and_dir.setMargin(directoryButton,new Insets(0,0,10,10));
        label_paht_and_dir.setMargin(labelPathText,new Insets(10,0,0,0));
        
        getChildren().addAll(label_paht_and_dir,treeTableViews);
        
        directoryButton.setOnAction(e -> {
            labelPathText.setText(DirChooser.selectDirectory(primaryStage));
            try {
                vm.set_treeItem(labelPathText.getText(), name);
                if(name.equals("left"))
                    treeTableViews.setRoot(vm.get_left_treeItem());
                else
                    treeTableViews.setRoot(vm.get_right_treeItem());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void createDirectorychooser() {
        Image img = new Image(String.valueOf(getClass().getResource("img/folder.png")));
        directoryButton.setGraphic(new ImageView(img)); 
    }

    private void createLabelPath(TreeItem<File> item) {
        labelPathText = new Text(item.getValue().getPath().toString());
        labelPathText.setStyle("-fx-font-weight: bold");
    }

    private void configTreeTableView() {
        treeTableViews.getColumns().setAll(nameCol, typeCol, dateModifCol, sizeCol, statusCol);
        setPadding(new Insets(3));
        setPrefWidth(750);
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

    private void createTreeTableView(TreeItem<File> item, String name){
        treeTableViews = new TreeTableView(item);
        treeTableViews.setRoot(item);
        treeTableViews.setShowRoot(false);
        this.name = name;
    }
    
    private void setBindingAndListeners(ViewModel vm) {
        /** Ce bind peut pose des problème, en refusant de fonctionner chez moi. peut-être le feras tu fonctionner.
         Du coup j'ai trouvé une solution temporaire de contournement : vm.set_selected_file().
         **/
        //vm.selected_file_property().bind(treeTableViews.getSelectionModel().selectedItemProperty()); 
        struct_folders_has_changed.bindBidirectional(vm.struct_folders_has_changed());
       
       if(name.equals("left")) {
            treeTableViews.rootProperty().bind(vm.root_left_property());
        }else{
            treeTableViews.rootProperty().bind(vm.root_right_property());
        }
        
       
        treeTableViews.setOnMousePressed(e -> {
            // Solution de contournement du binding qui ne fonctionne pas chez moi, à enlever dès que sa fonctionne.
            vm.set_selected_file(treeTableViews.getSelectionModel().selectedItemProperty().getValue().getValue());
            if (e.getClickCount() == 2) {
               vm.openSelectedFile();
            }
        });
    }
}
