/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.view;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeItem;
import File.model.File;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author herve
 */
public class CompareBoxView extends VBox{
    private final TreeTableView treeTableViews;
    private final TreeTableColumn<File, File> nameCol = new TreeTableColumn<>("Name");
    private final TreeTableColumn<File, File> typeCol = new TreeTableColumn<>("Type");
    private final TreeTableColumn<File, File> dateModifCol = new TreeTableColumn<>("Date modif");
    private final TreeTableColumn<File, File> sizeCol = new TreeTableColumn<>("Size");
    private final TreeTableColumn<File, File> statusCol = new TreeTableColumn<>("Status");
    
    
    public CompareBoxView(TreeItem<File> item){
        // Etant donné qu'il s'agit d'un object d'affichage, les treeTableView sont dans la vue et non dans le viewModel comme nous le pensions.
        treeTableViews = new TreeTableView(item);
        treeTableViews.setRoot(item);
        treeTableViews.setShowRoot(false);
        
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
        
        treeTableViews.getColumns().setAll(nameCol, typeCol, dateModifCol, sizeCol, statusCol);
        setPadding(new Insets(3));
        setPrefWidth(600);
        
        Text labelPathText = new Text(item.getValue().getPath().toString());
        labelPathText.setStyle("-fx-font-weight: bold");
        getChildren().addAll(labelPathText,treeTableViews);
    }   
}
