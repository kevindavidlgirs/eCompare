/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.model;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        // On récupère le nom du bouton sur un format compréhensible par le programme.
        String well_formatted_status = status_converter(s);
        // Le champ selected des ducuments ont par défaut la valeur true. donc pas besoin de faire statusSelectedForView.add();
        if(s.equals("All")){ // Si all est sélectionné.   
            set_all_status_true(file_structure_left); // On change la valeur du champ selected à true de tous documents de la structure de fichier.
            set_all_status_true(file_structure_right);
        }else if(s.equals("Folders Only") && statusSelectedForView.isEmpty()) {
            statusSelectedForView.add(well_formatted_status);
            set_just_folders_true(file_structure_left);
            set_just_folders_true(file_structure_right);
        }else if(s.equals("BigsFiles")){
            set_just_bigs_files_true(file_structure_left);
            set_just_bigs_files_true(file_structure_right);
        } else{
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
            //Quelques conditions à ajouter dans set_struct_selected_items
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
    
    private void set_just_bigs_files_true(File f){
         if(f.isDirectory() && !f.getList().isEmpty()){
            f.getList().forEach(fl -> {
                set_just_bigs_files_true(fl);
            });
        }
        
        if(f.getSize() >= 50){
            f.set_selected(true);
        }else{
            f.set_selected(false);
        }
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
            case "BigsFiles":
                result = "BIGSF";
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
        if(fileStructLeft.isDirectory()) {
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
        if(searchSameParentDirectoryForFileOrphan(fileOrphan, file_structure_right, false)){
            return true;
        }
        else if(searchSameParentPathForFileOrphan(fileOrphan, file_structure_right, false)){
            return true;
        }else{
            file_structure_right.addFile(fileOrphan.isDirectory() ? new Directory(fileOrphan, Paths.get(file_structure_right.getPath().toString() + "\\" + fileOrphan.getName())) : new SimpleFile(fileOrphan, Paths.get(file_structure_right.getPath().toString() + "\\" + fileOrphan.getName())));
            return true;
        }
    }

    private boolean searchSameParentDirectoryForFileOrphan(File fileOrphan, File fileStructRight, boolean fileAdded){
        if(fileStructRight.isDirectory() && fileOrphan.isDirectory() && !fileOrphan.getList().isEmpty()){
            for(File f : fileStructRight.getList()){
                if(f.isDirectory()){
                    if(getPathRelativized(file_structure_right, f.getPath()).equals(getPathRelativized(file_structure_left, fileOrphan.getPath()))){
                        fileAdded = copyOrphanFileWithSameParentDirectory(fileOrphan, f);
                    }else{
                        fileAdded = searchSameParentDirectoryForFileOrphan(fileOrphan, f, fileAdded);
                    }
                }
            }
        }
        return fileAdded;
    }

    private boolean copyOrphanFileWithSameParentDirectory(File fileOrphan, File f){
        List<File> fileToBeAdded = new ArrayList<>();
        for(File f1 : fileOrphan.getList()){
            boolean fileExiste = false;
            for(File f2 : f.getList()){
                if(f1.getName().equals(f2.getName())){
                    fileExiste = true;
                }
            }
            if(!fileExiste){
                fileToBeAdded.add(f1);
            }
        }
        if(!fileToBeAdded.isEmpty()) {
            for (File f1 : fileToBeAdded) {
                f.addFile(f1.isDirectory() ? new Directory(f1, Paths.get(f.getPath().toString()+"\\"+f1.getName())) : new SimpleFile(f1, Paths.get(f.getPath().toString()+"\\"+f1.getName())));
            }
            return true;
        }
        return false;
    }

    private boolean searchSameParentPathForFileOrphan(File fileOrphan, File fileStructRight, boolean fileAdded){
        if(fileStructRight.isDirectory()){
            for(File f : fileStructRight.getList()){
                if(getPathRelativized(file_structure_right, f.getPath()).equals(getPathRelativized(file_structure_left, fileOrphan.getParent().getValue().getPath()))){
                    copyOrphanFileWithSameParentPath(fileOrphan, f);
                }else{
                    fileAdded = searchSameParentPathForFileOrphan(fileOrphan, f, fileAdded);
                }
            }
        }
        return fileAdded;
    }

    private boolean copyOrphanFileWithSameParentPath(File fileOrphan, File f){
        boolean fileExiste = false;
        for(File f1 : f.getList()) {
            if (f1.getName().equals(fileOrphan.getName())) {
                fileExiste = true;
            }
        }
        if(!fileExiste){
            f.addFile(fileOrphan.isDirectory() ? new Directory(fileOrphan, Paths.get(f.getPath().toString() + "\\" + fileOrphan.getName())) : new SimpleFile(fileOrphan, Paths.get(f.getPath().toString() + "\\" + fileOrphan.getName())));
            return true;
        }
        return false;
    }

    private void searchNewerOrOlderFile(File fileStructLeft, String status){
        if(fileStructLeft.isDirectory()) {
            fileStructLeft.getList().forEach((f1) -> {
                if (f1.isDirectory()) {
                    searchNewerOrOlderFile(f1, status);
                }else if (!f1.isDirectory() && f1.getStatus().toString().equals(status)){
                    copyNewerOrOlderFile(f1);
                }
            });
        }else if(fileStructLeft.getStatus().toString().equals(status)){
            copyNewerOrOlderFile(fileStructLeft);
        }
    }

    private boolean copyNewerOrOlderFile(File file){
        if(searchSameFileInStructRightForNewerOrOlderFile(file, file_structure_right, false)){
            return true;
        }else{
            File f;
            for(File f1 : file_structure_right.getList()){
                if(f1.getName().equals(file.getName())){
                    f = f1;
                    file_structure_right.removeFile(f);
                    file_structure_right.addFile(file.isDirectory() ? new Directory(file, Paths.get(file_structure_right.getPath().toString() + "\\" + file.getName())) : new SimpleFile(file, Paths.get(file_structure_right.getPath().toString() + "\\" + file.getName())));
                    break;
                }
            }
             return true;
        }
    }

    private boolean searchSameFileInStructRightForNewerOrOlderFile(File file, File fileStructRight, boolean fileAdded){
        if(fileStructRight.isDirectory()){
            for (File f : fileStructRight.getList()) {
                if (getPathRelativized(file_structure_right, f.getPath()).equals(getPathRelativized(file_structure_left, file.getParent().getValue().getPath()))) {
                    fileAdded = deleteAndCopyNewerOrOlderFile(f, file);
                } else {
                    fileAdded = searchSameFileInStructRightForNewerOrOlderFile(file, f, fileAdded);
                }
            }
        }
        return fileAdded;
    }

    private boolean deleteAndCopyNewerOrOlderFile(File f, File file){
        if (f.isDirectory()) {
            List<File> listForItemsToBeRemoved = new ArrayList<>();
            for (File f1 : f.getList()) {
                if (file.getName().equals(f1.getName())) {
                    listForItemsToBeRemoved.add(f1);
                }
            }
            for (File f1 : listForItemsToBeRemoved) {
                f.removeFile(f1);
                f.addFile(file.isDirectory() ? new Directory(file, Paths.get(f.getPath().toString() + "\\" + file.getName())) : new SimpleFile(file, Paths.get(f.getPath().toString() + "\\" + file.getName())));
            }
            if(!listForItemsToBeRemoved.isEmpty())
                return true;
        }
        return false;
    }
    
    public void deleteFile(File selectedFile, File fileStruct) {
        if (fileStruct.isDirectory() && !fileStruct.getList().contains(selectedFile.getValue())) {
            for(File file : fileStruct.getList()){
                if (file.getPath().equals(selectedFile.getParent().getValue().getPath())) {
                    file.removeFile(selectedFile.getValue());
                } else {
                    deleteFile(selectedFile, file);
                }
            }
        }else if(fileStruct.isDirectory() && fileStruct.getList().contains(selectedFile.getValue())){
            fileStruct.removeFile(selectedFile.getValue());
        }
    }

    private Path getPathRelativized(File fileStructLeftOrRight, Path p){
        Path p1 = Paths.get(fileStructLeftOrRight.getPath().toString());
        return p1.relativize(p);
    }
}
