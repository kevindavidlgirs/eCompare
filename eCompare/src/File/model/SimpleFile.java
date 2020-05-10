/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.model;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;

/**
 *
 * @author 2207hembilo
 */
public class SimpleFile extends File {

    private final SimpleStringProperty FileContents = new SimpleStringProperty();

    //Utiliser si ce n'est pas un fichier texte
    public SimpleFile(String name, LocalDateTime date, long size, Path path){
        super(name, date, size, path);
        this.FileContents.setValue(null);
    }

    //Utiliser si c'est un fichier texte
    public SimpleFile(String name, LocalDateTime date, long size, Path path, String FileContents) {
        this(name, date, size, path);
        this.FileContents.setValue(FileContents);
    }

    //Utiliser pour la copy en profondeur
    public SimpleFile(File f, Path path, String FileContents){
        this(f.getName(), f.getDate(), f.getSize(), path);
        this.FileContents.setValue(FileContents);
    }

    public String getFileContents(){return FileContents.getValue();}
    public ObservableStringValue FileContentsProperty() { return FileContents;}
    
    public void set_file_content(String content){
        FileContents.setValue(content);
    }
    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public boolean isSame(File f) {
        if (this.getDate().isEqual(f.getDate())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isNewer(File f) {
        if (this.getDate().isAfter(f.getDate())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void compare(File f1, File f2) {
        compare(f2);
    }

    @Override
    public void compare(File f) {
        if (!this.isDirectory() && !f.isDirectory()) {
            check_and_setStatus(f);
        }
    }

    @Override
    public void check_and_setStatus(File f) {
        if (this.isSame(f)) {
            this.set_status(Status.SAME);
            f.set_status(Status.SAME);
        } else if (this.isNewer(f)) {
            this.set_status(Status.NEWER);
            f.set_status(Status.OLDER);
        } else if (f.isNewer(this)) {
            f.set_status(Status.NEWER);
            this.set_status(Status.OLDER);
        }
    }

    @Override
    protected String displayFormat(int offset) {
        return super.displayFormat(offset) + getName()
                + ((this.isDirectory()) ? " D " : " F ")
                + getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " "
                + getSize() + " "
                + ((getSelected()) ? "TRUE" : "FALSE") + " "
                + getFileContents() + " "
                + getStatus() + "\n";
    }

    @Override
    public boolean isOrphan(File f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public boolean removeFile(File file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
