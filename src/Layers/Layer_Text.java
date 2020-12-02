package Layers;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class Layer_Text extends Layer{

    String text;
    Font font;
    int pos_x,pos_y;

    public Layer_Text(String text,Font font,int x,int y){
        this.text =text;
        this.font=font;
        this.pos_x=x;
        this.pos_y=y;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setFont(font);
        graphics.strokeText(text,pos_x,pos_y);
    }

}
