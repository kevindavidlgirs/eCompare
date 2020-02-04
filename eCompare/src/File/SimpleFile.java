/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author 2207hembilo
 */
public class SimpleFile extends File {

    public SimpleFile(String name, LocalDateTime date, long size, Path path) {
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
                + getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " "
                + getSize() + " "
                + getStatus() + "\n";
    }

    private boolean isSame(File f1) {
        if (this.getDate().isEqual(f1.getDate())) {
            return true;
        }
        return false;
    }

    private boolean isOlder(File f1) {
        if (this.getDate().isBefore(f1.getDate())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isNewer(File f1) {
        if (this.getDate().isAfter(f1.getDate())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isOrphan(List<File> ls) {
        for (File f : ls) {
            if (f.getName().compareTo(this.getName()) == 0
                    && f.isDirectory() && this.isDirectory()
                    || !f.isDirectory() && !this.isDirectory()) {
                return false;
            }
        }
        return true;
    }

    

    @Override
    public void compare(File f) {
        if (!f.isDirectory()) {
            if (this.getName().compareTo(f.getName()) == 0) {
                if (this.isSame(f)) {
                    this.set_status(Status.SAME);
                    f.set_status(Status.SAME);
                } else if (this.isNewer(f)) {
                    this.set_status(Status.NEWER);
                    f.set_status(Status.OLDER);
                } else if (this.isOlder(f)) {
                    this.set_status(Status.OLDER);
                    f.set_status(Status.NEWER);
                }
            }
        }
    }
    
    @Override
    public void addFile(File f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<File> getList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
