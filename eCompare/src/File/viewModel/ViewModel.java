/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.viewModel;

import File.model.Model;
import File.model.File;
import File.model.SimpleFile;
import javafx.beans.property.*;
import javafx.scene.control.TreeItem;
import java.io.IOException;


/**
 *
 * @author herve
 */
public class ViewModel {
    private final Model model;
    private final EditVM editor;
    private final BooleanProperty all_button = new SimpleBooleanProperty(true);
    private final BooleanProperty newer_left_button = new SimpleBooleanProperty(false);
    private final BooleanProperty newer_right_button = new SimpleBooleanProperty(false);
    private final BooleanProperty orphans_button = new SimpleBooleanProperty(false);
    private final BooleanProperty same_button = new SimpleBooleanProperty(false);
    private final BooleanProperty folders_only = new SimpleBooleanProperty(false);
    private final ObjectProperty<SimpleFile> selected_file_property = new SimpleObjectProperty<>();
    private final ObjectProperty<TreeItem<File>> root_left = new SimpleObjectProperty<>();
    private final ObjectProperty<TreeItem<File>> root_right = new SimpleObjectProperty<>();
    private final SimpleStringProperty leftLabelPathText = new SimpleStringProperty();
    private final SimpleStringProperty rightLabelPathText = new SimpleStringProperty();

    public ViewModel(Model model) {
        this.model = model;
        editor = new EditVM(this,this.model);
        set_leftRoot();
        set_rightRoot();
    }
    
    public void set_leftRoot() {
        File folder = model.get_left_struct_folder();
        root_left.setValue(makeTreeRoot(folder));
        leftLabelPathText.setValue(folder.getPath().toString());
    }

    public void set_rightRoot() {
        File folder = model.get_right_struct_folder();
        root_right.setValue(makeTreeRoot(folder));
        rightLabelPathText.setValue(folder.getPath().toString());
    }
    
    public ObjectProperty<TreeItem<File>> root_left_property() {
        return root_left;
    }
    
    public ObjectProperty<TreeItem<File>> root_right_property() {
        return root_right;
    }

    public SimpleStringProperty leftLabelPathTextProperty(){ return leftLabelPathText; }

    public SimpleStringProperty rightLabelPathTextProperty(){ return rightLabelPathText; }

    public void set_treeItem(String path, String name) throws IOException {
        if (name.equals("left")) {
            model.set_left_struct_folder(path);
        } else {
            model.set_right_struct_folder(path);
        }
        set_leftRoot();
        set_rightRoot();
    }

    public int get_nb_status() {
        return model.get_list_status_selected().size();
    }

    public void add_status_to_edit(String status) {
        model.add_status_to_edit(status);
    }

    public void remove_status_to_edit(String status) {
        model.remove_status_to_edit(status);
    }

    public BooleanProperty newer_left_button_Property() {
        return newer_left_button;
    }

    public BooleanProperty newer_right_button_Property() {
        return newer_right_button;
    }

    public BooleanProperty orphans_button_Property() {
        return orphans_button;
    }

    public BooleanProperty same_button_Property() {
        return same_button;
    }

    public BooleanProperty all_button_Property() {
        return all_button;
    }

    public BooleanProperty folders_only_Property() {
        return folders_only;
    }

    private void set_all_buttons_status_false() {
        newer_left_button.setValue(false);
        newer_right_button.setValue(false);
        orphans_button.setValue(false);
        same_button.setValue(false);
        folders_only.setValue(false);
    }

    public void set_selected_items(String status, Boolean buttons) {
        if (buttons && status.compareTo("All") != 0) {
            add_status_to_edit(status);
            all_button.setValue(false);
        } else if(!buttons){
            System.out.println(buttons.toString() + " " + status.toString());
            remove_status_to_edit(status);
            if(get_nb_status() == 0){
                all_button.setValue(true);
                model.clear_statusList();
                add_status_to_edit("All");
            }
        } else if(status.compareTo("All") == 0 || get_nb_status() == 0) {
            add_status_to_edit(status);
            model.clear_statusList();
            all_button.setValue(true);
            set_all_buttons_status_false();
        }
        set_leftRoot();
        set_rightRoot();
    }

    private TreeItem<File> makeTreeRoot(File root) {
        TreeItem<File> res = new TreeItem<>(root);
        res.setExpanded(true);
        if (root.isDirectory()) {
            root.getList().forEach(se -> {
                if (se.isSelected()) {
                    res.getChildren().add(makeTreeRoot(se));
                }
            });
        }
        return res;
    }
    
    public ObjectProperty<SimpleFile> selected_file_property() {
        return selected_file_property;
    }
    
    public void set_selected_file(File file){
        selected_file_property.setValue((SimpleFile) file);
    }
    
    public void openSelectedFile() {
        if(!selected_file_property.getValue().isDirectory()){
            editor.set_selected_file_name(selected_file_property.getValue().getName());
            editor.setText(selected_file_property.getValue().getFileContents());
            editor.setVisible(true);
        }
    }

    public EditVM getEditVM() {
        return editor;
    }
}
