package draw2;

import draw1.*;
import scribble3.ScribbleTool;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jack on 15/11/13.
 */
public class DrawingPad extends draw1.DrawingPad {

    public DrawingPad(String title) {
        super(title);
    }

    protected void initTools() {
        toolkit = new ToolKit();
        toolkit.addTool(new ScribbleTool(canvas, "Scribble"));
        toolkit.addTool(new TwoEndsShapeTool(canvas, "Line", new LineShape()));
        toolkit.addTool(new TwoEndsShapeTool(canvas, "Oval", new OvalShape()));
        toolkit.addTool(new TwoEndsShapeTool(canvas, "Rect", new RectangleShape()));
        toolkit.addTool(new TwoEndsShapeTool(canvas, "Filled Oval", new FilledOvalShape()));
        toolkit.addTool(new TwoEndsShapeTool(canvas, "Filled Rect", new FilledRectangleShape()));
        drawingCanvas.setTool(toolkit.getTool(0));
    }

    public static void main(String[] args) {
        JFrame frame = new DrawingPad("Drawing Pad");
        frame.setSize(width, height);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width/2 - width/2,
        screenSize.height/2 - height/2);
        frame.setVisible(true);
    }

}