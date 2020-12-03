package Layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

import java.io.Serializable;

public abstract class Layer implements Serializable {

    public enum LayerType{LINE,OVAL,RECTANGLE,CIRCLE,TEXT,BITMAP, CURVE}

    public enum FillType{NO,FILL}

    public enum LineType {FULL,POINT,DASH}
    static double[] dash_array_no =null;
    static double[] dash_array_point ={0,40};
    static double[] dash_array_dash ={40,40};

    public LayerType layerType;
    public LineType lineType;
    public FillType fillType=FillType.NO;
    public ColorInfo color=new ColorInfo(Color.BLUE);
    public float width=5;
    public float x_shifting=0,y_shifting=0;

    public void setGraphicsMode(GraphicsContext graphics){
        graphics.setLineWidth(width);
        graphics.setFill(fillType==FillType.FILL?color.getColor(): Color.TRANSPARENT);
        graphics.setStroke(color.getColor());
        switch (lineType) {
            case FULL -> {
                graphics.setLineDashes(dash_array_no);
                graphics.setLineCap(StrokeLineCap.SQUARE);
            }
            case DASH -> {
                graphics.setLineDashes(dash_array_dash);
                graphics.setLineCap(StrokeLineCap.SQUARE);
            }
            case POINT -> {
                graphics.setLineDashes(dash_array_point);
                graphics.setLineCap(StrokeLineCap.ROUND);
            }
        }
    }

    public abstract void draw(GraphicsContext graphics);

    public abstract boolean isInner(float x, float y);

    public abstract void applyShifting();

}
