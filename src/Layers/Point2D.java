package Layers;

import java.io.Serializable;

/**
 * A self-made point class, because the native Point2D class is not Serializable
 */
public class Point2D implements Serializable {

    private double x;
    private double y;

    public final double getX() {
        return this.x;
    }

    public final double getY() {
        return this.y;
    }

    public Point2D(Point2D point2D){
        this.x= point2D.getX();
        this.y= point2D.getY();
    }

    public Point2D(double x,double y) {
        this.x = x;
        this.y = y;
    }

    public Point2D add(double x, double y) {
        return new Point2D(this.getX() + x, this.getY() + y);
    }

}

