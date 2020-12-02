package Layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Point2D;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Layer_Curve extends Layer{

    ArrayList<Point2D> points;

    public Layer_Curve(ArrayList<Point2D> point2DArray){
        points=point2DArray;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setFill(color.getColor());
        int len= points.size();
        for(int i=0;i<len-1;i++){
            graphics.strokeLine(points.get(i).getX(),points.get(i).getY(),points.get(i+1).getX(),points.get(i+1).getY());
        }
    }

}
