package controller;

import Layers.Layer;
import Layers.Layer_Line;
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
    private double x0,y0;

    private PointGroup getPointGroup(MouseEvent e){
        return new PointGroup(
                Math.min(x0,e.getX()),
                Math.min(y0,e.getY()),
                Math.max(x0,e.getX()),
                Math.max(y0,e.getY())
        );
    }

    public void mouseDown(MouseEvent e){
        System.out.println("down");
        x0=e.getX();
        y0=e.getY();
        isPressed=true;
    }

    public void mouseRelease(MouseEvent e){
        System.out.println("release");
        isPressed=false;
        LayersControl.getInstance().getLayerGroup().appendLayer(new Layer_Rectangle(getPointGroup(e)));
        LayersControl.getInstance().getLayerGroup().draw(LayersControl.getInstance().getActiveGraphics().getGraphicsContext2D());
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
        LayersControl.getInstance().getLayerGroup().draw(LayersControl.getInstance().getActiveGraphics().getGraphicsContext2D());
        Layer temp=new Layer_Rectangle(getPointGroup(e));
        temp.draw(graphics);
    }

}
