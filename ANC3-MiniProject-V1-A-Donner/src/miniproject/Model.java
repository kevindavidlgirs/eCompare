/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniproject;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.control.ListView;

/**
 *
 * @author 2207hembilo
 */
public class Model {

    private final List<String> toDoList = new ArrayList<>();
    private final List<String> doneList = new ArrayList<>();

    public Model() {
    }

    public Model(List<String> t) {
        for (int i = 0; i < t.size(); ++i) {
            if (t.get(i).length() >= 3) {
                if (!toDoList.contains(t.get(i))) {
                    this.toDoList.add(t.get(i));
                }
            }
        }
    }

    public List<String> getToDoList() {
        return Collections.unmodifiableList(toDoList);
    }

    public List<String> getDoneList() {
        return Collections.unmodifiableList(doneList);
    }

    public void setDone(int index) {
        if (index >= 0 && index < this.toDoList.size()) {
            this.doneList.add(this.toDoList.remove(index));
        } else {
            throw new InvalidTransferException("Index incorrect !");
        }
    }

    public void setToDo(int index) {
        if (index >= 0 && index < this.doneList.size()) {
            this.toDoList.add(this.doneList.remove(index));
        } else {
            throw new InvalidTransferException("Index incorrect !");
        }
    }

    public boolean addToDo(String text) {
        if (text.length() > 2 && !toDoList.contains(text) && !doneList.contains(text)) {
            toDoList.add(text);
            return true;
        }
        return false;
    }

    public void transfer(String toDoOrDone,int index) {
        if (index != -1) {            
            if(">>".equals(toDoOrDone)){
                setDone(index);
            }else{
                setToDo(index);
            }
        }
    }
   
    public String toString(){
        String temp ="";
        
        for(int i = 0; i < this.toDoList.size();++i){
            temp += toDoList.get(i);
        }
        return temp;
    }
}
