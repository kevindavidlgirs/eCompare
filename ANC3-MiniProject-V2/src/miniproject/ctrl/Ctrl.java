package miniproject.ctrl;
import miniproject.model.Model;

public class Ctrl {

    private final Model model;

    public Ctrl(Model model) {
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
    
    public void addToDo(String txt){
        model.addToDo(txt);
    }
}
