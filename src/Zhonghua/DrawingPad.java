package Zhonghua;

import scribble2.ColorDialog;
import scribble3.ScribbleCanvas;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by jack on 15/11/13.
 */
public class DrawingPad extends draw3.DrawingPad{
    protected final String extensionFileName = "zhonghua";
    protected String myTitle;
    private final String defaultFileName = "./saves/Untitled.zhonghua";
    public DrawingPad(String title) {
        super(title);
        this.myTitle = title;

        //Set chooser default directory
        chooser.setCurrentDirectory(new File("./saves"));
        chooser.setFileFilter(new FileNameExtensionFilter("Drawing File(.zhonghua)",extensionFileName));
        imageChooser.setCurrentDirectory(new File("."));
        imageChooser.setFileFilter(new FileNameExtensionFilter("Image Files(jpeg,png,jpg)", "jpeg","png","jpg"));
        //Open default file.
        if (((DrawingCanvas)canvas).openFileWithFeedback(defaultFileName)){
            currentFilename = defaultFileName;
            setTitle(myTitle+" [" + currentFilename + "]");
        }

        JMenu optionMenu = menuBar.getMenu(2);
        addChangeBackgroundColorOptionToMenu(optionMenu);
        addSelectImageForBackgroundOptionToMenu(optionMenu);

    }

    private void addSelectImageForBackgroundOptionToMenu(JMenu optionMenu) {
        JMenuItem mi = new JMenuItem("Background Image");
        optionMenu.add(mi);
        mi.addActionListener(new ImageListener());
    }

    @Override
    protected ScribbleCanvas makeCanvas() {
        return (drawingCanvas = keyboardDrawingCanvas = new DrawingCanvas());
    }

    private void addChangeBackgroundColorOptionToMenu(JMenu optionMenu) {
        JMenuItem mi = new JMenuItem("Background Color");
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
        if (!checkExtension(filename, extensionFileName)){
            JOptionPane.showMessageDialog(null, "Please select Zhonghua file.");
            return;
        }
        if (((DrawingCanvas)canvas).openFileWithFeedback(filename)){
            currentFilename = filename;
            setTitle(myTitle+" [" + currentFilename + "]");
        }else {
            JOptionPane.showMessageDialog(null, "Can't open this file.");
        }
    }

    @Override
    public void saveFile() {
        if (currentFilename == null) {
            currentFilename = defaultFileName;
        }
        canvas.saveFile(currentFilename);
        setTitle(myTitle+" [" + currentFilename + "]");
    }

    @Override
    public void saveFileAs(String filename) {
        if (!checkExtension(filename, extensionFileName))
            currentFilename = filename + "." + extensionFileName;
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
            ext = f.substring(i+1);
        }
        return ext;
    }

    /**
     * Check if file has one of the given extensions.
     * @param filename String
     * @param extensions List of String
     * @return boolean
     */
    protected static boolean checkExtension(String filename, String... extensions){
        String ext = getExtension(filename);
        for(String extension:extensions){
            if (ext.equals(extension)){
                return true;
            }
        }
        return false;
    }

    private class ImageListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            int retval = imageChooser.showDialog(null, "Select image");
            if (retval == JFileChooser.APPROVE_OPTION) {

                File theFile = imageChooser.getSelectedFile();
                if (theFile != null) {
                    if (theFile.isFile()) {
                        String filename = theFile.getAbsolutePath();
                        System.out.println("Selected an image:"+filename);
                        //
                        if (checkExtension(filename, "Image Files", "jpeg","png","jpg"))
                            setBackgroundImage(filename);
                        else
                            JOptionPane.showMessageDialog(null, "Please select image file.");
                    }
                }
            }
        }
    }
    public void setBackgroundImage(String filename){
        try{
            ((DrawingCanvas)canvas).setBackgroundImage(new ImageIcon(filename));
            canvas.repaint();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Can't open this file.");
        }
    }
    protected JFileChooser imageChooser = new JFileChooser();
}
