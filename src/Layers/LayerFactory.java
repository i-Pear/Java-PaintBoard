package Layers;

import Main.LayersControl;
import controller.CanvasController;
import controller.ControllerAdapter;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class LayerFactory {

    public static Layer createShapeLayer(ControllerAdapter.Input_status status, PointGroup points) {
        Layer layer;
        switch (status) {
            case LINE:
                layer = new Layer_Line(points);
                break;
            case CIRCLE:
                layer = new Layer_Circle(points);
                break;
            case RECTANGLE:
                layer = new Layer_Rectangle(points);
                break;
            case ELLIPSE:
                layer = new Layer_Ellipse(points);
                break;
            default:
                return null;
        }
        layer.color = new ColorInfo(CanvasController.color);
        layer.lineType = ControllerAdapter.getInstance().getLineType();
        layer.width = ControllerAdapter.getInstance().getLineWidth();
        return layer;
    }

    public static Layer createCurveLayer(ArrayList<Layer_Line> point2DS) {
        return new Layer_Curve(
                point2DS,
                new ColorInfo(CanvasController.color),
                ControllerAdapter.getInstance().getLineType(),
                ControllerAdapter.getInstance().getLineWidth()
        );
    }

    public static Layer createBitmapLayer(Image image) {
        return new Layer_Bitmap(image);
    }

}
