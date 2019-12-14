/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniproject.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import miniproject.InvalidTransferException;
import java.util.Observable;

/**
 *
 * @author 2207hembilo
 */
public class Model extends Observable {

    public enum TypeNotif {
        INIT, LINE_SELECTED, LINE__UNSELECTED, MOVE_LINE_RIGHT, MOVE_LINE_LEFT, LINE_ADDED;
    }

    private static final int MIN_WORD_LENGTH = 3;
    private final List<String> toDoList = new ArrayList<>();
    private final List<String> doneList = new ArrayList<>();

    //Comme je n'ai pas accès à la fonctionnalité pour ajouter dans "toDoList" j'ai 
    //fait en sort qu'INIT(TypeNotif) notifie la view pour qu'elle ajoute "INIT_DATA" 
    //dans sa  toDoList. Donc lorsqu'on instancie le Model (si sa todoList est vide) 
    //il y a une erreur durant le transfère de todoList à doneList puisque l'index 
    //sélectionné dans la todoList de la view est inexistant dans la todoList du 
    //model ce qui déclenche une erreur... À voir si l'on va garder "INIT_DATA"...
    private static final List<String> INIT_DATA = Arrays.asList(
            "Jouer à SuperMario",
            "Traîner sur FaceBook",
            "Revoir Pro2",
            "Twitter",
            "Travailler au projet Anc3",
            "Regarder du foot",
            "Ecouter de la musique"
    );

    public Model() {
        toDoList.addAll(INIT_DATA);
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
        if (text.length() > 2 && !toDoList.contains(text) && !doneList.contains(text)) {
            toDoList.add(text);
            return true;
        }
        return false;
    }

    public void notif(TypeNotif typeNotif) {
        setChanged();
        notifyObservers(typeNotif);
    }

    public boolean addLine(String line) {
        notif(TypeNotif.LINE_ADDED);
        return true;
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
