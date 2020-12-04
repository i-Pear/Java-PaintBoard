package Layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

import java.io.Serializable;

/**
 * Base class for layers
 */
public abstract class Layer implements Serializable {

    /**
     * static declarations
     */
    public enum LayerType{LINE, ELLIPSE,RECTANGLE,CIRCLE,TEXT,BITMAP, CURVE}
    public enum FillType{NO,FILL}
    public enum LineType {FULL,POINT,DASH}
    static double[] dash_array_no =null;
    static double[] dash_array_point ={0,40};
    static double[] dash_array_dash ={40,40};

    /**
     * Common layer properties
     */
    public LayerType layerType;
    public LineType lineType;
    public FillType fillType=FillType.NO;
    public ColorInfo color;
    public float width=5;
    public float x_shifting=0,y_shifting=0;

    /**
     * Set common properties to graphics for later drawing
     * @param graphics
     */
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

    /**
     * a virtual function to draw contents to board
     * @param graphics
     */
    public abstract void draw(GraphicsContext graphics);

    /**
     * a virtual function for checking whether user's mouse is in the layer
     * @param x user's mouse position
     * @param y user's mouse position
     * @return if mouse is in the layer
     */
    public abstract boolean isInner(float x, float y);

    /**
     * Apply x_shifting and y_shifting to formal position,
     * then clear them
     */
    public abstract void applyShifting();

    /**
     * Set common properties to cloned layers
     * @param layer
     */
    public void setClone(Layer layer){
        layer.layerType=layerType;
        layer.lineType=lineType;
        layer.fillType=fillType;
        layer.color=new ColorInfo(color);
        layer.width=width;
        layer.x_shifting=x_shifting;
        layer.y_shifting=y_shifting;
    }

    /**
     * A virtual function for returning a new instance
     * @return new instance
     */
    public abstract Layer getClone();
}
