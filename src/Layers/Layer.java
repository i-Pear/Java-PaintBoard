package Layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public abstract class Layer implements Serializable {

    public enum LayerType{LINE,OVAL,RECTANGLE,CIRCLE,TEXT,BITMAP,PEN}

    public enum FillType{NO,FILL}

    public LayerType layerType;
    public FillType fillType=FillType.NO;
    public ColorInfo color=new ColorInfo(Color.BLUE);
    public float width=5;
    public float x_shifting=0,y_shifting=0;

    public abstract void draw(GraphicsContext graphics);

    public abstract boolean isInner(float x, float y);

    public abstract void applyShifting();

}
