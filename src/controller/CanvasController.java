package controller;

import Layers.Layer;
import Layers.LayerFactory;
import Layers.Layer_Rectangle;
import Layers.PointGroup;
import Main.LayersControl;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class CanvasController {

    private static CanvasController instance=new CanvasController();

    public static CanvasController getInstance() {
        return instance;
    }

    private boolean isPressed=false;
    private double x_start, y_start;

    private PointGroup getPointGroup(MouseEvent e){
        return new PointGroup(
                Math.min(x_start,e.getX()),
                Math.min(y_start,e.getY()),
                Math.max(x_start,e.getX()),
                Math.max(y_start,e.getY())
        );
    }

    public void mouseDown(MouseEvent e){
        System.out.println("down");
        x_start =e.getX();
        y_start =e.getY();
        isPressed=true;
    }

    public void mouseRelease(MouseEvent e){
        System.out.println("release");
        isPressed=false;
        Layer layer= LayerFactory.createShapeLayer(ControllerAdapter.input_status,getPointGroup(e));
        LayersControl.getInstance().getLayerGroup().appendLayer(layer);
        LayersControl.getInstance().repaint();
    }

    public void mouseMove(MouseEvent e){
        System.out.println("move");
        if(!isPressed)return;

        // get graphics content
        GraphicsContext graphics=LayersControl.getInstance().getActiveGraphics().getGraphicsContext2D();

        // clear
        graphics.setFill(Color.WHITE);
        graphics.fillRect(0,0,LayersControl.getInstance().getActiveGraphics().getWidth(),LayersControl.getInstance().getActiveGraphics().getHeight());

        // draw
        LayersControl.getInstance().repaint();

        Layer temp=new Layer_Rectangle(getPointGroup(e));
        temp.draw(graphics);
    }

}
