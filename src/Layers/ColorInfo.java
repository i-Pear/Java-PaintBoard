package Layers;

import javafx.scene.paint.Color;

public class ColorInfo {

    double R, G, B;

    public ColorInfo(Color color){
        R=color.getRed();
        G=color.getGreen();
        B=color.getBlue();
    }

    public Color getColor() {
        return new Color(R, G, B,1);
    }

}
