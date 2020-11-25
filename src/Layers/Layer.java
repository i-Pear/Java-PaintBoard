package Layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public abstract class Layer implements Serializable {

    enum LayerType{LINE,OVAL,RECTANGLE,TEXT};

    enum FillType{NO,FILL};

    Color color;

    public abstract void draw(GraphicsContext graphics);

}
