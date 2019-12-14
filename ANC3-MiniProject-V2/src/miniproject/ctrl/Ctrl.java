/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniproject.ctrl;

import miniproject.model.Model;

/**
 *
 * @author lasynsec
 */
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

//    public void transfer(String string, int idxSel) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
