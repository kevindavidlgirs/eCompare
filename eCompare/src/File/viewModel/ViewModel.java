/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.viewModel;

import File.model.Model;
import File.model.File;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;

/**
 *
 * @author herve
 */
public class ViewModel {
    private final Model model;
    private final TreeItem<File> left_tree_item;
    private final TreeItem<File> right_tree_item;
    private final BooleanProperty all_button = new SimpleBooleanProperty(true);
    private final BooleanProperty newer_left_button = new SimpleBooleanProperty(false);
    private final BooleanProperty newer_right_button = new SimpleBooleanProperty(false);
    private final BooleanProperty orphans_button = new SimpleBooleanProperty(false);
    private final BooleanProperty same_button = new SimpleBooleanProperty(false);
    private final BooleanProperty folders_only = new SimpleBooleanProperty(false);
    
    public ViewModel(Model model){
        this.model = model;
        left_tree_item = makeTreeRoot(model.get_left_struct_folder());
        right_tree_item = makeTreeRoot(model.get_right_struct_folder());
    }

    public TreeItem<File> get_left_treeItem(){
        return left_tree_item;
    }

    public TreeItem<File> get_right_treeItem(){
        return right_tree_item;
    }

    public BooleanProperty newer_left_button(){
        return newer_left_button;
    }
    
    public BooleanProperty newer_right_button(){
        return newer_right_button;
    }
    
    public BooleanProperty orphans_button(){
        return orphans_button;
    }
    
    public BooleanProperty same_button(){
        return same_button;
    }
    
    public BooleanProperty all_button(){
        return all_button;
    }
    
    public BooleanProperty folders_only(){
        return folders_only;
    }
    
    public void add_status_to_edit(String status){
        model.add_status_to_edit(status);
    }
    
    public void remove_status_to_edit(String status){
        model.remove_status_to_edit(status);
    }
    
    public int get_nb_status(){
        return model.get_status().size();
    }
    
    // Méthode pour tester les éléments qui entrent dans la liste. en fonction du bouton appuyé.
    public void display_Status(){
        for( String s : model.get_status()){
            System.out.println(s.toString());
        }
    }
    
    //La valeur des boutons est stockée dans une liste (add_status_to_edit) qui est mise à jour à chaque action.
     public void set_selected_items(String status, Boolean buttons){         
         if(buttons){
             add_status_to_edit(status);
         }else {
             remove_status_to_edit(status);
         }
         
         //display_Status();
         //System.out.println(get_nb_status());
         //model.set_struct_selected_items(...);
     }
     
    private TreeItem<File> makeTreeRoot(File root) {

        TreeItem<File> res = new TreeItem<>(root);
        res.setExpanded(true);

        if (root.isDirectory()) {

            root.getList().forEach(se -> {
                res.getChildren().add(makeTreeRoot(se));
            });
        }
        
        return res;
    }
}
