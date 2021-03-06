package Zhonghua;

import draw3.KeyboardDrawingCanvasListener;

import java.awt.event.MouseEvent;

/**
 * DrawingListener
 * Created by Zhonghua on 15/11/15.
 */
public class DrawingListener extends KeyboardDrawingCanvasListener {
    public DrawingListener(DrawingCanvas canvas) {
        super(canvas);
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        ((DrawingCanvas)canvas).addUndoEdit();
    }

}
