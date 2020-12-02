package Layers;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Layer_Curve extends Layer{

    ArrayList<Layer_Line> lines;

    public Layer_Curve(ArrayList<Layer_Line> point2DArray){
        lines =point2DArray;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setLineWidth(width);
        graphics.setFill(color.getColor());
        int len= lines.size();
        for(int i=0;i<len-1;i++){
            Layer_Line line=lines.get(i);
            line.x_shifting=x_shifting;
            line.y_shifting=y_shifting;
            line.draw(graphics);
        }
    }

    @Override
    public boolean isInner(float x, float y) {
        return false;
    }

    @Override
    public void applyShifting() {
        int size= lines.size();
        for(int i=0;i<size;i++){
            lines.get(i).applyShifting();
        }
        x_shifting=y_shifting=0;
    }

}
