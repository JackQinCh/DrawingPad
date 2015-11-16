package Zhonghua;

import scribble3.Shape;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by jack on 15/11/15.
 */
public class EraserShap extends Shape {
    private final int px;

    public EraserShap(int px) {
        this.px = px;
    }

    @Override
    public void draw(Graphics g) {
        for (Point point:points){
            g.fillOval(point.x - px, point.y - px, px*2, px*2);
        }
    }

    public void addPoint(Point p) {
        if (p != null) {
            points.add(p);
        }
    }

    private final List<Point> points = new ArrayList();
}
