package Zhonghua;

import java.awt.*;

/**
 * Created by jack on 15/11/14.
 */
public class FilledDiamondShape extends DiamondShape {
    @Override
    public void draw(Graphics g) {
        if (color != null)
            g.setColor(color);
        g.fillPolygon(xArray, yArray, 4);
    }
}
