/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.view;

import File.viewModel.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class ButtonsBoxView extends HBox{

    private final ToggleButton all_button = new ToggleButton("All");
    private final ToggleButton newer_left_button = new ToggleButton("Newer Left");
    private final ToggleButton newer_right_button = new ToggleButton("Newer Right");
    private final ToggleButton orphans_button = new ToggleButton("Orphans");
    private final ToggleButton same_button = new ToggleButton("Same");
    private final ToggleButton folders_only = new ToggleButton("Folders Only");
    private final ToggleButton bigs_files = new ToggleButton("BigsFiles");
    private final ToggleGroup newer_left_right = new ToggleGroup();
    private final BooleanProperty actionEnabled = new SimpleBooleanProperty(true);

    {
        getChildren().addAll(all_button, newer_left_button, newer_right_button, orphans_button, same_button, folders_only, bigs_files);
    }
    
    public ButtonsBoxView(ViewModel vm){
        newer_right_button.setToggleGroup(newer_left_right);
        newer_left_button.setToggleGroup(newer_left_right);
        vm.actionEnabledProperty().bind(actionEnabled);
        all_button.setStyle("-fx-font-weight: bold");

        setSpacing(10);
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER);
        setBinding(vm);

        all_button.setOnAction(e -> {
            vm.getStatusBts().set_selected_items(all_button.getText(), all_button.isSelected());
            if(all_button.isSelected()){
                actionEnabled.setValue(true);
                all_button.setStyle("-fx-font-weight: bold");
                newer_right_button.setStyle("-fx-font-weight: regular");
                newer_left_button.setStyle("-fx-font-weight: regular");
                orphans_button.setStyle("-fx-font-weight: regular");
                same_button.setStyle("-fx-font-weight: regular");
                folders_only.setStyle("-fx-font-weight: regular");
                bigs_files.setStyle("-fx-font-weight: regular");
            } else {
                all_button.setStyle("-fx-font-weight: regular");
            }
        });
        
        newer_right_button.setOnAction(e -> {
            vm.getStatusBts().set_selected_items(newer_right_button.getText(), newer_right_button.isSelected());
            if(newer_right_button.isSelected()){
                actionEnabled.setValue(false);
                newer_right_button.setStyle("-fx-font-weight: bold");
                newer_left_button.setStyle("-fx-font-weight: regular");
                all_button.setStyle("-fx-font-weight: regular");
                bigs_files.setStyle("-fx-font-weight: regular");
            } else {
                if(!vm.getStatusBts().orphans_button_Property().getValue() && !vm.getStatusBts().newer_left_button_Property().getValue()){
                     actionEnabled.setValue(true);
                }
                newer_right_button.setStyle("-fx-font-weight: regular");
            }
        });
        
        newer_left_button.setOnAction(e -> {
            vm.getStatusBts().set_selected_items(newer_left_button.getText(), newer_left_button.isSelected());

            if(newer_left_button.isSelected()){
                actionEnabled.setValue(false);
                newer_left_button.setStyle("-fx-font-weight: bold");
                newer_right_button.setStyle("-fx-font-weight: regular");
                all_button.setStyle("-fx-font-weight: regular");
                bigs_files.setStyle("-fx-font-weight: regular");
            } else {
                if(!vm.getStatusBts().newer_right_button_Property().getValue() && !vm.getStatusBts().orphans_button_Property().getValue()){
                     actionEnabled.setValue(true);
                }
                newer_left_button.setStyle("-fx-font-weight: regular");
            }
        });
        
        orphans_button.setOnAction(e -> {
            vm.getStatusBts().set_selected_items(orphans_button.getText(), orphans_button.isSelected());

            if(orphans_button.isSelected()){
                actionEnabled.setValue(false);
                orphans_button.setStyle("-fx-font-weight: bold");
                all_button.setStyle("-fx-font-weight: regular");
                bigs_files.setStyle("-fx-font-weight: regular");
            } else {
               if(!vm.getStatusBts().newer_right_button_Property().getValue() && !vm.getStatusBts().newer_left_button_Property().getValue()){
                     actionEnabled.setValue(true);
                }
                orphans_button.setStyle("-fx-font-weight: regular");
            }
        });
        
        same_button.setOnAction(e -> {
            vm.getStatusBts().set_selected_items(same_button.getText(), same_button.isSelected());

            if(same_button.isSelected()) {
                same_button.setStyle("-fx-font-weight: bold");
                all_button.setStyle("-fx-font-weight: regular");
                bigs_files.setStyle("-fx-font-weight: regular");
            } else {
                same_button.setStyle("-fx-font-weight: regular");
            }
        });
        
        folders_only.setOnAction(e -> {
            vm.getStatusBts().set_selected_items(folders_only.getText(), folders_only.isSelected());

            if(folders_only.isSelected()) {
                folders_only.setStyle("-fx-font-weight: bold");
                all_button.setStyle("-fx-font-weight: regular");
                bigs_files.setStyle("-fx-font-weight: regular");
            } else {
                folders_only.setStyle("-fx-font-weight: regular");
            }
        });

        bigs_files.setOnAction(e -> {
            vm.getStatusBts().set_selected_items(bigs_files.getText(), bigs_files.isSelected());

            if(bigs_files.isSelected()){
                actionEnabled.setValue(true);
                bigs_files.setStyle("-fx-font-weight: bold");
                all_button.setStyle("-fx-font-weight: regular");
                newer_right_button.setStyle("-fx-font-weight: regular");
                newer_left_button.setStyle("-fx-font-weight: regular");
                orphans_button.setStyle("-fx-font-weight: regular");
                same_button.setStyle("-fx-font-weight: regular");
                folders_only.setStyle("-fx-font-weight: regular");
            } else {
                bigs_files.setStyle("-fx-font-weight: regular");
            }
        });
    }
     
    private void setBinding(ViewModel vm) {
        all_button.selectedProperty().bindBidirectional(vm.getStatusBts().all_button_Property());
        newer_right_button.selectedProperty().bindBidirectional(vm.getStatusBts().newer_right_button_Property());
        newer_left_button.selectedProperty().bindBidirectional(vm.getStatusBts().newer_left_button_Property());
        orphans_button.selectedProperty().bindBidirectional(vm.getStatusBts().orphans_button_Property());
        same_button.selectedProperty().bindBidirectional(vm.getStatusBts().same_button_Property());
        folders_only.selectedProperty().bindBidirectional(vm.getStatusBts().folders_only_Property());
        bigs_files.selectedProperty().bindBidirectional(vm.getStatusBts().bigs_files_Property());
    }
}
