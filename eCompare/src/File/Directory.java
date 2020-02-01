/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author 2207hembilo
 */
public class Directory extends File {

    private final List<File> files = new ArrayList<>();

    public Directory(String name, LocalDateTime date, long size, Path path) {
        super(name, date, size, path);

    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public long getSize() {
        int res = 0;
        for (File f : files) {
            res += f.getSize();
        }
        return res;
    }

    @Override
    public void addFile(File f) {
        files.add(f);
    }

    @Override
    public List<File> getList() {
        return Collections.unmodifiableList(files);
    }

    private void set_default_status_orphan(File f, Status s) {
        if (f.getStatus() == null) {
            if (f.isDirectory()) {
                for (File f1 : f.getList()) {
                    set_default_status_orphan(f1, s);
                }
            }
            f.set_status(s);

        }
    }

    private boolean dirIsSame(File f) {
        if (f.isDirectory()) {
            int cpt = 0;
            while (f.getList().size() > cpt && f.getList().get(cpt).getStatus() == getStatus().SAME) {
                ++cpt;
            }

            return (f.getList().size() == cpt);
        }
        return false;
    }

    private void set_default_partial_same(File f) {
        if (f.isDirectory()) {
            if (f.getStatus() == Status.ORPHAN && !dirIsOrphan(f)) {
                f.set_status(Status.PARTIAL_SAME);
            }

            int cpt = 0;
            while (f.getList().size() > cpt) {
                if (f.getList().get(cpt).isDirectory() && f.getList().get(cpt).getStatus() == Status.ORPHAN && !dirIsOrphan(f.getList().get(cpt))) {
                    f.getList().get(cpt).set_status(Status.PARTIAL_SAME);
                }
                ++cpt;
            }
        }
    }

    private boolean dirIsNoOlder(File f) {
        if (f.isDirectory()) {
            int cpt = 0;
            while (f.getList().size() > cpt && f.getList().get(cpt).getStatus() != getStatus().OLDER) {
                ++cpt;
            }
            return (f.getList().size() == cpt);
        }
        return false;
    }

    private boolean dirIsNewer(File f) {
        if (f.isDirectory()) {
            int cpt = 0;
            while (f.getList().size() > cpt && f.getList().get(cpt).getStatus() != getStatus().NEWER) {
                ++cpt;
            }
            return (f.getList().size() > cpt);
        }
        return false;
    }

    private boolean dir_has_orphan_element(File f) {
        if (f.isDirectory()) {
            int cpt = 0;
            while (f.getList().size() > cpt && f.getList().get(cpt).getStatus() != getStatus().ORPHAN) {
                ++cpt;
            }

            return (f.getList().size() > cpt);
        }

        return false;
    }

    private boolean dirIsOrphan(File f) {
        if (f.isDirectory()) {
            int cpt = 0;
            while (f.getList().size() > cpt && f.getList().get(cpt).getStatus() == getStatus().ORPHAN) {
                ++cpt;
            }

            return (f.getList().size() == cpt);
        }

        return false;
    }

    private boolean dirHasOneOrMoreSame(File f) {
        if (f.isDirectory()) {
            int cpt = 0;
            while (f.getList().size() > cpt && f.getList().get(cpt).getStatus() != getStatus().SAME) {
                ++cpt;
            }

            return (f.getList().size() > cpt);
        }
        return false;
    }
    
    @Override
    public void compare(File f) {
        
        if (this.isDirectory() && f.isDirectory() && this.getSize() > 0 && f.getSize() > 0) {
            set_default_status_orphan(this, Status.ORPHAN);
            set_default_status_orphan(f, Status.ORPHAN);
            
            
            //Troisème boucle  prévue pour la récursion
            int cpt = 0;// Le compteur, pour qu'à la première itération les reps correspondants soient mis, par défaut, en PARTIAL_SAME.
            for (File f3 : this.getList()) {              
                for (File f4 : f.getList()) {
                    f3.compare(f4);                   
                    if (f3.getName().compareTo(f4.getName()) == 0) {
                                          
                        if (f3.getSize() == f4.getSize()) {
                            if (dirIsSame(f4) && dirIsSame(f3)) {
                                f4.set_status(Status.SAME);
                                f3.set_status(Status.SAME);
                            }                           
                        }
                        
                        if(dirIsNewer(f3) && dirIsNoOlder(f3) && !dir_has_orphan_element(f3)){
                            f3.set_status(Status.NEWER);
                            f4.set_status(Status.OLDER);
                        }else if(dirIsNewer(f4) && dirIsNoOlder(f4)&& !dir_has_orphan_element(f4)){
                            f4.set_status(Status.NEWER);
                            f3.set_status(Status.OLDER);
                        }
                        
                        if(dirIsOrphan(f3)){
                            f3.set_status(Status.ORPHAN);      
                        }
                        
                        if(dirIsOrphan(f4)){
                            f4.set_status(Status.ORPHAN);
                        }
                        
                        if (cpt == 0) {
                            set_default_partial_same(f3);
                            set_default_partial_same(f4);
                        }                           
                    }
                }
                ++cpt;
            }
        }
    }

    @Override
    protected String displayFormat(int offset) {
        StringBuilder res = new StringBuilder();
        if (offset == 0) {
            res.append(this.getPath().getParent().getFileName())
                    .append("/")
                    .append(this.getPath().getFileName())
                    .append("\n");
        } else {
            res.append(super.displayFormat(offset))
                    .append(this.getName())
                    .append(((this.isDirectory()) ? " D " : " F "))
                    .append(getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                    .append(" " + this.getSize() + " ")
                    .append(getStatus() + " ").append("\n");
        }

        for (File f : files) {
            res.append(f.displayFormat(offset + 1));
        }
        return res.toString();
    }
}
