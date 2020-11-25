package controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class Controller {

    private boolean isPressed=false;
    private double x0,y0;

    @FXML
    private Canvas canvas1;

    @FXML
    public void newFile(){

    }

    @FXML
    public void mouseDown(MouseEvent e){
        System.out.println("down");
        x0=e.getX();
        y0=e.getY();
        isPressed=true;
    }

    @FXML
    public void mouseRelease(MouseEvent e){
        System.out.println("release");
        isPressed=false;
        canvas1.getGraphicsContext2D().strokeLine(x0,y0,e.getX(),e.getY());
    }

    @FXML
    public void mouseMove(MouseEvent e){
        if(!isPressed)return;
        System.out.println("do");
        canvas1.getGraphicsContext2D().clearRect(0,0, canvas1.getWidth(), canvas1.getWidth());
        canvas1.getGraphicsContext2D().strokeLine(x0,y0,e.getX(),e.getY());
    }

}
