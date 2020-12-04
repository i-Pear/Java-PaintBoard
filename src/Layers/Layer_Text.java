package Layers;

import Controller.CanvasController;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Implement for text layer
 */
public class Layer_Text extends Layer {

    String text;
    FontInfo font;
    int pos_x, pos_y;

    public Layer_Text(String text, FontInfo font, int x, int y) {
        this.color = new ColorInfo(CanvasController.color);
        this.text = text;
        this.font = font;
        this.pos_x = x;
        this.pos_y = y;

        this.fillType=FillType.FILL;
        this.lineType=LineType.FULL;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setFont(font.getFont());
        setGraphicsMode(graphics);
        graphics.fillText(text, pos_x + x_shifting, pos_y + y_shifting);
    }

    @Override
    public boolean isInner(float x, float y) {
        Rectangle2D rectangle_outer = new Rectangle2D(pos_x, pos_y - font.getFont().getSize(), text.length() * font.getFont().getSize(), font.getFont().getSize());
        return rectangle_outer.contains(x, y);
    }

    @Override
    public void applyShifting() {
        pos_x += x_shifting;
        pos_y += y_shifting;
        x_shifting = y_shifting = 0;
    }

    @Override
    public Layer getClone() {
        Layer_Text new_layer = new Layer_Text(text, new FontInfo(font), pos_x, pos_y);
        super.setClone(new_layer);
        return new_layer;
    }

}
