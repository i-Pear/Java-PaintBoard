package Main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class MainFrame extends Application {

    public static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("Paint Board");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(!LayersControl.getInstance().isAllSaved()){
                    // ask to save file
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Files not saved");
                    alert.setHeaderText("There exists files in opening tabs which have not been saved");
                    alert.setContentText("Exit without saving?");
                    ButtonType buttonYes = new ButtonType("Exit");
                    ButtonType buttonCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(buttonYes, buttonCancel);
                    Optional result = alert.showAndWait();

                    if(result.get()==buttonCancel){
                        event.consume();
                    }
                }
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
