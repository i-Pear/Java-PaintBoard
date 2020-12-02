package Layers;

import com.sun.javafx.geom.Ellipse2D;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
        graphics.setLineWidth(width);
        graphics.setFill(fillType==FillType.FILL?color.getColor(): Color.TRANSPARENT);
        switch (fillType){
            case NO:
                graphics.strokeRect(leftUpper.getX()+x_shifting,leftUpper.getY()+y_shifting,rightBottom.getX()-leftUpper.getX(),rightBottom.getY()-leftUpper.getY());
            case FILL:
                graphics.strokeRect(leftUpper.getX()+x_shifting,leftUpper.getY()+y_shifting,rightBottom.getX()-leftUpper.getX(),rightBottom.getY()-leftUpper.getY());
                graphics.fillRect(leftUpper.getX()+x_shifting,leftUpper.getY()+y_shifting,rightBottom.getX()-leftUpper.getX(),rightBottom.getY()-leftUpper.getY());
        }
    }

    @Override
    public boolean isInner(float x, float y) {
        Rectangle2D rectangle_outer = new Rectangle2D((float) leftUpper.getX() - width, (float) leftUpper.getY() - width,
                (float) rightBottom.getX()-(float)leftUpper.getX() + 2 * width, (float) rightBottom.getY()-(float)leftUpper.getY() + 2 * width);
        Rectangle2D rectangle_inner = new Rectangle2D((float) leftUpper.getX() + width, (float) leftUpper.getY() + width,
                (float) rightBottom.getX()-(float)leftUpper.getX() - 2 * width, (float) rightBottom.getY()-(float)leftUpper.getY() - 2 * width);
        if (fillType == FillType.FILL) {
            return rectangle_outer.contains(x, y);
        } else {
            return rectangle_outer.contains(x, y) && (!rectangle_inner.contains(x, y));
        }
    }

    @Override
    public void applyShifting() {
        leftUpper=leftUpper=leftUpper.add(x_shifting,y_shifting);
        rightBottom=rightBottom.add(x_shifting,y_shifting);
        x_shifting=y_shifting=0;
    }
}
