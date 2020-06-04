package File.view;
import java.io.File;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class DirChooser {

    public static String selectDirectory(Stage primaryStage, File file) throws NullPointerException{
        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(file);
        File directory = dc.showDialog(primaryStage);
        return directory.getPath();
    }
}
