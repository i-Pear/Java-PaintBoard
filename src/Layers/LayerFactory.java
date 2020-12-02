package Layers;

import controller.ControllerAdapter;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class LayerFactory {

    public static Layer createShapeLayer(ControllerAdapter.Input_status status,PointGroup points){
        switch (status){
            case LINE:
                return new Layer_Line(points);
            case CIRCLE:
                return new Layer_Circle(points);
            case RECTANGLE:
                return new Layer_Rectangle(points);
            case ELLIPSE:
                return new Layer_Ellipse(points);
            default:
                return null;
        }
    }

    public static Layer createCurveLayer(ArrayList<Layer_Line> point2DS){
        return new Layer_Curve(point2DS);
    }

    public static Layer createBitmapLayer(Image image){
        return new Layer_Bitmap(image);
    }

}
