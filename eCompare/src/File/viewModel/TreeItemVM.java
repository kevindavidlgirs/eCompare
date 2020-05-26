package File.viewModel;

import File.model.File;
import File.model.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TreeItem;
import java.io.IOException;

public class TreeItemVM {

    private final Model model;
    private final EditVM editor;
    private final SimpleStringProperty labelPathText = new SimpleStringProperty();
    private final ObjectProperty<TreeItem<File>> root = new SimpleObjectProperty<>();
    private final ObjectProperty<TreeItem<File>> selected_file_property = new SimpleObjectProperty<>();

    public TreeItemVM(Model model, EditVM editor) {
        this.model = model;
        this.editor = editor;
    }

    public void set_root(File f) {
        root.setValue(makeTreeRoot(f));
        labelPathText.setValue(f.getPath().toString());
    }

    public void refresh_root(String name) {
        if (name.equals("left")) {
            root.setValue(makeTreeRoot(model.get_left_struct_folder()));
        } else {
            root.setValue(makeTreeRoot(model.get_right_struct_folder()));
        }
    }

    public void set_treeItem(String path, String name) throws IOException {
        if (name.equals("left")) {
            model.set_left_struct_folder(path);
        } else {
            model.set_right_struct_folder(path);
        }
    }

    private TreeItem<File> makeTreeRoot(File root) {
        TreeItem<File> res = new TreeItem<>(root);
        res.setExpanded(true);
        if (root.isDirectory()) {
            root.getList().forEach(se -> {
                if (se.isSelected()) {
                    res.getChildren().add(makeTreeRoot(se));
                }
            });
        }
        return res;
    }

    public ObjectProperty<TreeItem<File>> root_property() {
        return root;
    }

    public SimpleStringProperty labelPathTextProperty() {
        return labelPathText;
    }

    public ObjectProperty<TreeItem<File>> selected_file_property() {
        return selected_file_property;
    }

    public void openSelectedFile() {
        if (!selected_file_property.getValue().getValue().isDirectory() && !selected_file_property.getValue().getValue().getFileContents().equals(null)) {
            editor.setText(selected_file_property.getValue().getValue().getFileContents());
            editor.set_selected_file_name(selected_file_property.getValue().getValue().getName());
            editor.setVisible(true);
        }
    }
}
