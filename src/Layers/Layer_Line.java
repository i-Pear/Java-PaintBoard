package Layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Polygon;

public class Layer_Line extends Layer {

    Point2D start, end;

    Layer_Line(double x0, double y0, double x1, double y1) {
        layerType = LayerType.LINE;

        start = new Point2D(x0, y0);
        end = new Point2D(x1, y1);
    }

    public Layer_Line(PointGroup pointGroup) {
        layerType = LayerType.LINE;

        start = pointGroup.p0;
        end = pointGroup.p1;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        setGraphicsMode(graphics);
        graphics.strokeLine(
                start.getX() + x_shifting, start.getY() + y_shifting,
                end.getX() + x_shifting, end.getY() + y_shifting
        );
    }

    @Override
    public boolean isInner(float x, float y) {
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();
        double length = Math.sqrt(dx * dx + dy * dy);
        double selectWidth = Math.max(width, 10);
        double x_standard = dx / length * selectWidth / 2;
        double y_standard = dy / length * selectWidth / 2;

        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(
                start.getX() - y_standard, start.getY() + x_standard,
                start.getX() + y_standard, start.getY() - x_standard,
                end.getX() - y_standard, end.getY() + x_standard,
                end.getX() + y_standard, end.getY() - x_standard
        );
        return polygon.contains(x, y);
    }

    @Override
    public void applyShifting() {
        start = start.add(x_shifting, y_shifting);
        end = end.add(x_shifting, y_shifting);
        x_shifting = y_shifting = 0;
    }

    @Override
    public Layer getClone() {
        Layer_Line new_layer = new Layer_Line(start.getX(), start.getY(), end.getX(), end.getY());
        super.setClone(new_layer);
        return new_layer;
    }

}
