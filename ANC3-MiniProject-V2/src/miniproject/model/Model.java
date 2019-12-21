package miniproject.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import miniproject.InvalidTransferException;
import java.util.Observable;


public class Model extends Observable {

    public enum TypeNotif {
        INIT, LINE_SELECTED, LINE__UNSELECTED, MOVE_LINE_RIGHT, MOVE_LINE_LEFT, TEXT_ADDED;
    }

    private static final int MIN_WORD_LENGTH = 3;
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
            notif(TypeNotif.MOVE_LINE_RIGHT);
        } else {
            throw new InvalidTransferException("Index incorrect !");
        }
    }

    public void setToDo(int index) {
        if (index >= 0 && index < this.doneList.size()) {
            this.toDoList.add(this.doneList.remove(index));
            notif(TypeNotif.MOVE_LINE_LEFT);
        } else {
            throw new InvalidTransferException("Index incorrect !");
        }
    }

    public boolean addToDo(String text) {
        if (text.length() >= MIN_WORD_LENGTH && !toDoList.contains(text) && !doneList.contains(text)) {
            toDoList.add(text);
            notif(TypeNotif.TEXT_ADDED);
            return true;
        }
        return false;
    }

    public void notif(TypeNotif typeNotif) {
        setChanged();
        notifyObservers(typeNotif);
    }


    @Override
    public String toString() {
        String temp = "";
        for (int i = 0; i < this.toDoList.size(); ++i) {
            temp += toDoList.get(i);
        }
        return temp;
    }
}
