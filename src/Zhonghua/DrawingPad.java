package Zhonghua;

import scribble2.ColorDialog;
import scribble3.ScribbleCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by jack on 15/11/13.
 */
public class DrawingPad extends draw3.DrawingPad{
    protected final String extensionFileName = ".zhonghua";
    protected String myTitle;
    private final String defaultFileName = "./saves/Untitled.zhonghua";
    public DrawingPad(String title) {
        super(title);
        this.myTitle = title;

        //Set chooser default directory
        chooser.setCurrentDirectory(new File("./saves"));
        chooser.setFileFilter(new FileFilter());
        //Open default file.
        if (canvas.openFile(defaultFileName)){
            currentFilename = defaultFileName;
            setTitle(myTitle+" [" + currentFilename + "]");
        }

        JMenu optionMenu = menuBar.getMenu(2);
        addChangeBackgroundColorOptionToMenu(optionMenu);

    }

    @Override
    protected ScribbleCanvas makeCanvas() {
        return (drawingCanvas = keyboardDrawingCanvas = new DrawingCanvas());
    }

    private void addChangeBackgroundColorOptionToMenu(JMenu optionMenu) {
        JMenuItem mi = new JMenuItem("BackgroundColor");
        optionMenu.add(mi);
        mi.addActionListener(new ColorListener());
    }

    class ColorListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Color result = dialog.showDialog();
            if (result != null) {
                canvas.setBackground(result);
                canvas.repaint();
            }
        }

        protected ColorDialog dialog =
                new ColorDialog(DrawingPad.this, "Choose color", canvas.getBackground());

    }

    @Override
    public void newFile() {
        currentFilename = null;
        canvas.newFile();
        setTitle(myTitle);
    }

    @Override
    public void openFile(String filename) {
        if (!getExtension(filename).equals(extensionFileName)){
            JOptionPane.showMessageDialog(null, "Can't open this file.");
            return;
        }
        if (canvas.openFile(filename)){
            currentFilename = filename;
            setTitle(myTitle+" [" + currentFilename + "]");
        }else {
            JOptionPane.showMessageDialog(null, "Can't open this file.");
        }

    }

    @Override
    public void saveFile() {
        if (currentFilename == null) {
            currentFilename = "./saves/Untitled" + extensionFileName;
        }
        canvas.saveFile(currentFilename);
        setTitle(myTitle+" [" + currentFilename + "]");
    }

    @Override
    public void saveFileAs(String filename) {
        System.out.println(getExtension(filename));
        if (!getExtension(filename).equals(extensionFileName))
            currentFilename = filename + extensionFileName;
        canvas.saveFile(currentFilename);
        setTitle(myTitle+" [" + currentFilename + "]");
    }

    public static void main(String[] args) {
        JFrame frame = new DrawingPad("Zhonghua Drawing Pad");
        frame.setSize(width, height);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width/2 - width/2,
                screenSize.height/2 - height/2);
        frame.setVisible(true);
    }

    /**
     * Get file extension
     * @param f String File name
     * @return String File extension
     */
    protected static String getExtension(String f) {
        String ext = "";
        int i = f.lastIndexOf('.');

        if (i > 0 &&  i < f.length() - 1) {
            ext = f.substring(i);
        }
        return ext;
    }
}
