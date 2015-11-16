package Zhonghua;

import scribble3.*;
import scribble3.Shape;

import javax.swing.*;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.List;

/**
 * DrawingCanvas
 * Created by Zhonghua on 15/11/13.
 */
public class DrawingCanvas extends draw3.KeyboardDrawingCanvas{
    public DrawingCanvas(UndoListener listener) {
        setBackground(Color.white);
        undoManager = new UndoManager();
        this.undoListener = listener;
    }

    public void setBackgroundImage(ImageIcon b) {
        backgroundImage = b;
        if (backgroundImage != null)
            setBackground(Color.white);
    }

    protected ImageIcon backgroundImage;

    @Override
    public void paint(Graphics g) {
        Dimension dim = getSize();
        //Background image
        if (backgroundImage != null){
            g.drawImage(backgroundImage.getImage(), 0, 0, getSize().width, getSize().height, this);
        }else{//Background color
            g.setColor(getBackground());
            g.fillRect(0, 0, dim.width, dim.height);
        }

        //Brush color
        if (shapes != null) {
            Iterator iter = shapes.iterator();
            while (iter.hasNext()) {
                g.setColor(getBackground());
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
        initUndoManager();
        return true;
    }

    protected UndoManager undoManager;
    protected UndoListener undoListener;

    @Override
    public void newFile() {
        super.newFile();
        initUndoManager();
    }

    protected void initUndoManager(){
        undoManager = new UndoManager();
        undoListener.checkUndoManager(undoManager);
        System.out.println("Can Undo:"+undoManager.canUndo());
        System.out.println("Can Redo:"+undoManager.canRedo());
    }
    @Override
    protected EventListener makeCanvasListener() {
        return (drawingCanvasListener = new DrawingListener(this));
    }

    protected void undo(){
        try{
            undoManager.undo();
            repaint();
            undoListener.checkUndoManager(undoManager);
        }catch (CannotUndoException e){
            e.printStackTrace();
        }
    }
    protected void redo(){
        try{
            undoManager.redo();
            repaint();
            undoListener.checkUndoManager(undoManager);
        }catch (CannotRedoException e){
            e.printStackTrace();
        }
    }

    protected void addUndoEdit(){
        undoManager.addEdit(new DrawEdit(shapes));
        undoListener.checkUndoManager(undoManager);
    }

    private final class DrawEdit extends AbstractUndoableEdit {
        private List<Shape> shapes;
        private Shape saveShape;

        public DrawEdit(List<Shape> shapes) {
            this.shapes = shapes;
        }

        @Override
        public void undo() throws CannotUndoException {
            super.undo();
            saveShape = shapes.get(shapes.size()-1);
            shapes.remove(shapes.size()-1);
        }

        @Override
        public void redo() throws CannotRedoException {
            super.redo();
            shapes.add(saveShape);
        }
    }
}
