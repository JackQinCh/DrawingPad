package Zhonghua;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

/**
 * Created by jack on 15/11/13.
 */
public class DrawingCanvas extends draw3.KeyboardDrawingCanvas{
    @Override
    public void paint(Graphics g) {
        Dimension dim = getSize();
        //Background color
        g.setColor(getBackground());
        g.fillRect(0, 0, dim.width, dim.height);
        //Brush color
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

    public boolean openFileWithFeedback(String filename){
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
            shapes = (java.util.List) in.readObject();
            in.close();
            repaint();
        } catch (IOException e1) {
            System.out.println("Unable to open file: " + filename);
            return false;
        } catch (ClassNotFoundException e2) {
            System.out.println(e2);
            return false;
        }
        return true;
    }
}
