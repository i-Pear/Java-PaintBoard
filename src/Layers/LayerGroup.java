package Layers;

import javafx.scene.canvas.GraphicsContext;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class LayerGroup implements Serializable {

    ArrayList<Layer> layers;
    String fileName;

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

    public Layer getLayerByPosition(float x,float y){
        int size= layers.size();
        // scan the layers decreasingly
        for(int i=size-1;i>=0;i--){
            if(layers.get(i).isInner(x,y)){
                return layers.get(i);
            }
        }
        return null;
    }

    public void saveFileWithLayers() throws IOException {
        if(fileName==null){
            // ask for fileName

        }
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
    }

}
