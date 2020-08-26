/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.view;

import File.model.Status;
import File.model.File;
import javafx.scene.control.cell.TextFieldTreeTableCell;

public abstract class FileCell extends TextFieldTreeTableCell<File, File> {

    private static final String CSSPATH = "File/view/cssView.css";

    public FileCell() {
        getStylesheets().add(CSSPATH);
    }

    @Override
    public void updateItem(File elem, boolean isEmpty) {
        super.updateItem(elem, isEmpty);
        if (elem == null || isEmpty) {
            
            return;
        }
        
        this.setText(texte(elem));
        this.getStyleClass().set(0, elem.getStatus() == Status.ORPHAN ? "ORPHAN"
                : elem.getStatus() == Status.SAME ? "SAME"
                : elem.getStatus() == Status.PARTIAL_SAME ? "PARTIAL_SAME"
                : elem.getStatus() == Status.NEWER ? "NEWER" : "OLDER");
    }

    abstract String texte(File elem);

}
