package Layers;

import Controller.CanvasController;
import Controller.ControllerAdapter;
import Controller.FontChooserController;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

import javax.media.jai.remote.SerializableRenderedImage;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * A implement of Simple Factory Pattern
 */
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

    public static Layer createTextLayer(int x,int y,String text){
        return new Layer_Text(text, FontChooserController.font,x,y);
    }

    public static Layer createBitmapLayer(int x, int y, WritableImage image) {
        BufferedImage bufferedImage= SwingFXUtils.fromFXImage(image, null);
        return new Layer_Bitmap(
                new SerializableRenderedImage(bufferedImage,true)
                ,x,y
        );
    }

}
