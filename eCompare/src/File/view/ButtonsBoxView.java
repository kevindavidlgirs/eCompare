/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.view;

import File.viewModel.ViewModel;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

/**
 *
 * @author herve
 */
public class ButtonsBoxView extends HBox{
    private final ToggleButton all_button = new ToggleButton("All");
    private final ToggleButton newer_left_button = new ToggleButton("Newer Left");
    private final ToggleButton newer_right_button = new ToggleButton("Newer Right");
    private final ToggleButton orphans_button = new ToggleButton("Orphans");
    private final ToggleButton same_button = new ToggleButton("Same");
    private final ToggleButton folders_only = new ToggleButton("Folders Only");
    private final ToggleGroup newer_left_right = new ToggleGroup();
    
    {
        getChildren().addAll(all_button, newer_left_button,newer_right_button,orphans_button,same_button,folders_only);
    }
    
    public ButtonsBoxView(ViewModel vm){
        setSpacing(10);
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER);
        
        //Permet de rendre les boutons newer_left et newer_right mutuellement exclusifs.
        newer_left_button.setToggleGroup(newer_left_right);
        newer_right_button.setToggleGroup(newer_left_right);
        
        all_button.selectedProperty().bindBidirectional(vm.all_button());
        newer_right_button.selectedProperty().bindBidirectional(vm.newer_right_button());
        newer_left_button.selectedProperty().bindBidirectional(vm.newer_left_button());
        orphans_button.selectedProperty().bindBidirectional(vm.orphans_button());
        same_button.selectedProperty().bindBidirectional(vm.same_button());
        folders_only.selectedProperty().bindBidirectional(vm.folders_only());
        
        
        all_button.setOnAction(e -> {
                vm.set_selected_items(all_button.getText().toString(),folders_only.isSelected());
        });
        
        newer_right_button.setOnAction(e -> {
            vm.set_selected_items(newer_right_button.getText().toString(),newer_right_button.isSelected());
        });
        
        newer_left_button.setOnAction(e -> {
            vm.set_selected_items(newer_left_button.getText().toString(), newer_left_button.isSelected());
        });
        
        orphans_button.setOnAction(e -> {
            vm.set_selected_items(orphans_button.getText().toString(),orphans_button.isSelected());
        });
        
        same_button.setOnAction(e -> {
            vm.set_selected_items(same_button.getText().toString(),same_button.isSelected());
        });
        
        folders_only.setOnAction(e -> {
            vm.set_selected_items(folders_only.getText().toString(),folders_only.isSelected());
        });
    }
}
