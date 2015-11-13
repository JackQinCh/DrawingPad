package scribble1;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.EventListener;

/**
 * Created by jack on 15/11/13.
 */
public class ScribbleCanvas extends JPanel {

    public ScribbleCanvas() {
        listener = new ScribbleCanvasListener(this);
        addMouseListener((MouseListener) listener);
        addMouseMotionListener((MouseMotionListener) listener);
    }

    protected EventListener listener;
    protected boolean mouseButtonDown = false;
    protected int x, y;

}
