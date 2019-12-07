/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniproject;

import static java.nio.file.Files.lines;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ListView;

/**
 *
 * @author 2207hembilo
 */
public class Model {
    private final List<String> toDoList = new ArrayList<>();
    private final List<String> doneList = new ArrayList<>();
    
    public List<String> getToDoList() {
        return Collections.unmodifiableList(toDoList);
    }

    public List<String> getDoneList() {
        return Collections.unmodifiableList(doneList);
    }
    
    public void setDone(int index){
        if(index >= 0 && index < this.toDoList.size()){
            this.doneList.add(this.toDoList.remove(index));
        }else{
            throw new InvalidTransferException();
        }     
    }
    
     public void setToDo(int index){
        if(index >= 0 && index < this.doneList.size()){
            this.toDoList.add(this.doneList.remove(index));
        }else{
            throw new InvalidTransferException();
        }
    }
   
/*
    private static void transfer(ListView<String> fromList, ListView<String> toList) {
        final int idxSel = fromList.getSelectionModel().getSelectedIndex();
        if (idxSel != -1) {
            toList.getItems().add(fromList.getItems().remove(idxSel));
        }
    }
   /*
    private void addToDo(String text) {
        if (text.length() > 2 && !toDoList.getItems().contains(text) && !doneList.getItems().contains(text)) {
            toDoList.getItems().add(text);
            addText.setText("");
        }
    }
    */
    public Model(List<String> t){
        
        for(int i =0; i < t.size(); ++i){
            if(t.get(i).length() >= 3){
                if(!toDoList.contains(t.get(i))){
                    this.toDoList.add(t.get(i));
                }
            }
        }       
    }
    
    public Model(){}
    
    public String toString(){
        String temp ="";
        
        for(int i = 0; i < this.toDoList.size();++i){
            temp += toDoList.get(i);
        }
        return temp;
    }
}
