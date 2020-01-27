/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author 2207hembilo
 */
public class SimpleFile extends File {

    public SimpleFile(String name, LocalDateTime date, long size, String path) {
        super(name, date, size, path);
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    protected String displayFormat(int offset) {
        return super.displayFormat(offset) + getName()
                + ((this.isDirectory()) ? " D " : " F ")
                + getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +""                
                + getSize() +" "
                + getStatus()+ "\n";
    }

    @Override
    public void addFile(File f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<File> getList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void compare(File f) {
        System.out.println("test de la méthode \"compare\" dans SimpleFile");
    }

}
