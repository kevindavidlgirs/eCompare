/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.main;

import File.model.Model;
import File.view.View;
import java.io.IOException;
import File.viewModel.ViewModel;
import javafx.stage.Stage;

public class FacadeECompare {

    private final Model model;
    private final ViewModel viewmodel;
    private final View view;

    public FacadeECompare(Stage primaryStage, String path1, String path2) throws IOException {
        model = new Model(path1, path2);
        viewmodel = new ViewModel(model);
        view = new View(primaryStage, viewmodel);
    }
}
