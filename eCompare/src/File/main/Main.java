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

// J'ai factorisé au maximum le code, afin de le rendre lisible et l'adapter à l'architecture MVVM.
// J'ai aussi avancé sur le model et la viewModel.
// Je te laisse regarder ce que j'ai fait.
// On se capte en fin de semaine, pour faire le point.
// Si tu veux me laisser un morceau pour finir, je suis OK Gars !!

// Enjoy!!

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception, FileNotFoundException {
        Model model = new Model("TestBC", "RootBC_Left", "RootBC_Right");
        ViewModel viewmodel = new ViewModel(model);
        View view = new View(primaryStage, viewmodel);
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
