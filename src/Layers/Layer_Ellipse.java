package Layers;

import com.sun.javafx.geom.Ellipse2D;
import Layers.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Layer_Ellipse extends Layer{

    Point2D leftUpper,rightBottom;

    Layer_Ellipse(int x0, int y0, int x1, int y1){
        layerType=LayerType.OVAL;

        leftUpper=new Point2D(x0,y0);
        rightBottom=new Point2D(x1,y1);
    }

    public Layer_Ellipse(PointGroup pointGroup){
        layerType=LayerType.RECTANGLE;

        leftUpper=pointGroup.p0;
        rightBottom=pointGroup.p1;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        setGraphicsMode(graphics);
        switch (fillType){
            case NO:
                graphics.strokeOval(leftUpper.getX()+x_shifting,leftUpper.getY()+y_shifting,rightBottom.getX()-leftUpper.getX(),rightBottom.getY()-leftUpper.getY());
            case FILL:
                graphics.strokeOval(leftUpper.getX()+x_shifting,leftUpper.getY()+y_shifting,rightBottom.getX()-leftUpper.getX(),rightBottom.getY()-leftUpper.getY());
                graphics.fillOval(leftUpper.getX()+x_shifting,leftUpper.getY()+y_shifting,rightBottom.getX()-leftUpper.getX(),rightBottom.getY()-leftUpper.getY());
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
        leftUpper=leftUpper.add(x_shifting,y_shifting);
        rightBottom=rightBottom.add(x_shifting,y_shifting);
        x_shifting=y_shifting=0;
    }

}
