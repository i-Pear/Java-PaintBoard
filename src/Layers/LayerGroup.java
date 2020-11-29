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

    public LayerGroup(){
        layers=new ArrayList<>();
    }

    public void appendLayer(Layer layer){
        layers.add(layer);
    }

    public void draw(GraphicsContext graphics){
        for(Layer layer:layers){
            layer.draw(graphics);
        }
    }

    public void saveFileWithLayers(String fileName) throws IOException {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
    }

}