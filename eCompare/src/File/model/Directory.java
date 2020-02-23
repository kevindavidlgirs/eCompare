/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.model;

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

    private void set_all_status_orphan(File f) {
        for (File f1 : f.getList()) {
            if (f1.isDirectory()) {
                set_all_status_orphan(f1);
            } else {
                f1.set_status(Status.ORPHAN);
            }
        }
        f.set_status(Status.ORPHAN);
    }

    private boolean isPartialSame(File f) {
        if (!isOrphan(f)) {
            return true;
        }
        return false;
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
    public List<File> getList() {
        return Collections.unmodifiableList(files);
    }

    @Override
    public void addFile(File f) {
        files.add(f);
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public boolean isSame(File f) {
        if (this.getName().compareTo(f.getName()) == 0
                && this.getList().size() == f.getList().size()
                && this.getList().size() > 0 && f.getList().size() > 0) {
            for (int i = 0; i < this.getList().size(); ++i) {
                if (!(this.getList().get(i).getStatus() == Status.SAME && f.getList().get(i).getStatus() == Status.SAME)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean isNewer(File f) {
        if (this.getList().size() == f.getList().size()
                && this.getName().compareTo(f.getName()) == 0
                && this.getList().size() > 0 && f.getList().size() > 0) {
            for (int i = 0; i < this.getList().size(); ++i) {
                if (this.getList().get(i).getStatus() == Status.NEWER) {
                    for (int y = 0; y < this.getList().size(); ++y) {
                        if (this.getList().get(y).getStatus() != Status.SAME
                                && this.getList().get(y).getStatus() != Status.NEWER) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isOrphan(File f) {
        if (f.getList().size() > 0) {
            for (File f1 : f.getList()) {
                if (f1.getStatus() != Status.ORPHAN) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void compare(File f1, File f2) {
        set_all_status_orphan(f1);
        set_all_status_orphan(f2);
        compare(f2);
    }

    @Override
    public void compare(File f) {
        if (this.isDirectory() && f.isDirectory()) {
            for (File f3 : this.getList()) {
                for (File f4 : f.getList()) {
                    if (f3.getName().compareTo(f4.getName()) == 0) {
                        f3.compare(f4);
                    }
                }
            }
        }
        check_and_setStatus(f);
    }

    @Override
    public void check_and_setStatus(File f) {
        if (this.isDirectory() && f.isDirectory()) {
            if (this.isSame(f)) {
                this.set_status(Status.SAME);
                f.set_status(Status.SAME);
            } else {
                if (this.isNewer(f)) {
                    this.set_status(Status.NEWER);
                    f.set_status(Status.OLDER);
                } else if (f.isNewer(this)) {
                    this.set_status(Status.OLDER);
                    f.set_status(Status.NEWER);
                } else {
                    if (this.isPartialSame(f)) {
                        this.set_status(Status.PARTIAL_SAME);
                        f.set_status(Status.PARTIAL_SAME);
                    }
                }
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
