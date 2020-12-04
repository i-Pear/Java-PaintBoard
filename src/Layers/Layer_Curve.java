package Layers;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Layer_Curve extends Layer {

    ArrayList<Layer_Line> lines;

    public Layer_Curve(ArrayList<Layer_Line> point2DArray, ColorInfo color, LineType lineType, float lineWidth) {
        layerType = LayerType.CURVE;
        this.lineType=lineType;
        this.color=color;
        this.width=lineWidth;
        // deep copy lines
        lines = new ArrayList<>();
        for(Layer_Line line:point2DArray){
            lines.add((Layer_Line) line.getClone());
        }
        // set properties
        for (Layer_Line line : lines) {
            line.color = color;
            line.lineType = lineType;
            line.width = lineWidth;
        }
    }

    @Override
    public void draw(GraphicsContext graphics) {
        int len = lines.size();
        for (int i = 0; i < len - 1; i++) {
            Layer_Line line = lines.get(i);
            line.x_shifting = x_shifting;
            line.y_shifting = y_shifting;
            line.draw(graphics);
        }
    }

    @Override
    public boolean isInner(float x, float y) {
        for (Layer_Line line : lines) {
            if (line.isInner(x, y)) return true;
        }
        return false;
    }

    @Override
    public void applyShifting() {
        int size = lines.size();
        for (int i = 0; i < size; i++) {
            lines.get(i).applyShifting();
        }
        x_shifting = y_shifting = 0;
    }

    @Override
    public Layer getClone() {
        Layer_Curve new_layer = new Layer_Curve(lines, new ColorInfo(color), lineType, width);
        super.setClone(new_layer);
        return new_layer;
    }

}
