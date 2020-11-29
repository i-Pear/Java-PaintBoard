package Layers;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Layer_Line extends Layer {

    private Point2D start, end;

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setFill(color.getColor());
        graphics.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
    }

}
