package Layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public abstract class Layer implements Serializable {

    enum LayerType{LINE,OVAL,RECTANGLE,CIRCLE,TEXT}

    enum FillType{NO,FILL}

    LayerType layerType;
    FillType fillType=FillType.NO;
    Color color=Color.BLUE;
    double width=5;

    public abstract void draw(GraphicsContext graphics);

}
