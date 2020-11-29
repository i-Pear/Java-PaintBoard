package Layers;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Layer_Circle extends Layer{

    Point2D leftUpper,rightBottom;

    Layer_Circle(int x0,int y0,int x1,int y1){
        layerType=LayerType.CIRCLE;

        leftUpper=new Point2D(x0,y0);
        rightBottom=new Point2D(x1,y1);
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setFill(color.getColor());
        switch (fillType){
            case NO:
                graphics.strokeOval(leftUpper.getX(),leftUpper.getY(),rightBottom.getX()-leftUpper.getX(),rightBottom.getY()-leftUpper.getY());
            case FILL:
                graphics.fillOval(leftUpper.getX(),leftUpper.getY(),rightBottom.getX()-leftUpper.getX(),rightBottom.getY()-leftUpper.getY());
        }
    }

}
