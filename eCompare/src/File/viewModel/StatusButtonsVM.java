package File.viewModel;

import File.model.Model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class StatusButtonsVM {
    private final Model model;
    private final ViewModel vm;
    private final BooleanProperty all_button = new SimpleBooleanProperty(true);
    private final BooleanProperty newer_left_button = new SimpleBooleanProperty(false);
    private final BooleanProperty newer_right_button = new SimpleBooleanProperty(false);
    private final BooleanProperty orphans_button = new SimpleBooleanProperty(false);
    private final BooleanProperty same_button = new SimpleBooleanProperty(false);
    private final BooleanProperty folders_only = new SimpleBooleanProperty(false);
    private final BooleanProperty actionEnabled = new SimpleBooleanProperty(false);

    public StatusButtonsVM(ViewModel vm, Model model){
        this.model = model;
        this.vm = vm;
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

    public void add_status_to_edit(String status) {
        model.add_status_to_edit(status);
    }

    public void remove_status_to_edit(String status) {
        model.remove_status_to_edit(status);
    }

    public int get_nb_status() {
        return model.get_list_status_selected().size();
    }

    public void set_all_buttons_status_false() {
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
        vm.getTreeItem("left").set_root(model.get_left_struct_folder());
        vm.getTreeItem("Right").set_root(model.get_right_struct_folder());
    }
}
