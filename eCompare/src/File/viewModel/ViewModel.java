/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.viewModel;

import File.model.Model;
import sun.reflect.generics.tree.Tree;

import java.io.IOException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


/**
 *
 * @author herve
 */
public class ViewModel {
    private final Model model;
    private final EditVM editorL;
    private final EditVM editorR;
    private final StatusButtonsVM statusBts;
    private final TreeItemVM root_left;
    private final TreeItemVM root_right;
    private final BooleanProperty actionEnabled = new SimpleBooleanProperty(false);


    public ViewModel(Model model) {
        this.model = model;
        editorL = new EditVM(this, "left");
        editorR = new EditVM(this, "right");
    
        root_left = new TreeItemVM(model, editorL);
        root_right = new TreeItemVM(model, editorR);
        
        statusBts = new StatusButtonsVM(this, model);
        
        root_left.set_root(model.get_left_struct_folder());
        root_right.set_root(model.get_right_struct_folder());
    }

    public EditVM getEditLeftVM() { return editorL; }

    public EditVM getEditRightVM() { return editorR; }
    
    public EditVM getEditVM(String side){
        if(side.equals("left")){
            return editorL;
        }else{
            ;return editorR;
        }
    }

    public StatusButtonsVM getStatusBts() { return statusBts; }

    public TreeItemVM getTreeItem(String side){
        if(side.equals("left")){
            return root_left;
        }else{
            return root_right;
        }
    }
    
    public void compare_folders(){
          model.get_left_struct_folder().compare(model.get_left_struct_folder(), model.get_right_struct_folder());
    }
    
    public void moveItems(){
        model.moveItems();
        compare_folders();
        
        getTreeItem("left").refresh_root("left");
        getTreeItem("right").refresh_root("right");
    }
    
    public BooleanProperty actionEnabledProperty() {
        return actionEnabled;
    }
    
    public void set_treeItem(String path, String side) throws IOException {
        root_left.set_treeItem(path, side);
        root_left.set_root(model.get_left_struct_folder());
        root_right.set_root(model.get_right_struct_folder());
    }
}
