/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.main;

import File.model.FileBuilder;
import File.model.File;
import File.view.View;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import javafx.stage.Stage;

/**
 *
 * @author herve
 */
public class FacadeECompare {

    private final File file_structure_left;
    private final File file_structure_right;

    public FacadeECompare(String root, String path, String path1, Stage primaryStage) throws IOException, FileNotFoundException  {
        file_structure_left = FileBuilder.make(Paths.get(root, path).toRealPath());
        file_structure_right = FileBuilder.make(Paths.get(root, path1).toRealPath());
        file_structure_left.compare(file_structure_left, file_structure_right);
        View view = new View(primaryStage, file_structure_left, file_structure_right);
    }
}
