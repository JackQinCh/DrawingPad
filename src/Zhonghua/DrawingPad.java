package Zhonghua;

import com.sun.scenario.effect.ColorAdjust;
import draw2.TwoEndsShapeTool;
import scribble2.ColorDialog;
import scribble3.ScribbleCanvas;
import scribble3.Tool;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;

/**
 * Created by jack on 15/11/13.
 */
public class DrawingPad extends draw3.DrawingPad implements UndoListener{
    protected final String extensionFileName = "zhonghua";
    protected String myTitle;
    private final String defaultFileName = "./saves/Untitled.zhonghua";
    protected JToolBar editToolBar;
    private JButton undoButton;
    private JButton redoButton;
    private LinkedList<JButton> buttons = new LinkedList<>();
    private JLabel statusLabel;
    public DrawingPad(String title) {
        super(title);
        this.myTitle = title;

        for (Component button:toolbar.getComponents()){
            buttons.add((JButton) button);
        }

        //Set chooser default directory
        chooser.setCurrentDirectory(new File("./saves"));
        chooser.setFileFilter(new FileNameExtensionFilter("Drawing File(.zhonghua)",extensionFileName));
        imageChooser.setCurrentDirectory(new File("."));
        imageChooser.setFileFilter(new FileNameExtensionFilter("Image Files(jpeg,png,jpg)", "jpeg","png","jpg"));


        JMenu optionMenu = menuBar.getMenu(2);
        addChangeBackgroundColorOptionToMenu(optionMenu);
        addSelectImageForBackgroundOptionToMenu(optionMenu);

        editToolBar = initEditToolBar();
        getContentPane().add(editToolBar, BorderLayout.EAST);

        //Open default file.
        if (((DrawingCanvas)canvas).openFileWithFeedback(defaultFileName)){
            currentFilename = defaultFileName;
            setTitle(myTitle+" [" + currentFilename + "]");
        }
        statusLabel = new JLabel("Current tool: "+toolkit.getTool(0).getName());
        toolbar.add(statusLabel);
    }

    protected JToolBar initEditToolBar(){
        JToolBar toolBar = new JToolBar("Edit tool bar", JToolBar.VERTICAL);

        undoButton = new JButton("Undo");
        undoButton.setEnabled(false);
        undoButton.addActionListener(new UndoActionListener());
        redoButton = new JButton("Redo");
        redoButton.setEnabled(false);
        redoButton.addActionListener(new RedoActionListener());

        JLabel eraserLabel = new JLabel("Eraser");
        JButton fiveEraserButton = new JButton("5 px");
        fiveEraserButton.addActionListener(new FivePXActionListener());
        JButton tenEraserButton = new JButton("10px");
        tenEraserButton.addActionListener(new TenPXActionListener());
        JButton thirtyEraserButton = new JButton("30px");
        thirtyEraserButton.addActionListener(new ThirtyPXActionListener());
        JButton rectEraserButton = new JButton("rect");
        rectEraserButton.addActionListener(new RectEraserActionListener());
        rectEraserButton.setBackground(Color.cyan);


        toolBar.add(undoButton);
        toolBar.add(redoButton);
        toolBar.addSeparator();
        toolBar.add(eraserLabel);
        toolBar.add(fiveEraserButton);
        toolBar.add(tenEraserButton);
        toolBar.add(thirtyEraserButton);
        toolBar.add(rectEraserButton);


        buttons.add(fiveEraserButton);
        buttons.add(tenEraserButton);
        buttons.add(thirtyEraserButton);
        buttons.add(rectEraserButton);

        return toolBar;
    }

    @Override
    protected void initTools() {
        super.initTools();
        toolkit.addTool(new TwoEndsShapeTool(canvas, "Diamond", new DiamondShape()));
        toolkit.addTool(new TwoEndsShapeTool(canvas, "Filled Diamond", new FilledDiamondShape()));
        toolkit.addTool(new TwoEndsShapeTool(canvas, "Triangle", new TriangleShape()));
        toolkit.addTool(new TwoEndsShapeTool(canvas, "Filled Triangle", new FilledTriangleShape()));
        toolkit.addTool(new TwoEndsShapeTool(canvas, "Circle", new CircleShape()));
        toolkit.addTool(new TwoEndsShapeTool(canvas, "Filled Circle", new FilledCircleShape()));
    }

    private void addSelectImageForBackgroundOptionToMenu(JMenu optionMenu) {
        JMenuItem mi = new JMenuItem("Background Image");
        optionMenu.add(mi);
        mi.addActionListener(new ImageListener());
    }

    @Override
    protected ScribbleCanvas makeCanvas() {
        return (drawingCanvas = keyboardDrawingCanvas = new DrawingCanvas(this));
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
                ((DrawingCanvas)canvas).setBackgroundImage(null);
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
        else
            currentFilename = filename;
        canvas.saveFile(currentFilename);
        setTitle(myTitle+" [" + currentFilename + "]");
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
    protected void setBackgroundImage(String filename){
        try{
            ((DrawingCanvas)canvas).setBackgroundImage(new ImageIcon(filename));
            canvas.repaint();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Can't open this file.");
        }
    }
    protected JFileChooser imageChooser = new JFileChooser();

    public static void main(String[] args) {
        width = 1000;
        height = 700;
        JFrame frame = new DrawingPad("Zhonghua Drawing Pad");
        frame.setSize(width, height);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width/2 - width/2,
                screenSize.height/2 - height/2);
        frame.setVisible(true);
    }

    private class UndoActionListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            ((DrawingCanvas)canvas).undo();
        }
    }


    private class RedoActionListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            ((DrawingCanvas)canvas).redo();
        }
    }

    @Override
    public void checkUndoManager(UndoManager manager) {
        undoButton.setEnabled(manager.canUndo());
        redoButton.setEnabled(manager.canRedo());
    }


    private class FivePXActionListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Tool tool = new EraserTool(canvas, "Eraser 5", 5);
            drawingCanvas.setTool(tool);
            refreshButtons("Eraser 5px");
        }
    }

    private class TenPXActionListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Tool tool = new EraserTool(canvas, "Eraser 10", 10);
            drawingCanvas.setTool(tool);
            refreshButtons("Eraser 10px");
        }
    }

    private class ThirtyPXActionListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Tool tool = new EraserTool(canvas, "Eraser 30", 30);
            drawingCanvas.setTool(tool);
            refreshButtons("Eraser 30px");

        }
    }

    private class RectEraserActionListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Tool tool = new TwoEndsShapeTool(canvas, "Rect Eraser", new RectEraserShape());
            drawingCanvas.setTool(tool);
            refreshButtons("Eraser Rect");
        }
    }

    @Override
    protected void refreshButtons(String name) {
        statusLabel.setText("Current tool: "+name);
    }
}
