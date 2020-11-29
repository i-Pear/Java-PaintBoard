package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import Main.LayersControl;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerAdapter {

    public static TabPane tabPane;
    @FXML
    public TabPane _tabPane;

    @FXML
    public void newFile(){
        tabPane=_tabPane;
        LayersControl.getInstance().createNewLayer();
    }

    @FXML
    public void tabChange(){
        LayersControl.getInstance().tabChange();
    }

    @FXML
    public void saveFile() throws IOException {
        LayersControl.getInstance().getLayerGroup().saveFileWithLayers("123");
    }

}
