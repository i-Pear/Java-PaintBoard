package Layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public abstract class Layer implements Serializable {

    enum LayerType{LINE,OVAL,RECTANGLE,CIRCLE,TEXT,BITMAP,PEN}

    enum FillType{NO,FILL}

    LayerType layerType;
    FillType fillType=FillType.NO;
    ColorInfo color=new ColorInfo(Color.BLUE);
    float width=5;
    public float x_shifting=0,y_shifting=0;

    public abstract void draw(GraphicsContext graphics);

    public abstract boolean isInner(float x, float y);

    public abstract void applyShifting();

}
