package Layers;

/**
 * A pack of two points, for decreasing the count of arguments
 */
public class PointGroup {

    public Point2D p0,p1;

    public PointGroup(double x0,double y0,double x1,double y1){
        p0=new Point2D(x0,y0);
        p1=new Point2D(x1,y1);
    }

}
