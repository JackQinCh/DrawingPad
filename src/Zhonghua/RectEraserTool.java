package Zhonghua;

import draw1.TwoEndsShape;
import draw2.TwoEndsShapeTool;
import scribble3.ScribbleCanvas;

import java.awt.*;

/**
 * Created by jack on 15/11/15.
 */
public class RectEraserTool extends TwoEndsShapeTool {

    public RectEraserTool(ScribbleCanvas canvas, String name, TwoEndsShape prototype) {
        super(canvas, name, prototype);
    }

    @Override
    public void endShape(Point p) {
        canvas.mouseButtonDown = false;
        if (prototype != null) {
            try {
                RectEraserShape rectEraserShape = (RectEraserShape) prototype.clone();
                rectEraserShape.setEnds(xStart, yStart, p.x, p.y);
                canvas.addShape(rectEraserShape);
            } catch (CloneNotSupportedException e) {}
            Graphics g = canvas.getGraphics();
            g.setPaintMode();
            canvas.repaint();
        }
    }
}
