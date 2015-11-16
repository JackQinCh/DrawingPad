package Zhonghua;

import java.awt.*;

/**
 * FilledTriangleShape
 * Created by Zhonghua on 15/11/14.
 */
public class FilledTriangleShape extends TriangleShape {
    @Override
    public void draw(Graphics g) {
        if (color != null)
            g.setColor(color);
        g.fillPolygon(xArray, yArray, 3);
    }
}
