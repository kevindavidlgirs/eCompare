/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.model;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 *
 * @author herve
 */
public class Model {
    private File file_structure_left;
    private File file_structure_right;
    private final List<String> statusSelectedForView = new ArrayList<>();

    public Model(String path1, String path2) throws IOException {
        file_structure_left = FileBuilder.make(Paths.get(path1).toRealPath());
        file_structure_right = FileBuilder.make(Paths.get(path2).toRealPath());
        file_structure_left.compare(file_structure_left, file_structure_right);
    }

    public List<String> get_list_status_selected(){ return Collections.unmodifiableList(statusSelectedForView); }

    public void clear_statusList(){
        statusSelectedForView.clear();
    }

    public File get_left_struct_folder(){
        return file_structure_left;
    }

    public File get_right_struct_folder(){
        return file_structure_right;
    }
    
    public void set_left_struct_folder(String path) throws IOException{
        file_structure_left = FileBuilder.make(Paths.get(path).toRealPath());
        file_structure_left.compare(file_structure_left, file_structure_right);
        set_struct_selected_items(file_structure_left, "left");
        set_struct_selected_items(file_structure_right, "right");
    }

    public void set_right_struct_folder(String path) throws IOException{
        file_structure_right = FileBuilder.make(Paths.get(path).toRealPath());
        file_structure_right.compare(file_structure_left, file_structure_right);
        set_struct_selected_items(file_structure_right, "right");
        set_struct_selected_items(file_structure_left, "left");
    }

    public void add_status_to_edit(String s){
        if(s.equals("All")){
            set_all_status_true(file_structure_left);
            set_all_status_true(file_structure_right);
        }else if(s.equals("Folders Only") && statusSelectedForView.size() == 0) {
            String well_formatted_status = status_converter(s);
            statusSelectedForView.add(well_formatted_status);
            set_just_folders_true(file_structure_left);
            set_just_folders_true(file_structure_right);
        }else{
            String well_formatted_status = status_converter(s);
            if(!statusSelectedForView.contains(well_formatted_status)){
                if(statusSelectedForView.contains("NEWERL") && well_formatted_status.equals("NEWERR")
                        || statusSelectedForView.contains("NEWERR") && well_formatted_status.equals("NEWERL")){
                    if (statusSelectedForView.contains("NEWERL")) {
                        statusSelectedForView.remove("NEWERL");
                    } else {
                        statusSelectedForView.remove("NEWERR");
                    }
                }
                statusSelectedForView.add(well_formatted_status);
            }
            set_struct_selected_items(file_structure_left, "left");
            set_struct_selected_items(file_structure_right, "right");
        }
    }

    public void remove_status_to_edit(String s){
        String well_formatted_status = status_converter(s);
        if(statusSelectedForView.contains(well_formatted_status)){
            this.statusSelectedForView.remove(well_formatted_status);
        }
        if(statusSelectedForView.size() == 1 && statusSelectedForView.contains("D")){
            set_just_folders_true(file_structure_left);
            set_just_folders_true(file_structure_right);
        }else{
            set_struct_selected_items(file_structure_left, "left");
            set_struct_selected_items(file_structure_right, "right");
        }
    }

    private void set_all_status_true(File f){
        if(f.isDirectory() && f.getList().size() != 0){
            f.getList().forEach(fl -> {
                set_all_status_true(fl);
            });
        }
        f.set_selected(true);
    }

    private void set_just_folders_true(File f){
        if(f.isDirectory() && f.getList().size() != 0){
            f.getList().forEach(fl -> {
                set_just_folders_true(fl);
            });
        }
        if(f.isDirectory()){
            f.set_selected(true);
        }else{
            f.set_selected(false);
        }
    }

    public void set_struct_selected_items(File f, String side){
        if(f.isDirectory() && f.getList().size() != 0 && !statusSelectedForView.isEmpty()){
            for(File f1 : f.getList()){
                set_struct_selected_items(f1, side);
            }
        }
        set_status(f, side);
    }

    private void set_status(File f, String side){
        if(f.isDirectory() && f.getList().size() != 0) {
            f.set_selected(false);
            for (File f1 : f.getList()) {
                if (f1.isSelected()) {
                    if (!f1.isDirectory() && statusSelectedForView.contains("D")) {
                        f.set_selected(true);
                        f1.set_selected(false);
                    } else {
                        f.set_selected(true);
                    }
                }
            }
        }else if(statusSelectedForView.contains("NEWERL") && ((side.equals("left") && f.getStatus().toString().equals("NEWER"))
                                                                || side.equals("right") && f.getStatus().toString().equals("OLDER"))) {
            f.set_selected(true);
        }else if(statusSelectedForView.contains("NEWERR") && ((side.equals("right") && f.getStatus().toString().equals("NEWER"))
                                                                || side.equals("left") && f.getStatus().toString().equals("OLDER"))){
            f.set_selected(true);
        }else if(statusSelectedForView.contains(f.getStatus().toString())){
            f.set_selected(true);
        }else {
            f.set_selected(false);
        }
    }

    public String status_converter(String s){
        String result="";
        if(s.equals("Newer Left")) {
            result = "NEWERL";
        }else if(s.equals("Newer Right")){
            result = "NEWERR";
        }else if(s.equals("Orphans")){
            result = "ORPHAN";
        } else if(s.equals("Same")){
            result = "SAME";
        } else if(s.equals("Folders Only")){
            result = "D";
        }
        return result;
    }

}
