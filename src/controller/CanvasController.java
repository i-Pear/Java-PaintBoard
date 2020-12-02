package controller;

import Layers.Layer;
import Layers.LayerFactory;
import Layers.Layer_Rectangle;
import Layers.PointGroup;
import Main.LayersControl;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CanvasController {

    private static CanvasController instance=new CanvasController();

    public static CanvasController getInstance() {
        return instance;
    }

    private boolean isPressed=false;
    private double x_start, y_start;

    private Layer selectedLayer;

    private PointGroup getPointGroup(MouseEvent e){
        return new PointGroup(
                Math.min(x_start,e.getX()),
                Math.min(y_start,e.getY()),
                Math.max(x_start,e.getX()),
                Math.max(y_start,e.getY())
        );
    }

    private Layer getCurrentDrawingLayer(MouseEvent e){
        switch (ControllerAdapter.input_status){
            case PEN:

            case LINE:
                return LayerFactory.createShapeLayer(ControllerAdapter.input_status,new PointGroup(x_start,y_start,e.getX(),e.getY()));
            case TEXT:
            case SELECT:
                return null;
            default:
                return LayerFactory.createShapeLayer(ControllerAdapter.input_status,getPointGroup(e));
        }
    }

    public void mouseDown(MouseEvent e){
        System.out.println("down");
        if(ControllerAdapter.input_status== ControllerAdapter.Input_status.SELECT){
            selectedLayer=LayersControl.getInstance().getLayerGroup().getLayerByPosition((float)e.getX(),(float)e.getY());
            System.out.println("selected");
        }else{
            selectedLayer=null;
        }
        x_start =e.getX();
        y_start =e.getY();
        isPressed=true;
    }

    public void mouseRelease(MouseEvent e){
        System.out.println("release");
        isPressed=false;

        // if it is creating new shape
        Layer layer= getCurrentDrawingLayer(e);
        if(layer!=null)LayersControl.getInstance().getLayerGroup().appendLayer(layer);

        // if it is moving shape
        if(selectedLayer!=null)selectedLayer.applyShifting();

        LayersControl.getInstance().repaint();
    }

    public void mouseMove(MouseEvent e){
        // System.out.println("move");
        if(!isPressed)return;

        // get graphics content
        GraphicsContext graphics=LayersControl.getInstance().getActiveGraphics().getGraphicsContext2D();

        // deal with "Select" tool
        if(ControllerAdapter.input_status== ControllerAdapter.Input_status.SELECT){
            if(selectedLayer==null)return;
            selectedLayer.x_shifting= (float) (e.getX()-x_start);
            selectedLayer.y_shifting= (float) (e.getY()-y_start);
        }

        // draw
        LayersControl.getInstance().repaint();

        Layer temp=getCurrentDrawingLayer(e);
        if(temp!=null)temp.draw(graphics);
    }

}
