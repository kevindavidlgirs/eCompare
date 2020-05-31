package File.view;
import java.io.File;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class DirChooser {

    public static String selectDirectory(Stage primaryStage) throws NullPointerException{
        DirectoryChooser dc = new DirectoryChooser();
        File directory = dc.showDialog(primaryStage);
        return directory.getPath();
    }
}
