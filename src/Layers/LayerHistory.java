package Layers;

import Main.MainFrame;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;

public class LayerHistory {

    ArrayList<LayerGroup> undoHistory=new ArrayList<>();
    ArrayList<LayerGroup> redoHistory=new ArrayList<>();
    LayerGroup current;

    public Boolean saved=false;

    public LayerGroup getCurrent(){
        return current;
    }

    public LayerHistory(LayerGroup layerGroup){
        if(layerGroup==null)layerGroup=new LayerGroup();
        current=layerGroup;
    }

    public void forward(String description){
        undoHistory.add(current.getClone());
        current.modifyDescription=description;
        redoHistory.clear();
    }

    public boolean canUndo(){
        return !undoHistory.isEmpty();
    }

    public boolean canRedo(){
        return !redoHistory.isEmpty();
    }

    public void undo(){
        redoHistory.add(0,current.getClone());
        current=undoHistory.get(undoHistory.size()-1);
        undoHistory.remove(undoHistory.size()-1);
    }

    public void redo(){
        undoHistory.add(current.getClone());
        current=redoHistory.get(0);
        redoHistory.remove(0);
    }

    /**
     * Following are transport-agent functions
     */

    public void appendLayer(Layer layer) {
        current.appendLayer(layer);
    }

    public void draw(GraphicsContext graphics) {
        current.draw(graphics);
    }

    public Layer getLayerByPosition(float x, float y) {
        return current.getLayerByPosition(x,y);
    }

    public static LayerGroup readFile(String filename) throws IOException, ClassNotFoundException {
        return LayerGroup.readFile(filename);
    }

    public void saveFile() throws IOException {
        current.saveFile();
    }

    public void saveFileAs() throws IOException {
        current.saveFileAs();
    }

    public void clear() {
        current.clear();
    }

}
