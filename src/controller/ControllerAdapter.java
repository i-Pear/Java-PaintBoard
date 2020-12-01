package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
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

    @FXML
    Button buttonSelect,buttonFreePen,buttonLine,buttonRectangle,buttonCircle,buttonEllipse,buttonText;

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

    // --- toolbox buttons ---

    public void buttonSelectClicked(){

    }

    public void buttonFreePenClicked(){

    }

    public void buttonLineClicked(){

    }

    public void buttonRectangleClicked(){

    }

    public void buttonCircleClicked(){

    }

    public void buttonEllipseClicked(){

    }

    public void buttonTextClicked(){

    }

}
