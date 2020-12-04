package Layers;

import controller.ControllerAdapter;
import javafx.collections.FXCollections;
import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
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

        updateHistoryList();
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

        updateHistoryList();
    }

    public void redo(){
        undoHistory.add(current.getClone());
        current=redoHistory.get(0);
        redoHistory.remove(0);

        updateHistoryList();
    }

    public void updateHistoryList(){
        ArrayList<String> arrayList=new ArrayList<>();
        for(LayerGroup layerGroup:undoHistory){
            arrayList.add(layerGroup.modifyDescription);
        }
        arrayList.add(current.modifyDescription);
        arrayList.add("--------");
        for(LayerGroup layerGroup:redoHistory){
            arrayList.add(layerGroup.modifyDescription);
        }
        ControllerAdapter.getInstance().historyList.setItems(FXCollections.observableArrayList(arrayList));
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
