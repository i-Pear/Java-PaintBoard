package Layers;

import Main.MainFrame;
import controller.ControllerAdapter;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LayerGroup implements Serializable {

    ArrayList<Layer> layers;
    String fileName = "New File";

    public LayerGroup() {
        layers = new ArrayList<>();
    }

    public void appendLayer(Layer layer) {
        layers.add(layer);
    }

    public void draw(GraphicsContext graphics) {
        for (Layer layer : layers) {
            layer.draw(graphics);
        }
    }

    public Layer getLayerByPosition(float x, float y) {
        int size = layers.size();
        // scan the layers decreasingly
        for (int i = size - 1; i >= 0; i--) {
            if (layers.get(i).isInner(x, y)) {
                return layers.get(i);
            }
        }
        return null;
    }

    public static LayerGroup readFile(String filename) throws IOException, ClassNotFoundException {
        // check if filename available
        File file = new File(filename);
        // if file doesn't exist, directly return
        if (!file.exists()) {
            return null;
        }
        ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(filename));
        LayerGroup layerGroup= (LayerGroup) objectInputStream.readObject();
        objectInputStream.close();
        return layerGroup;
    }

    /**
     * try to save file with existing filename
     */
    public void saveFile() throws IOException {
        // check if filename available
        File file = new File(fileName);
        // if file doesn't exist, ask for a new filename
        if (!file.exists()) {
            saveFileAs();
            return;
        }
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
    }

    /**
     * try to save file with a new filename
     */
    public void saveFileAs() throws IOException {
        // ask for fileName
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as...");
        fileChooser.setInitialFileName("New File.jpf");
        FileChooser.ExtensionFilter filter=new FileChooser.ExtensionFilter("JPaint File", "*.jpf");
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setSelectedExtensionFilter(filter);
        File file = fileChooser.showSaveDialog(MainFrame.mainStage);
        if(file==null)return;
        fileName = file.getAbsolutePath();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
    }

    public void clear() {
        layers.clear();
    }

}
