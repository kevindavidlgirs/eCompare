package miniproject.mvvm;

import miniproject.model.Model;

//modification
public class ViewModel {

    private final Model model;

    public ViewModel(Model model) {
        this.model = model;
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
