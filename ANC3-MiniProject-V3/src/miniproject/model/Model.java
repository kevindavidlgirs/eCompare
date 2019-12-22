package miniproject.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import miniproject.InvalidTransferException;

public class Model {
    
    //Ajout
    private final ObservableList<String> toDoList = FXCollections.observableArrayList();
    private final ObservableList<String> doneList = FXCollections.observableArrayList();

    private static final int MIN_WORD_LENGTH = 3;
    
    //A supprimer
    //private final List<String> toDoList = new ArrayList<>();
    //private final List<String> doneList = new ArrayList<>();

    //Ajout
    public ObservableList<String> getToDoList() {
        return FXCollections.unmodifiableObservableList(toDoList);
    }
    
    public ObservableList<String> getDoneList() {
        return FXCollections.unmodifiableObservableList(doneList);
    }
    
    
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

    /*A supprimer
    public List<String> getToDoList() {
        return Collections.unmodifiableList(toDoList);
    }
    A supprimer
    public List<String> getDoneList() {
        return Collections.unmodifiableList(doneList);
    }*/

    public void setDone(int index) {
        if (index >= 0 && index < this.toDoList.size()) {
            this.doneList.add(this.toDoList.remove(index));
            //notif(TypeNotif.MOVE_LINE_RIGHT);
        } else {
            throw new InvalidTransferException("Index incorrect !");
        }
    }

    public void setToDo(int index) {
        if (index >= 0 && index < this.doneList.size()) {
            this.toDoList.add(this.doneList.remove(index));
            //notif(TypeNotif.MOVE_LINE_LEFT);
        } else {
            throw new InvalidTransferException("Index incorrect !");
        }
    }

    public boolean addToDo(String text) {
        if (text.length() >= MIN_WORD_LENGTH && !toDoList.contains(text) && !doneList.contains(text)) {
            toDoList.add(text);
            return true;
        }
        return false;
    }
/*A supprimer
    public void notif(TypeNotif typeNotif) {
        setChanged();
        notifyObservers(typeNotif);
    }

*/
    @Override
    public String toString() {
        String temp = "";
        for (int i = 0; i < this.toDoList.size(); ++i) {
            temp += toDoList.get(i);
        }
        return temp;
    }
}
