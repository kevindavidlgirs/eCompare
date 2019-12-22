package miniproject.mvvm;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import miniproject.model.Model;


public class ViewModel {
    private final StringProperty addtext = new SimpleStringProperty();
    private final IntegerProperty numLineSelected = new SimpleIntegerProperty(-1);
    private final Model model;

    public ViewModel(Model model) {
        this.model = model;
    }
    
    public SimpleListProperty<String> toDoListProperty(){
        return new SimpleListProperty<>(model.getToDoList());
    }
    
    public SimpleListProperty<String> doneListProperty(){
        return new SimpleListProperty<>(model.getDoneList());
    }

    public ReadOnlyIntegerProperty sizeToDoListProperty(){
        return toDoListProperty().sizeProperty();
    }
    
    public ReadOnlyIntegerProperty sizeDoneListProperty(){
        return doneListProperty().sizeProperty();
    }
    
    public StringProperty addTextProperty(){
        return this.addtext;
    }
    
    public IntegerProperty numLineSelected(){
        return this.numLineSelected;
    }
    
    public void transfer(String toDoOrDone, int index) {
        if (index != -1) {
            if (">>".equals(toDoOrDone)) {
                model.setDone(index);
            } else {
                model.setToDo(index);
            }
        }
    }

    public void addToDo(String txt) {
        model.addToDo(txt);
    }
}
