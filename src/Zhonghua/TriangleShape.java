package Zhonghua;

import java.awt.*;

/**
 * Created by jack on 15/11/14.
 */
public class TriangleShape extends DiamondShape{
    @Override
    public void drawOutline(Graphics g, int x1, int y1, int x2, int y2) {
        int xl,xr,xc,yl,yr,yc;
        xl = x1;
        yl = yr = y2;
        xr = x2;
        xc = (x1+x2)/2;
        yc = y1;
        xArray = new int[]{xl,xc,xr};
        yArray = new int[]{yl,yc,yr};
        g.drawPolygon(xArray, yArray, 3);
    }

    @Override
    public void draw(Graphics g) {
        if (color != null)
            g.setColor(color);
        g.drawPolygon(xArray, yArray, 3);
    }
}
