package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import Main.LayersControl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerAdapter implements Initializable {

    public static TabPane tabPane;
    public TabPane _tabPane;
    public static ControllerAdapter instance;

    public enum Input_status{SELECT,PEN,LINE,RECTANGLE,CIRCLE,ELLIPSE,TEXT}
    public static Input_status input_status=Input_status.SELECT;

    @FXML
    Button buttonSelect,buttonFreePen,buttonLine,buttonRectangle,buttonCircle,buttonEllipse,buttonText;

    @FXML
    ToggleButton buttonFill;

    public ControllerAdapter() throws IOException {
        instance=this;
    }

    void setButtonIcon(Button button,String path){
        ImageView imageView=new ImageView(path);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        button.setGraphic(imageView);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setButtonIcon(buttonSelect,"resources/Big/select.png");
        setButtonIcon(buttonFreePen,"resources/Big/pen.png");
        setButtonIcon(buttonLine,"resources/Big/line.png");
        setButtonIcon(buttonRectangle,"resources/Big/rectangle.png");
        setButtonIcon(buttonCircle,"resources/Big/circle.png");
        setButtonIcon(buttonEllipse,"resources/Big/ellipse.png");
        setButtonIcon(buttonText,"resources/Big/text.png");
        {
            ImageView imageView=new ImageView("resources/Big/fill.png");
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            buttonFill.setGraphic(imageView);
        }
    }

    public static ControllerAdapter getInstance(){
        return instance;
    }

    public void newFile(){
        tabPane=_tabPane;
        LayersControl.getInstance().createNewLayer();
    }

    public void tabChange(){
        LayersControl.getInstance().tabChange();
    }

    public void saveFile() throws IOException {
        LayersControl.getInstance().getLayerGroup().saveFileWithLayers();
    }

    // --- toolbox buttons callbacks ---

    static boolean doFill=false;
    public void toggleFill(){
        doFill=!doFill;
    }

    public void buttonSelectClicked(){
        input_status=Input_status.SELECT;
    }

    public void buttonFreePenClicked(){
        input_status=Input_status.PEN;
    }

    public void buttonLineClicked(){
        input_status=Input_status.LINE;
    }

    public void buttonRectangleClicked(){
        input_status=Input_status.RECTANGLE;
    }

    public void buttonCircleClicked(){
        input_status=Input_status.CIRCLE;
    }

    public void buttonEllipseClicked(){
        input_status=Input_status.ELLIPSE;
    }

    public void buttonTextClicked(){
        input_status=Input_status.TEXT;
    }

}
