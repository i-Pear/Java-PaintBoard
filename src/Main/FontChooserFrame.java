package Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Font Window Frame Launcher
 */
public class FontChooserFrame {

    public static Stage fontStage;

    static FontChooserFrame instance;

    public static FontChooserFrame getInstance() {
        if(instance==null){
            instance=new FontChooserFrame();
        }
        return instance;
    }

    public void start(Stage primaryStage) throws Exception {
        fontStage=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("FontChooser.fxml"));
        primaryStage.setTitle("Choose Font");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.showAndWait();
    }

}
