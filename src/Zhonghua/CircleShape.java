package Zhonghua;

import draw1.TwoEndsShape;

import java.awt.*;

/**
 * CircleShape
 * Created by Zhonghua on 15/11/14.
 */
public class CircleShape extends TwoEndsShape {
    int x, y, d;
    @Override
    public void drawOutline(Graphics g, int x1, int y1, int x2, int y2) {
        int r2 = Math.abs(x1-x2)*Math.abs(x1-x2)+Math.abs(y1-y2)*Math.abs(y1-y2);
        int r = (int)Math.sqrt(r2);
        d = 2*r;
        x = x1 - r;
        y = y1 - r;
        g.drawOval(x, y, d, d);
    }

    @Override
    public void draw(Graphics g) {

        if (color != null)
            g.setColor(color);
        g.drawOval(x, y, d, d);
    }
}
