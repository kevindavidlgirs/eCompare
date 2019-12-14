/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniproject.main;

import javafx.application.Application;
import javafx.stage.Stage;
import miniproject.ctrl.Ctrl;
import miniproject.model.Model;
import miniproject.view.View;

/**
 *
 * @author lasynsec
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Model model = new Model();
        Ctrl ctrl = new Ctrl(model);
        View view = new View(primaryStage, ctrl);
        model.addObserver(view);
        model.notif(Model.TypeNotif.INIT);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
