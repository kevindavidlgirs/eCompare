/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.viewModel;

import File.model.File;
import File.model.Model;
import java.io.IOException;
import java.time.LocalDateTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableStringValue;
import javafx.scene.control.TreeItem;
/**
 *
 * @author herve
 */

public class EditVM {
    private final StringProperty text = new SimpleStringProperty();
    private final StringProperty file_name = new SimpleStringProperty();
    private final BooleanProperty showing = new SimpleBooleanProperty(false);
    private final StringProperty nameProperty = new SimpleStringProperty();
    private final ViewModel viewModel;
    private final String side;
    
    EditVM(ViewModel vm, String side) {
        viewModel = vm;
        this.side = side;
    }

    void setText(String s) {
        text.setValue(s);
    }
    
    public StringProperty textProperty() {
        return text;
    }

    public ObservableIntegerValue textLengthProperty() {
        return text.length();
    }
    
    void set_selected_file_name(String s) {
        file_name.setValue(s);
    }
    
    public ReadOnlyBooleanProperty showingProperty() {
        return showing;
    }

    public void setVisible(boolean b) {
        showing.setValue(b);
    }
    
    public StringProperty nameProperty(){
        return nameProperty;
    }
    
    public StringProperty getNameProperty(){
      return (StringProperty) viewModel.getTreeItem(side).selected_file_property().getValue().getValue().nameProperty();
    }
    
    public void start_binding_and_listening(){
        nameProperty.bindBidirectional((Property<String>) viewModel.getTreeItem(side).selected_file_property().getValue().getValue().nameProperty());
    }
    
    public void update(String side){
        viewModel.getTreeItem(side).selected_file_property().getValue().getValue().set_size(text.length().get());
        viewModel.getTreeItem(side).selected_file_property().getValue().getValue().set_file_content(text.get());
        viewModel.getTreeItem(side).selected_file_property().getValue().getValue().set_date(LocalDateTime.now());
        
        viewModel.compare_folders();
        
        viewModel.getTreeItem("left").refresh_root("left");
        viewModel.getTreeItem("right").refresh_root("right");
        
        setVisible(false);
    }
}
