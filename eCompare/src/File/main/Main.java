/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import File.model.Model;
import File.view.View;
import File.viewModel.ViewModel;

/**
 *
 * @author kevin
 */

public class Main extends Application {

    //Je me suis dit que la facade pouvait être à cet endroit à voir dans l'itération 4..
    @Override
    public void start(Stage primaryStage) throws Exception {
        FacadeECompare fe = new FacadeECompare(primaryStage, "eCompare/TestBC/RootBC_Left", "eCompare/TestBC/RootBC_Right");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
