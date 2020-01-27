package miniproject.main;

import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import miniproject.mvvm.ViewModel;
import miniproject.model.Model;
import miniproject.view.View;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        List<String> INIT_DATA = Arrays.asList(
                "Jouer à SuperMario",
                "Traîner sur FaceBook",
                "Revoir Pro2",
                "Twitter",
                "Travailler au projet Anc3",
                "Regarder du foot",
                "Ecouter de la musique"
        );
        Model model = new Model(INIT_DATA);
        ViewModel viewModel = new ViewModel(model);
        View view = new View(primaryStage, viewModel);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
