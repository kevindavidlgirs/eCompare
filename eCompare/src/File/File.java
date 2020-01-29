/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author 2207hembilo
 */
public abstract class File {

    private final String name;
    private LocalDateTime date;
    private final long size;
    private final String path;
    private Status status;

    public File(String name, LocalDateTime date, long size, String path) {
        this.name = name;
        this.date = date;
        this.size = size;
        this.path = path;
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

    public String getPath() {
        return this.path;
    }

    public boolean isSame(File f1) {
        if (this.getDate().isEqual(f1.getDate())) {
            return true;
        }
        return false;
    }

    public boolean isOlder(File f1) {
        if (this.getDate().isBefore(f1.getDate())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNewer(File f1) {
        if (this.getDate().isAfter(f1.getDate())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOrphan(List<File> ls) {
        for (File f : ls) {
            if (f.getName().compareTo(this.getName()) == 0
                    && f.isDirectory() && this.isDirectory()
                    || !f.isDirectory() && !this.isDirectory()) {
                return false;
            }
        }
        return true;
    }
    
    public boolean dirIsSame(File f){
        if(f.isDirectory()){
            int cpt = 0;
            while(f.getList().size() > cpt && f.getList().get(cpt).getStatus() == status.SAME){
                ++cpt;
            }
            
           return (f.getList().size() == cpt);
        }
        return false;
    }
    
    public boolean dirIsNewer(File f){
         if(f.isDirectory()){
            int cpt = 0;
            while(f.getList().size() > cpt && f.getList().get(cpt).getStatus() != status.NEWER){
                ++cpt;
            }
            
           return (f.getList().size() > cpt);
        }
        return false;
    }
    
     public boolean dirHasOneOrMoreSame(File f){
         if(f.isDirectory()){
            int cpt = 0;
            while(f.getList().size() > cpt && f.getList().get(cpt).getStatus() != status.SAME){
                ++cpt;
            }
            
           return (f.getList().size() > cpt);
        }
        return false;
     }
     
    public void set_status(Status s) {
        this.status = s;
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

    public abstract void addFile(File f);

    public abstract void compare(File f);

    public abstract List<File> getList();

}
