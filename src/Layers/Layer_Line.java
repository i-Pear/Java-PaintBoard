package Layers;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Layer_Line extends Layer {

    Point2D start, end;

    Layer_Line(int x0,int y0,int x1,int y1){
        layerType=LayerType.LINE;

        start=new Point2D(x0,y0);
        end=new Point2D(x1,y1);
    }

    public Layer_Line(PointGroup pointGroup){
        layerType=LayerType.LINE;

        start=pointGroup.p0;
        end=pointGroup.p1;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setFill(color.getColor());
        graphics.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
    }

}
