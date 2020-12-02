package Layers;

import com.sun.javafx.geom.Ellipse2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

import javax.swing.border.StrokeBorder;
import java.awt.*;

public class Layer_Circle extends Layer {

    Point2D leftUpper, rightBottom;

    Layer_Circle(int x0, int y0, int x1, int y1) {
        layerType = LayerType.CIRCLE;

        leftUpper = new Point2D(x0, y0);
        rightBottom = new Point2D(x1, y1);
    }

    public Layer_Circle(PointGroup pointGroup) {
        layerType = LayerType.CIRCLE;

        leftUpper = pointGroup.p0;
        rightBottom = pointGroup.p1;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setLineWidth(width);
        graphics.setFill(color.getColor());
        switch (fillType) {
            case NO:
                graphics.strokeOval(leftUpper.getX() + x_shifting, leftUpper.getY() + y_shifting, rightBottom.getX() - leftUpper.getX(), rightBottom.getY() - leftUpper.getY());
            case FILL:
                graphics.fillOval(leftUpper.getX() + x_shifting, leftUpper.getY() + y_shifting, rightBottom.getX() - leftUpper.getX(), rightBottom.getY() - leftUpper.getY());
        }
    }

    @Override
    public boolean isInner(float x, float y) {
        Ellipse2D ellipse_outer = new Ellipse2D((float) leftUpper.getX() - width, (float) leftUpper.getY() - width,
                (float) rightBottom.getX()-(float)leftUpper.getX() + 2 * width, (float) rightBottom.getY()-(float)leftUpper.getY() + 2 * width);
        Ellipse2D ellipse_inner = new Ellipse2D((float) leftUpper.getX() + width, (float) leftUpper.getY() + width,
                (float) rightBottom.getX()-(float)leftUpper.getX() - 2 * width, (float) rightBottom.getY()-(float)leftUpper.getY() - 2 * width);
        if (fillType == FillType.FILL) {
            return ellipse_outer.contains(x, y);
        } else {
            return ellipse_outer.contains(x, y) && (!ellipse_inner.contains(x, y));
        }
    }

    @Override
    public void applyShifting() {
        leftUpper=leftUpper.add(x_shifting, y_shifting);
        rightBottom=rightBottom.add(x_shifting, y_shifting);
        x_shifting = y_shifting = 0;
    }

}
