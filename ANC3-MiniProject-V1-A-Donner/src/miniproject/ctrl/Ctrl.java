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
    
    public Ctrl(Model model){
        this.model = model;
    }
    
    public void addToDo(String txt){
        model.addToDo(txt);
    }
}
