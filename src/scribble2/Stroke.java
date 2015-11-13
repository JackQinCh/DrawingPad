package scribble2;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * Created by jack on 15/11/13.
 */
public class Stroke implements Serializable {

    public Stroke() {}

    public Stroke(Color color) {
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void addPoint(Point p) {
        if (p != null) {
            points.add(p);
        }
    }

    public List getPoints() {
        return points;
    }

    // The list of points on the stroke
    // elements are instances of java.awt.Point
    protected List points = new ArrayList();

    protected Color color = Color.black;

}
