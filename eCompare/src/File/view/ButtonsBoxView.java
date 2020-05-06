/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.view;

import File.viewModel.ViewModel;
import javafx.geometry.Insets;
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

    //private final ToggleGroup transition = new ToggleGroup();

    {
        getChildren().addAll(all_button, newer_left_button, newer_right_button, orphans_button, same_button, folders_only);
    }
    
    public ButtonsBoxView(ViewModel vm){
        newer_right_button.setToggleGroup(newer_left_right);
        newer_left_button.setToggleGroup(newer_left_right);

        //Devrait se retrouver dans le "cssView.css" avec une chose du genre -> .toggle-button:selected { ..... }
        all_button.setStyle("-fx-font-weight: bold");
        //Devrait se retrouver dans le "cssView.css" avec une chose du genre -> .toggle-button:selected { ..... }

        setSpacing(10);
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER);
        setBinding(vm);

        all_button.setOnAction(e -> {
            vm.set_selected_items(all_button.getText(), all_button.isSelected());

            //Devrait se retrouver dans le "cssView.css" avec une chose du genre -> .toggle-button:selected { ..... }
            if(all_button.isSelected()){
                all_button.setStyle("-fx-font-weight: bold");
                newer_right_button.setStyle("-fx-font-weight: regular");
                newer_left_button.setStyle("-fx-font-weight: regular");
                orphans_button.setStyle("-fx-font-weight: regular");
                same_button.setStyle("-fx-font-weight: regular");
                folders_only.setStyle("-fx-font-weight: regular");
            } else {
                all_button.setStyle("-fx-font-weight: regular");
            }
            //Devrait se retrouver dans le "cssView.css" avec une chose du genre -> .toggle-button:selected { ..... }
        });
        
        newer_right_button.setOnAction(e -> {
            vm.set_selected_items(newer_right_button.getText(), newer_right_button.isSelected());
            //Devrait se retrouver dans le "cssView.css" avec une chose du genre -> .toggle-button:selected { ..... }
            if(newer_right_button.isSelected()){
                newer_right_button.setStyle("-fx-font-weight: bold");
                newer_left_button.setStyle("-fx-font-weight: regular");
                all_button.setStyle("-fx-font-weight: regular");
            } else {
                newer_right_button.setStyle("-fx-font-weight: regular");
            }
            //Devrait se retrouver dans le "cssView.css" avec une chose du genre -> .toggle-button:selected { ..... }
        });
        
        newer_left_button.setOnAction(e -> {
            vm.set_selected_items(newer_left_button.getText(), newer_left_button.isSelected());

            //Devrait se retrouver dans le "cssView.css" avec une chose du genre -> .toggle-button:selected { ..... }
            if(newer_left_button.isSelected()){
                newer_left_button.setStyle("-fx-font-weight: bold");
                newer_right_button.setStyle("-fx-font-weight: regular");
                all_button.setStyle("-fx-font-weight: regular");
            } else {
                newer_left_button.setStyle("-fx-font-weight: regular");
            }
            //Devrait se retrouver dans le "cssView.css" avec une chose du genre -> .toggle-button:selected { ..... }
        });
        
        orphans_button.setOnAction(e -> {
            vm.set_selected_items(orphans_button.getText(), orphans_button.isSelected());

            //Devrait se retrouver dans le "cssView.css" avec une chose du genre -> .toggle-button:selected { ..... }
            if(orphans_button.isSelected()){
                orphans_button.setStyle("-fx-font-weight: bold");
                all_button.setStyle("-fx-font-weight: regular");
            } else {
                orphans_button.setStyle("-fx-font-weight: regular");
            }
            //Devrait se retrouver dans le "cssView.css" avec une chose du genre -> .toggle-button:selected { ..... }
        });
        
        same_button.setOnAction(e -> {
            vm.set_selected_items(same_button.getText(), same_button.isSelected());

            //Devrait se retrouver dans le "cssView.css" avec une chose du genre -> .toggle-button:selected { ..... }
            if(same_button.isSelected()) {
                same_button.setStyle("-fx-font-weight: bold");
                all_button.setStyle("-fx-font-weight: regular");
            } else {
                same_button.setStyle("-fx-font-weight: regular");
            }
            //Devrait se retrouver dans le "cssView.css" avec une chose du genre -> .toggle-button:selected { ..... }
        });
        
        folders_only.setOnAction(e -> {
            vm.set_selected_items(folders_only.getText(), folders_only.isSelected());

            //Devrait se retrouver dans le "cssView.css" avec une chose du genre -> .toggle-button:selected { ..... }
            if(folders_only.isSelected()) {
                folders_only.setStyle("-fx-font-weight: bold");
                all_button.setStyle("-fx-font-weight: regular");
            } else {
                folders_only.setStyle("-fx-font-weight: regular");
            }
            //Devrait se retrouver dans le "cssView.css" avec une chose du genre -> .toggle-button:selected { ..... }
        });
    }

    private void setBinding(ViewModel vm) {
        all_button.selectedProperty().bindBidirectional(vm.all_button_Property());
        newer_right_button.selectedProperty().bindBidirectional(vm.newer_right_button_Property());
        newer_left_button.selectedProperty().bindBidirectional(vm.newer_left_button_Property());
        orphans_button.selectedProperty().bindBidirectional(vm.orphans_button_Property());
        same_button.selectedProperty().bindBidirectional(vm.same_button_Property());
        folders_only.selectedProperty().bindBidirectional(vm.folders_only_Property());
    }
}
