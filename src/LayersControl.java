import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tab;

import java.util.ArrayList;

public class LayersControl {

    private ArrayList<Canvas> canvas;
    private ArrayList<Tab> tabs;
    private int activeID;

    private static LayersControl instance;

    private LayersControl() {
        instance=new LayersControl();
        activeID=0;
    }

    public static LayersControl getInstance() {
        return instance;
    }

    public GraphicsContext getActiveGraphics(){
        return canvas.get(activeID).getGraphicsContext2D();
    }

}
