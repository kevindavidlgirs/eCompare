/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.model;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableLongValue;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 *
 * @author 2207hembilo
 */
public abstract class File  extends TreeItem<File>{

    private final StringProperty name;
    private final ObjectProperty<LocalDateTime>  date;
    private final LongProperty size;
    private final ObjectProperty<Path> path;
    private ObjectProperty<Status> status;
    private final BooleanProperty selected = new SimpleBooleanProperty(true);

    public File(String name, LocalDateTime date, long size, Path path) {
        this.name = new SimpleStringProperty(name);
        this.date =  new SimpleObjectProperty<>(date);
        this.size = new SimpleLongProperty(size);
        this.path = new SimpleObjectProperty<>(path);
        this.status =  new SimpleObjectProperty(null);
        setExpanded(true);
        setValue(this);
    }
    
    public String getName() {return this.name.get();}
    public ObservableStringValue nameProperty() { return this.name;}
    
    public LocalDateTime getDate() { return date.getValue();}
    public ReadOnlyObjectProperty<LocalDateTime> dateTimeProperty() { return date;}
    
    public long getSize() {return this.size.get();}
    public ReadOnlyLongProperty sizeProperty() {return size;}
    public ObservableLongValue getsizeProperty(){return size;}
            
    public Path getPath() { return this.path.getValue();}
    public ReadOnlyObjectProperty pathProperty() {return path;}
    
    public Status getStatus() {return status.getValue();}
    public ReadOnlyObjectProperty statusProperty() {return path;}

    public void set_selected(boolean selected){ this.selected.setValue(selected); }
    public boolean getSelected(){return selected.get();}
    public boolean isSelected(){return selected.get();}
    public BooleanProperty isSelectedProperty(){return selected;}
    
    public void set_status(Status s) { this.status.setValue(s); }

    public void set_size(long size){ this.size.setValue(size); }
    
    public void set_date(LocalDateTime date){
        this.date.setValue(date);
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

    abstract void addFile(File f);
    
    final void _addFile(File f){
        super.getChildren().add(f);
    }
    
    @Override
    public final ObservableList<TreeItem<File>> getChildren() {
        return FXCollections.unmodifiableObservableList(super.getChildren());
    }
    
    public abstract void compare(File f1, File f2);
    
    public abstract void compare(File f);
    
    public abstract List<File> getList();
    
    public abstract void check_and_setStatus(File f);
    
    final void bindSizeTo(ObservableValue<Long> value) {
        size.bind(value);
    }

    final void bindDateTimeTo(ObservableValue<LocalDateTime> value) {
        date.bind(value);
    }

    public abstract boolean removeFile(File file);

    public abstract String getFileContents();

    public abstract void set_file_content(String s);
}
