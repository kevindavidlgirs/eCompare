/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.viewModel;

import File.model.Model;
import File.model.File;
import File.model.SimpleFile;
import java.time.LocalDateTime;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;

/**
 *
 * @author herve
 */

public class EditVM {
    private final StringProperty text = new SimpleStringProperty();
    private final StringProperty file_name = new SimpleStringProperty();
    private final BooleanProperty showing = new SimpleBooleanProperty(false);
    private final ViewModel viewModel;
    private final Model model;
    
    
    EditVM(ViewModel vm, Model model) {
        viewModel = vm;
        this.model = model;
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
    
    public StringProperty selected_file_name() {
        return file_name;
    }
    
    public ReadOnlyBooleanProperty showingProperty() {
        return showing;
    }

    public void setVisible(boolean b) {
        showing.setValue(b);
    }
    
    public void update(){
        viewModel.selected_file_property().getValue().set_size(text.length().get());
        viewModel.selected_file_property().getValue().set_file_content(text.get());
        viewModel.selected_file_property().getValue().set_date(LocalDateTime.now());
        model.get_left_struct_folder().compare(model.get_left_struct_folder(), model.get_right_struct_folder());
        viewModel.set_leftRoot();
        viewModel.set_rightRoot();
        setVisible(false);
    }
}
