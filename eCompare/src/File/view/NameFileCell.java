/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.view;

import File.model.File;

/**
 *
 * @author kevin
 */
public class NameFileCell extends FileCell {

    @Override
    String texte(File elem) {
        return elem.getName();
    }
    
}
