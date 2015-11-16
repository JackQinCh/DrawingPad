package Zhonghua;

import java.awt.*;

/**
 * FilledCircleShape
 * Created by Zhonghua on 15/11/14.
 */
public class FilledCircleShape extends CircleShape {
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (color != null)
            g.setColor(color);
        g.fillOval(x, y, d, d);
    }
}
