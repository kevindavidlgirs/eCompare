/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.model;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author 2207hembilo
 */
public abstract class File {

    private final String name;
    private final LocalDateTime date;
    private final long size;
    private final Path path;
    private Status status;
    private Boolean selected;

    public File(String name, LocalDateTime date, long size, Path path) {
        this.name = name;
        this.date = date;
        this.size = size;
        this.path = path;
    }
    
    public boolean getSelected(){
        return selected;
    }
    
    public String getName() {
        return this.name;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public long getSize() {
        return this.size;
    }

    public Path getPath() {
        return this.path;
    }

    public void set_status(Status s) {
        this.status = s;
    }
    
    public void set_selected(boolean selected){
        this.selected = selected;
    }
    
    protected String displayFormat(int offset) {
        String res = "";
        for (int i = 0; i < offset; ++i) {
            res += "\t";
        }
        return res;
    }

    @Override
    public String toString() {
        return displayFormat(0);
    }

    public abstract boolean isDirectory();
    
    public abstract boolean isSame(File f1);
        
    public abstract boolean isOrphan(File f);
    
    public abstract boolean isNewer(File f);

    public abstract void addFile(File f);

    public abstract void compare(File f1, File f2);
    
    public abstract void compare(File f);
    
    public abstract List<File> getList();
    
    public abstract void check_and_setStatus(File f);

    

}
