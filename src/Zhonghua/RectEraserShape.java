package Zhonghua;

import draw1.RectangleShape;

import java.awt.*;

/**
 * Created by jack on 15/11/15.
 */
public class RectEraserShape extends RectangleShape {

    @Override
    public void draw(Graphics g) {
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int w = Math.abs(x1 - x2) + 1;
        int h = Math.abs(y1 - y2) + 1;
        g.fillRect(x, y, w, h);
    }
}
