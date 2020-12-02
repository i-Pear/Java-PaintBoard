package Layers;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class Layer_Text extends Layer {

    String text;
    Font font;
    int pos_x, pos_y;

    public Layer_Text(String text, Font font, int x, int y) {
        this.text = text;
        this.font = font;
        this.pos_x = x;
        this.pos_y = y;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setFont(font);
        graphics.strokeText(text, pos_x + x_shifting, pos_y + y_shifting);
    }

    @Override
    public boolean isInner(float x, float y) {
        Rectangle2D rectangle_outer = new Rectangle2D(pos_x, pos_y, text.length() * font.getSize(), font.getSize());
        return rectangle_outer.contains(x,y);
    }

    @Override
    public void applyShifting() {
        pos_x += x_shifting;
        pos_y += y_shifting;
        x_shifting = y_shifting = 0;
    }

}
