package Main;

import Layers.LayerGroup;
import controller.CanvasController;
import controller.ControllerAdapter;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class LayersControl {

    private ArrayList<Canvas> canvas = new ArrayList<>();
    private ArrayList<Tab> tabs = new ArrayList<>();
    private int activeID;
    private ArrayList<LayerGroup> layerGroups = new ArrayList<>();

    private static LayersControl instance = new LayersControl();

    private LayersControl() {
        activeID = 0;
    }

    public static LayersControl getInstance() {
        return instance;
    }

    public Canvas getActiveGraphics() {
        return canvas.get(activeID);
    }

    public void repaint() {
        getInstance().getActiveGraphics().getGraphicsContext2D().setFill(Color.WHITE);
        getInstance().getActiveGraphics().getGraphicsContext2D().fillRect(0, 0, LayersControl.getInstance().getActiveGraphics().getWidth(), LayersControl.getInstance().getActiveGraphics().getHeight());
        getInstance().getLayerGroup().draw(getInstance().getActiveGraphics().getGraphicsContext2D());
    }

    public LayerGroup getLayerGroup() {
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
        tabs.add(tab);
        // append new tab
        ControllerAdapter.tabPane.getTabs().add(tab);

        if(layerGroup==null){
            layerGroup=new LayerGroup();
        }
        layerGroups.add(layerGroup);
    }

    public WritableImage getSnapshot() {
        WritableImage image = new WritableImage((int) getInstance().canvas.get(activeID).getWidth(), (int) getInstance().canvas.get(activeID).getHeight());
        canvas.get(activeID).snapshot(null, image);
        return image;
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
