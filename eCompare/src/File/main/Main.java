/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.main;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Kevin David L. Girs and Hervé Mbilo.
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new FacadeECompare(primaryStage, "eCompare\\TestBC\\RootBC_Left", "eCompare\\TestBC\\RootBC_Right");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
