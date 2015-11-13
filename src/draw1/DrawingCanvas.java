

package draw1;

import scribble3.ScribbleCanvas;
import scribble3.Tool;

import java.util.EventListener;

public class DrawingCanvas extends ScribbleCanvas {

    public DrawingCanvas() {
    }

    public void setTool(Tool tool) {
        drawingCanvasListener.setTool(tool);
    }

    public Tool getTool() {
        return drawingCanvasListener.getTool();
    }

    // factory method
    protected EventListener makeCanvasListener() {
        return (drawingCanvasListener = new DrawingCanvasListener(this));
    }

    protected DrawingCanvasListener drawingCanvasListener;

}
