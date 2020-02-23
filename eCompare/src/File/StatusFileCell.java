/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

/**
 *
 * @author kevin
 */
public class StatusFileCell extends FileCell{

    @Override
    String texte(File elem) {
        return  (elem.getPath().getParent().getFileName().toString().equals("TestBC")) ? null :" "+elem.getStatus();
    }
    
}
