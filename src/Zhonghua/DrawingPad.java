package Zhonghua;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jack on 15/11/13.
 */
public class DrawingPad extends draw3.DrawingPad{
    public DrawingPad(String title) {
        super(title);

        chooser.setFileFilter(new FileFilter());

    }

    public static void main(String[] args) {
        JFrame frame = new draw3.DrawingPad("Zhonghua Drawing Pad");
        frame.setSize(width, height);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width/2 - width/2,
                screenSize.height/2 - height/2);
        frame.setVisible(true);
    }
}
