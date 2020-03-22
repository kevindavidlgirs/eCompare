/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.view;

import File.model.File;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author kevin
 */
public class DateFileCell extends FileCell{

    @Override
    String texte(File elem) {
        return ""+elem.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
}
