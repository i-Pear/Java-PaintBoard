package Layers;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class LayerGroup {

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

}
