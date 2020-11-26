package Layers;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Layer_Rectangle extends Layer{

    Point2D leftUpper,rightBottom;

    public Layer_Rectangle(double x0,double y0,double x1,double y1){
        layerType=LayerType.RECTANGLE;

        leftUpper=new Point2D(x0,y0);
        rightBottom=new Point2D(x1,y1);
    }

    public Layer_Rectangle(PointGroup pointGroup){
        layerType=LayerType.RECTANGLE;

        leftUpper=pointGroup.p0;
        rightBottom=pointGroup.p1;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setFill(color);
        switch (fillType){
            case NO:
                graphics.strokeRect(leftUpper.getX(),leftUpper.getY(),rightBottom.getX()-leftUpper.getX(),rightBottom.getY()-leftUpper.getY());
            case FILL:
                graphics.fillRect(leftUpper.getX(),leftUpper.getY(),rightBottom.getX()-leftUpper.getX(),rightBottom.getY()-leftUpper.getY());
        }
    }
}
