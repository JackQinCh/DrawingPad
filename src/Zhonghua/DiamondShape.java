package Zhonghua;

import draw1.TwoEndsShape;

import java.awt.*;

/**
 * DiamondShape
 * Created by Zhonghua on 15/11/14.
 */
public class DiamondShape extends TwoEndsShape {

    protected int[] xArray, yArray;
    @Override
    public void draw(Graphics g) {
        if (color != null)
            g.setColor(color);
        g.drawPolygon(xArray, yArray, 4);
    }

    @Override
    public void drawOutline(Graphics g, int x1, int y1, int x2, int y2) {
        int xl,xr,xt,xb,yl,yr,yt,yb;
        xl = Math.min(x1, x2);
        xr = Math.max(x1, x2);
        yl = yr = (y1 + y2)/2;
        xt = xb = (x1 + x2)/2;
        yt = Math.min(y1,y2);
        yb = Math.max(y1, y2);
        xArray = new int[]{xl,xt,xr,xb};
        yArray = new int[]{yl,yt,yr,yb};
        g.drawPolygon(xArray, yArray, 4);
    }
}
