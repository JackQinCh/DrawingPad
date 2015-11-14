package Zhonghua;

import java.awt.*;
import java.util.Iterator;

/**
 * Created by jack on 15/11/13.
 */
public class DrawingCanvas extends draw3.KeyboardDrawingCanvas{
    @Override
    public void paint(Graphics g) {
        Dimension dim = getSize();
        g.setColor(getBackground());
        g.fillRect(0, 0, dim.width, dim.height);
        g.setColor(Color.black);
        if (shapes != null) {
            Iterator iter = shapes.iterator();
            while (iter.hasNext()) {
                scribble3.Shape shape = (scribble3.Shape) iter.next();
                if (shape != null) {
                    shape.draw(g);
                }
            }
        }
    }
}
