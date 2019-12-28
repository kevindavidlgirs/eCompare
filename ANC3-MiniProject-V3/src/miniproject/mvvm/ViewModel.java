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

    private final BooleanProperty itemTransferDoneList = new SimpleBooleanProperty(false);
    private final BooleanProperty itemTransferToDoList = new SimpleBooleanProperty(false);
    private final BooleanProperty itemAddToDoList = new SimpleBooleanProperty(false);
    private final IntegerProperty numLineSelectedToDoList = new SimpleIntegerProperty(-1);
    private final IntegerProperty numLineSelectedDoneList = new SimpleIntegerProperty(-1);
    private final StringProperty addtext = new SimpleStringProperty();
    private final Model model;

    public ViewModel(Model model) {
        this.model = model;
        itemTransferDoneList.bind(numLineSelectedToDoList.isEqualTo(-1));
        itemTransferToDoList.bind(numLineSelectedDoneList.isEqualTo(-1));
        itemAddToDoList.bind(addtext.length().lessThan(3));
    }

    public SimpleListProperty<String> toDoListProperty() {
        return new SimpleListProperty<>(model.getToDoList());
    }

    public SimpleListProperty<String> doneListProperty() {
        return new SimpleListProperty<>(model.getDoneList());
    }

    public ReadOnlyIntegerProperty sizeToDoListProperty() {
        return toDoListProperty().sizeProperty();
    }

    public ReadOnlyIntegerProperty sizeDoneListProperty() {
        return doneListProperty().sizeProperty();
    }

    public BooleanProperty itemTransferableDoneListProperty() {
        return this.itemTransferDoneList;
    }

    public BooleanProperty itemTransferableToDoListProperty() {
        return this.itemTransferToDoList;
    }

    public BooleanProperty itemAddableToDoList() {
        return this.itemAddToDoList;
    }

    public StringProperty addTextProperty() {
        return this.addtext;
    }

    public IntegerProperty numLineSelectedToDoListProperty() {
        return this.numLineSelectedToDoList;
    }

    public IntegerProperty numLineSelectedDoneListProperty() {
        return this.numLineSelectedDoneList;
    } 
    
    //Suppression du paramètre index. La valeur de l'index provient de numLineSelectedToDoList.get().
    public void transfer(String toDoOrDone) {
        int index = -1;
        System.out.println(index);
            if (">>".equals(toDoOrDone)) {
                index = numLineSelectedToDoList.get();
                model.setDone(index);
            } else {
               index = numLineSelectedDoneList.get();
                model.setToDo(index);
            }
    }

    //Modification
    public void addToDo(String txt) {
        if (model.addToDo(txt)) {
            addtext.set("");
        }
    }
}
