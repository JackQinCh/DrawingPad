package Zhonghua;

import scribble3.AbstractTool;
import scribble3.ScribbleCanvas;

import java.awt.*;

/**
 * Created by jack on 15/11/15.
 */
public class EraserTool extends AbstractTool{
    private final int px;
    protected EraserTool(ScribbleCanvas canvas, String name, int px) {
        super(canvas, name);
        this.px = px;
    }

    @Override
    public void startShape(Point p) {
        eraser = new EraserShap(px);
        eraser.addPoint(p);
        Graphics g = canvas.getGraphics();
        g.setColor(canvas.getBackground());
        g.fillOval(p.x - px, p.y - px, px*2, px*2);
    }

    @Override
    public void addPointToShape(Point p) {
        if (eraser != null) {
            eraser.addPoint(p);
            Graphics g = canvas.getGraphics();
            g.setColor(canvas.getBackground());
            g.fillOval(p.x - px, p.y - px, px*2, px*2);
        }
    }

    @Override
    public void endShape(Point p) {
        if (eraser != null) {
            eraser.addPoint(p);
            Graphics g = canvas.getGraphics();
            g.setColor(canvas.getBackground());
            g.fillOval(p.x - px, p.y - px, px*2, px*2);
            canvas.addShape(eraser);
            eraser = null;
        }

    }
    protected EraserShap eraser = null;
}
