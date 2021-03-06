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
import javafx.beans.Observable;
import javafx.beans.binding.ObjectBinding;

public class Directory extends File {

    private final List<File> files = new ArrayList<>();
    private final SizeBinding sizeBinding = new SizeBinding();
    private final DateTimeBinding dateTimeBinding = new DateTimeBinding();

    public Directory(String name, LocalDateTime date, long size, Path path) {
        super(name, date, size, path, "D");
        addToSizeBinding(getChildren()); 
        addToDateTimeBinding(getChildren());
        bindSizeTo(sizeBinding);
        bindDateTimeTo(dateTimeBinding);
    }

    public Directory(File f, Path path){
        this(f.getName(), f.getDate(), f.getSize(), path);
        addToSizeBinding(getChildren());
        addToDateTimeBinding(getChildren());
        bindSizeTo(sizeBinding);
        bindDateTimeTo(dateTimeBinding);
        for(File f1 : f.getList()){
            this.addFile(f1.isDirectory() ? new Directory(f1, f1.getPath()) : new SimpleFile(f1, f1.getPath()));
        }
    }

    private void set_all_status_orphan(File f) {
        f.getList().forEach((f1) -> {
            if (f1.isDirectory()) {
                set_all_status_orphan(f1);
            } else {
                f1.set_status(Status.ORPHAN);
            }
        });
        f.set_status(Status.ORPHAN);
    }

    private boolean isPartialSame(File f) {
        return !isOrphan(f);
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
    public boolean removeFile(File f){
        files.remove(f);
        return true;
    }

    @Override
    public void addFile(File f) {
        addToSizeBinding(f.sizeProperty());
        addToDateTimeBinding(f.dateTimeProperty());
        files.add(f);
        _addFile(f);
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
            if (!f.getList().stream().noneMatch((f1) -> (f1.getStatus() != Status.ORPHAN))) {
                return false;
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
            for (File f1 : this.getList()) {
                for (File f2 : f.getList()) {
                    if (f1.getName().compareTo(f2.getName()) == 0) {
                        f1.compare(f2);
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
    
    private void addToSizeBinding(Observable obs) {
        sizeBinding.addBinding(obs);
        sizeBinding.invalidate();
    }

    private void addToDateTimeBinding(Observable obs) {
        dateTimeBinding.addBinding(obs);
        dateTimeBinding.invalidate();
    }
    
    private class SizeBinding extends ObjectBinding<Long> {
        
        @Override 
        protected Long computeValue() {
            return getChildren().stream().map(f -> f.getValue().getSize()).reduce(0L, (s1, s2) -> s1 + s2);
        }
        void addBinding(Observable obs) {
            super.bind(obs);
        }
    }
    
    private class DateTimeBinding extends ObjectBinding<LocalDateTime> {

        @Override 
        protected LocalDateTime computeValue() {
            return getChildren().isEmpty() ? LocalDateTime.now() : getChildren().stream().map(f -> f.getValue().getDate()).max(LocalDateTime::compareTo).get();
        }
        void addBinding(Observable obs) {
            super.bind(obs);
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
                    .append(((this.getSelected()) ? " TRUE " : " FALSE ") + " ")
                    .append(getStatus() + " ").append("\n");
        }

        files.forEach((f) -> {
            res.append(f.displayFormat(offset + 1));
        });
        return res.toString();
    }

    @Override
    public String getFileContents() {
        return null;
    }

    @Override
    public void set_file_content(String s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

}
