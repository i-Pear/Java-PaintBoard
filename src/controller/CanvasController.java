package controller;

import Layers.*;
import Main.LayersControl;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class CanvasController {

    private static CanvasController instance = new CanvasController();

    public static CanvasController getInstance() {
        return instance;
    }

    private boolean isPressed = false;
    private double x_start, y_start;

    private Layer selectedLayer;
    private int selectedLayerIndex;

    public static ContextMenu contextMenu;

    public CanvasController() {
        MenuItem upper = new MenuItem("Bring Forward");
        upper.setOnAction(e -> {
            if (selectedLayerIndex == -1) return;
            LayerGroup layerGroup = LayersControl.getInstance().getLayerGroup();
            Layer temp = layerGroup.layers.get(selectedLayerIndex);
            layerGroup.layers.set(selectedLayerIndex, layerGroup.layers.get(selectedLayerIndex + 1));
            layerGroup.layers.set(selectedLayerIndex + 1, temp);
            LayersControl.getInstance().repaint();
        });

        MenuItem lower = new MenuItem("Bring Backward");
        lower.setOnAction(actionEvent -> {
            if (selectedLayerIndex == -1) return;
            LayerGroup layerGroup = LayersControl.getInstance().getLayerGroup();
            Layer temp = layerGroup.layers.get(selectedLayerIndex);
            layerGroup.layers.set(selectedLayerIndex, layerGroup.layers.get(selectedLayerIndex - 1));
            layerGroup.layers.set(selectedLayerIndex - 1, temp);
            LayersControl.getInstance().repaint();
        });

        MenuItem top = new MenuItem("Bring TopMost");
        top.setOnAction(actionEvent -> {
            if (selectedLayerIndex == -1) return;
            LayerGroup layerGroup = LayersControl.getInstance().getLayerGroup();
            Layer temp = layerGroup.layers.get(selectedLayerIndex);
            layerGroup.layers.remove(selectedLayerIndex);
            layerGroup.layers.add(temp);
            LayersControl.getInstance().repaint();
        });

        MenuItem bottom = new MenuItem("Bring Bottom");
        bottom.setOnAction(actionEvent -> {
            if (selectedLayerIndex == -1) return;
            LayerGroup layerGroup = LayersControl.getInstance().getLayerGroup();
            Layer temp = layerGroup.layers.get(selectedLayerIndex);
            layerGroup.layers.remove(selectedLayerIndex);
            layerGroup.layers.add(0,temp);
            LayersControl.getInstance().repaint();
        });
        contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(lower, upper, top, bottom);
    }

    private PointGroup getPointGroup(MouseEvent e) {
        return new PointGroup(
                Math.min(x_start, e.getX()),
                Math.min(y_start, e.getY()),
                Math.max(x_start, e.getX()),
                Math.max(y_start, e.getY())
        );
    }

    private Layer getCurrentDrawingLayer(MouseEvent e) {
        // will never call here
        return switch (ControllerAdapter.input_status) {
            case LINE -> LayerFactory.createShapeLayer(ControllerAdapter.input_status, new PointGroup(x_start, y_start, e.getX(), e.getY()));
            case PEN, TEXT, SELECT -> null;
            default -> LayerFactory.createShapeLayer(ControllerAdapter.input_status, getPointGroup(e));
        };
    }

    ArrayList<Layer_Line> current_free_path;
    public static Color color = Color.BLACK;

    public void contextMenuRequested(MouseEvent e) {
        selectedLayerIndex = LayersControl.getInstance().getLayerGroup().getLayerIndexByPosition((float) e.getX(), (float) e.getY());
        if (selectedLayerIndex == -1) {
            // not found layer, set both to disabled
            contextMenu.getItems().get(0).setDisable(true);
            contextMenu.getItems().get(1).setDisable(true);
            contextMenu.getItems().get(2).setDisable(true);
            contextMenu.getItems().get(3).setDisable(true);
            return;
        }
        // check whether layer can move upper/lower
        contextMenu.getItems().get(0).setDisable(!(selectedLayerIndex > 0));
        contextMenu.getItems().get(1).setDisable(!(selectedLayerIndex < LayersControl.getInstance().getLayerGroup().layers.size() - 1));
        contextMenu.getItems().get(2).setDisable(selectedLayerIndex == LayersControl.getInstance().getLayerGroup().layers.size() - 1);
        contextMenu.getItems().get(3).setDisable(selectedLayerIndex == 0);
        contextMenu.show(LayersControl.getInstance().getActiveCanvas(), e.getScreenX(), e.getScreenY());
    }

    public void mouseDown(MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY) {
            contextMenuRequested(e);
            return;
        }
        // System.out.println("down");
        if (ControllerAdapter.input_status == ControllerAdapter.Input_status.SELECT) {
            selectedLayer = LayersControl.getInstance().getLayerGroup().getLayerByPosition((float) e.getX(), (float) e.getY());
            if(selectedLayer!=null){
                LayersControl.getInstance().getLayerHistory().forward("Move Shape");
            }
            // System.out.println("selected");
        } else {
            selectedLayer = null;
        }
        // create an empty path vector
        if (ControllerAdapter.input_status == ControllerAdapter.Input_status.PEN) {
            LayersControl.getInstance().getLayerHistory().forward("Create Free Line");
            current_free_path = new ArrayList<>();
        }
        x_start = e.getX();
        y_start = e.getY();
        isPressed = true;
    }

    public void mouseRelease(MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY) {
            return;
        }
        // System.out.println("release");
        isPressed = false;

        // if it is creating new shape
        {
            switch (ControllerAdapter.input_status){
                case RECTANGLE -> LayersControl.getInstance().getLayerHistory().forward("Create Rectangle");
                case LINE -> LayersControl.getInstance().getLayerHistory().forward("Create Line");
                case CIRCLE -> LayersControl.getInstance().getLayerHistory().forward("Create Circle");
                case ELLIPSE -> LayersControl.getInstance().getLayerHistory().forward("Create Ellipse");
                default -> {}
            }
            Layer layer = getCurrentDrawingLayer(e);
            if (layer != null) layer.fillType = ControllerAdapter.doFill ? Layer.FillType.FILL : Layer.FillType.NO;
            if (layer != null) LayersControl.getInstance().getLayerGroup().appendLayer(layer);
        }

        // if it is moving shape
        if (selectedLayer != null) {
            selectedLayer.applyShifting();
        }

        // if it is drawing free line
        if (ControllerAdapter.input_status == ControllerAdapter.Input_status.PEN) {
            Layer layer = LayerFactory.createCurveLayer(current_free_path);
            LayersControl.getInstance().getLayerGroup().appendLayer(layer);
            current_free_path = null;
        }

        LayersControl.getInstance().repaint();

        ControllerAdapter.getInstance().refreshHistoryButton();
    }

    public void mouseDrag(MouseEvent e) {
        // System.out.println("move");
        if (!isPressed) {
            return;
        }

        // get graphics content
        GraphicsContext graphics = LayersControl.getInstance().getActiveCanvas().getGraphicsContext2D();

        // deal with "Select" tool
        if (ControllerAdapter.input_status == ControllerAdapter.Input_status.SELECT) {
            if (selectedLayer == null) return;
            selectedLayer.x_shifting = (float) (e.getX() - x_start);
            selectedLayer.y_shifting = (float) (e.getY() - y_start);
        }

        // specially deal with free line
        if (ControllerAdapter.input_status == ControllerAdapter.Input_status.PEN) {
            Layer_Line line = new Layer_Line(
                    new PointGroup(x_start, y_start, e.getX(), e.getY())
            );
            line.lineType = ControllerAdapter.getInstance().getLineType();
            line.width = ControllerAdapter.getInstance().getLineWidth();
            line.color = new ColorInfo(color);
            x_start = e.getX();
            y_start = e.getY();
            line.draw(graphics);
            current_free_path.add(line);
            return;
        }

        // draw
        LayersControl.getInstance().repaint();

        // specially deal with text tool
        if (ControllerAdapter.input_status == ControllerAdapter.Input_status.TEXT) {
            Layer bound = LayerFactory.createShapeLayer(ControllerAdapter.input_status, getPointGroup(e));
            bound.fillType = Layer.FillType.NO;
            bound.width = 3;
            bound.lineType = Layer.LineType.FULL;
            bound.draw(graphics);
            return;
        }

        Layer temp = getCurrentDrawingLayer(e);
        if (temp != null) temp.fillType = ControllerAdapter.doFill ? Layer.FillType.FILL : Layer.FillType.NO;
        if (temp != null) temp.draw(graphics);
    }

    public void mouseMove(MouseEvent e) {
        if (isPressed) return;
        if (ControllerAdapter.input_status != ControllerAdapter.Input_status.SELECT) {
            ControllerAdapter.getInstance()._tabPane.setCursor(Cursor.DEFAULT);
            return;
        }
        if (LayersControl.getInstance().getLayerGroup().getLayerByPosition((float) e.getX(), (float) e.getY()) != null) {
            ControllerAdapter.getInstance()._tabPane.setCursor(Cursor.MOVE);
        } else {
            ControllerAdapter.getInstance()._tabPane.setCursor(Cursor.DEFAULT);
        }
    }

}
