/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.model;

import java.io.IOException;
import java.nio.file.Path;
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
        file_structure_left.compare(file_structure_left, file_structure_right);
        set_struct_selected_items(file_structure_right, "right");
        set_struct_selected_items(file_structure_left, "left");
    }

    public void add_status_to_edit(String s){
        if(s.equals("All")){
            set_all_status_true(file_structure_left);
            set_all_status_true(file_structure_right);
        }else if(s.equals("Folders Only") && statusSelectedForView.isEmpty()) {
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
        if(f.isDirectory() && !f.getList().isEmpty()){
            f.getList().forEach(fl -> {
                set_all_status_true(fl);
            });
        }
        f.set_selected(true);
    }

    private void set_just_folders_true(File f){
        if(f.isDirectory() && !f.getList().isEmpty()){
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
        if(f.isDirectory() && !f.getList().isEmpty() && !statusSelectedForView.isEmpty()){
            f.getList().forEach((f1) -> {
                set_struct_selected_items(f1, side);
            });
        }
        set_status(f, side);
    }

    private void set_status(File f, String side){
        if(f.isDirectory() && !f.getList().isEmpty()) {
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
        switch (s) {
            case "Newer Left":
                result = "NEWERL";
                break;
            case "Newer Right":
                result = "NEWERR";
                break;
            case "Orphans":
                result = "ORPHAN";
                break;
            case "Same":
                result = "SAME";
                break;
            case "Folders Only":
                result = "D";
                break;
            default:
                break;
        }
        return result;
    }


    public void moveItems(){
        if(statusSelectedForView.contains("ORPHAN")){
            searchOrphanFile(file_structure_left);
        }
        
        if(statusSelectedForView.contains("NEWERL")){
            searchNewerOrOlderFile(file_structure_left, "NEWER");
        }else if (statusSelectedForView.contains("NEWERR")){
            searchNewerOrOlderFile(file_structure_left, "OLDER");
        }
    }

    private void searchOrphanFile(File fileStructLeft){
        if(fileStructLeft.isDirectory() && !fileStructLeft.getStatus().toString().equals("ORPHAN")) {
            fileStructLeft.getList().forEach((f1) -> {
                if (f1.isDirectory() && !f1.getStatus().toString().equals("ORPHAN")) {
                    searchOrphanFile(f1);
                }else if (f1.getStatus().toString().equals("ORPHAN")){
                    copieOrphanFile(f1);
                }
            });
        }else if(fileStructLeft.getStatus().toString().equals("ORPHAN") ){
            copieOrphanFile(fileStructLeft);
        }
    }

    private boolean copieOrphanFile(File fileOrphan){
        boolean fileAdded = false;
        if(searchSameParentDirectory(fileOrphan, file_structure_right, fileAdded)){
            return true;
        }
        else if(searchSameParentPath(fileOrphan, file_structure_right, fileAdded)){
            return true;
        }else{
            file_structure_right.addFile(fileOrphan);
            return true;
        }
    }

    //recherche dans la structure de droite le même répertoire parent du dossier orphelin
    private boolean searchSameParentDirectory(File fileOrphan, File fileStructRight, boolean fileAdded){
        if(fileStructRight.isDirectory() && fileOrphan.isDirectory() && !fileOrphan.getList().isEmpty()){
            for(File f : fileStructRight.getList()){
                if(f.isDirectory()){
                    if(getPathRelativized(file_structure_right, f.getPath()).equals(getPathRelativized(file_structure_left, fileOrphan.getPath()))){
                        for(File f1 : fileOrphan.getList()){
                            if(!f.getList().contains(f1)){
                                //COPIE EN PROFONDEUR N'EST PAS IMPLEMENTEE
                                f.addFile(f1);
                                fileAdded = true;
                            }
                        }
                    }else{
                        fileAdded = searchSameParentDirectory(fileOrphan, f, fileAdded);
                    }
                }
            }
        }
        return fileAdded;
    }

    //recherche dans la structure de droite le même path parent du dossier orphelin
    //Gère le transfère de SimpleFile
    private boolean searchSameParentPath(File fileOrphan, File fileStructRight, boolean fileAdded){
        if(fileStructRight.isDirectory()){
            for(File f : fileStructRight.getList()){
                if(getPathRelativized(file_structure_right, f.getPath()).equals(getPathRelativized(file_structure_left, fileOrphan.getParent().getValue().getPath()))){
                    if(!f.getList().contains(fileOrphan)){
                        //COPIE EN PROFONDEUR N'EST PAS IMPLEMENTEE
                        f.addFile(fileOrphan);
                        fileAdded = true;
                    }
                }else{
                    fileAdded = searchSameParentPath(fileOrphan, f, fileAdded);
                }
            }
        }
        return fileAdded;
    }

    //Vu le temps qu'il me reste j'ai implémenté une solution déjà existante. (beaucoup de traitement...)
    //A peaufiner
    private void searchNewerOrOlderFile(File fileStructLeft, String status){
        if(fileStructLeft.isDirectory()) {
            fileStructLeft.getList().forEach((f1) -> {
                if (f1.isDirectory()) {
                    searchNewerOrOlderFile(f1, status);
                }else if (!f1.isDirectory() && f1.getStatus().toString().equals(status)){
                    copieNewerOrOlderFile(f1, file_structure_right);
                }
            });
        }else if(fileStructLeft.getStatus().toString().equals(status)){
            copieNewerOrOlderFile(fileStructLeft, file_structure_right);
        }
    }

    private void copieNewerOrOlderFile(File file, File fileStructRight){
        if(fileStructRight.isDirectory()){
            fileStructRight.getList().forEach((f) -> {
                if(getPathRelativized(file_structure_right, f.getPath()).equals(getPathRelativized(file_structure_left, file.getParent().getValue().getPath()))){
                    //f.getList(); retourne une unmodifiableList et de plus impossible d'itérer sur une list et de la modifier durant l'itération
                    //Cette solution n'est pas très intelligente mais fonctionnel, à peaufiner.
                    //COPIE EN PROFONDEUR N'EST PAS IMPLEMENTEE
                    if(f.isDirectory()){
                        List<File> listForItemsToBeRemoved = new ArrayList<>();
                        for(File f1 : f.getList()){
                            if(file.getName().equals(f1.getName())){
                                listForItemsToBeRemoved.add(f1);
                            }
                        }for(File f1 : listForItemsToBeRemoved){
                            f.removeFile(f1);
                            f.addFile(file);
                        }
                    }
                }else{
                    copieNewerOrOlderFile(file, f);
                }
            });
        }
    }

    //Permet de récupérer le path sans le chemin de "la racine au dossier" selectionné
    private Path getPathRelativized(File fileStructLeftOrRight, Path p){
        Path p1 = Paths.get(fileStructLeftOrRight.getPath().toString());
        return p1.relativize(p);
    }
}
