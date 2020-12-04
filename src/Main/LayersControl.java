package Main;

import Layers.LayerGroup;
import Layers.LayerHistory;
import controller.CanvasController;
import controller.ControllerAdapter;
import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class LayersControl {

    private ArrayList<Canvas> canvas = new ArrayList<>();
    public ArrayList<Tab> tabs = new ArrayList<>();
    private int activeID;
    private ArrayList<LayerHistory> layerGroups = new ArrayList<>();

    private static LayersControl instance = new LayersControl();

    private LayersControl() {
        activeID = 0;
    }

    public static LayersControl getInstance() {
        return instance;
    }

    public Canvas getActiveCanvas() {
        return canvas.get(activeID);
    }

    public void repaint() {
        getInstance().getActiveCanvas().getGraphicsContext2D().setFill(Color.WHITE);
        getInstance().getActiveCanvas().getGraphicsContext2D().fillRect(0, 0, LayersControl.getInstance().getActiveCanvas().getWidth(), LayersControl.getInstance().getActiveCanvas().getHeight());
        getInstance().getLayerGroup().draw(getInstance().getActiveCanvas().getGraphicsContext2D());
    }

    public LayerGroup getLayerGroup() {
        return layerGroups.get(activeID).getCurrent();
    }

    public LayerHistory getLayerHistory(){
        return layerGroups.get(activeID);
    }

    public void createNewLayer(){
        createNewLayer(null);
    }

    public void createNewLayer(LayerGroup layerGroup) {
        // create new canvas
        Canvas canvas_new = new Canvas();
        canvas_new.setWidth(2000);
        canvas_new.setHeight(2000);
        canvas_new.setOnMousePressed(event -> CanvasController.getInstance().mouseDown(event));
        canvas_new.setOnMouseReleased(event -> CanvasController.getInstance().mouseRelease(event));
        canvas_new.setOnMouseDragged(event -> CanvasController.getInstance().mouseDrag(event));
        canvas_new.setOnMouseMoved(event -> CanvasController.getInstance().mouseMove(event));
        canvas.add(canvas_new);
        // create scrollPane with canvas
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(canvas_new);
        // create new tab
        Tab tab = new Tab();
        tab.setText("New File");
        tab.setContent(scrollPane);
        tab.setOnSelectionChanged(event -> LayersControl.getInstance().tabChange());
        tab.setOnCloseRequest(this::tabClosing);
        tabs.add(tab);
        // append new tab
        ControllerAdapter.tabPane.getTabs().add(tab);

        if(layerGroup==null){
            layerGroup=new LayerGroup();
        }
        layerGroups.add(new LayerHistory(layerGroup));
    }

    public WritableImage getSnapshot() {
        WritableImage image = new WritableImage((int) getInstance().canvas.get(activeID).getWidth(), (int) getInstance().canvas.get(activeID).getHeight());
        canvas.get(activeID).snapshot(null, image);
        return image;
    }

    void unRegisterTab(){
        canvas.remove(activeID);
        tabs.remove(activeID);
        layerGroups.remove(activeID);
    }

    public void tabClosing(Event event) {
        if(!LayersControl.getInstance().getLayerHistory().saved){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("File not saved");
            alert.setHeaderText("File has not been saved");
            alert.setContentText("Save file?");
            ButtonType buttonYes = new ButtonType("Save");
            ButtonType buttonNo = new ButtonType("No");
            ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonYes, buttonNo, buttonCancel);
            Optional result = alert.showAndWait();

            if (result.get() == buttonYes) {
                try{
                    LayersControl.getInstance().getLayerGroup().saveFile();
                    // check if file really been saved
                    if(!LayersControl.getInstance().getLayerHistory().saved){
                        event.consume();
                        return;
                    }
                }catch (Exception ignore){}
                // close tab
                unRegisterTab();
            } else if(result.get() == buttonCancel){
                event.consume();
            }else{
                // close tab
                unRegisterTab();
            }
        }
    }

    public boolean isAllSaved(){
        for(LayerHistory layerHistory:layerGroups){
            if(!layerHistory.saved)return false;
        }
        return true;
    }

    public void tabChange() {
        for (int i = 0; i < tabs.size(); i++) {
            if (ControllerAdapter.tabPane.getSelectionModel().isSelected(i)) {
                activeID = i;
                // System.out.println("select change to " + i);
                break;
            }
        }
    }

}
