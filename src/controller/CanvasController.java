package controller;

import Layers.*;
import Main.LayersControl;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;

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
            case LINE:
                return LayerFactory.createShapeLayer(ControllerAdapter.input_status,new PointGroup(x_start,y_start,e.getX(),e.getY()));
            case PEN:
            case TEXT:
            case SELECT:
                // will never call here
                return null;
            default:
                return LayerFactory.createShapeLayer(ControllerAdapter.input_status,getPointGroup(e));
        }
    }

    ArrayList<Layer_Line> current_free_path;

    public void mouseDown(MouseEvent e){
        // System.out.println("down");
        if(ControllerAdapter.input_status== ControllerAdapter.Input_status.SELECT){
            selectedLayer=LayersControl.getInstance().getLayerGroup().getLayerByPosition((float)e.getX(),(float)e.getY());
            System.out.println("selected");
        }else{
            selectedLayer=null;
        }
        if(ControllerAdapter.input_status== ControllerAdapter.Input_status.PEN){
            current_free_path=new ArrayList<>();
        }
        x_start =e.getX();
        y_start =e.getY();
        isPressed=true;
    }

    public void mouseRelease(MouseEvent e){
        // System.out.println("release");
        isPressed=false;

        // if it is creating new shape
        {
            Layer layer= getCurrentDrawingLayer(e);
            if(layer!=null)layer.fillType=ControllerAdapter.doFill? Layer.FillType.FILL: Layer.FillType.NO;
            if(layer!=null)LayersControl.getInstance().getLayerGroup().appendLayer(layer);
        }

        // if it is moving shape
        if(selectedLayer!=null)selectedLayer.applyShifting();

        // if it is drawing free line
        if(ControllerAdapter.input_status== ControllerAdapter.Input_status.PEN){
            Layer layer=LayerFactory.createCurveLayer(current_free_path);
            LayersControl.getInstance().getLayerGroup().appendLayer(layer);
            current_free_path=null;
        }

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

        // specially deal with free line
        if(ControllerAdapter.input_status== ControllerAdapter.Input_status.PEN){
            Layer_Line line=new Layer_Line(
                    new PointGroup(x_start,y_start,e.getX(),e.getY())
            );
            x_start=e.getX();
            y_start=e.getY();
            line.draw(graphics);
            current_free_path.add(line);
            return;
        }

        // draw
        LayersControl.getInstance().repaint();

        Layer temp=getCurrentDrawingLayer(e);
        if(temp!=null)temp.fillType=ControllerAdapter.doFill? Layer.FillType.FILL: Layer.FillType.NO;
        if(temp!=null)temp.draw(graphics);
    }

}
