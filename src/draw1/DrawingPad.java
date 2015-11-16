package draw1;

import scribble3.Scribble;
import scribble3.ScribbleCanvas;
import scribble3.ScribbleTool;
import scribble3.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawingPad extends Scribble {

    protected final JComponent toolbar;
    public DrawingPad(String title) {
        super(title);
        initTools();
        ActionListener toolListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Object source = event.getSource();
                if (source instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) source;
                    Tool tool = toolkit.setSelectedTool(button.getText());
                    drawingCanvas.setTool(tool);
                }
            }
        };
        toolbar = createToolBar(toolListener);
        getContentPane().add(toolbar, BorderLayout.SOUTH);
        JMenu menu = createToolMenu(toolListener);
        menuBar.add(menu, 1); // insert at index position 1
    }

    public Tool getSelectedTool() {
        return toolkit.getSelectedTool();
    }

    protected void initTools() {
        toolkit = new ToolKit();
        toolkit.addTool(new ScribbleTool(canvas, "Scribble"));
        toolkit.addTool(new TwoEndsTool(canvas, "Line", TwoEndsTool.LINE));
        toolkit.addTool(new TwoEndsTool(canvas, "Oval", TwoEndsTool.OVAL));
        toolkit.addTool(new TwoEndsTool(canvas, "Rectangle", TwoEndsTool.RECT));
        drawingCanvas.setTool(toolkit.getTool(0));
    }

    // factory method
    protected ScribbleCanvas makeCanvas() {
        return (drawingCanvas = new DrawingCanvas());
    }

    protected JComponent createToolBar(ActionListener toolListener) {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(true);
        int n = toolkit.getToolCount();
        for (int i = 0; i < n; i++) {
            Tool tool = toolkit.getTool(i);
            if (tool != null) {
                JButton button = new JButton(tool.getName());
                button.addActionListener(toolListener);
                toolbar.add(button);
            }
        }
        return toolbar;
    }

    protected JMenu createToolMenu(ActionListener toolListener) {
        JMenu menu = new JMenu("Tools");
        int n = toolkit.getToolCount();
        for (int i = 0; i < n; i++) {
            Tool tool = toolkit.getTool(i);
            if (tool != null) {
                JMenuItem menuitem = new JMenuItem(tool.getName());
                menuitem.addActionListener(toolListener);
                menu.add(menuitem);
            }
        }
        return menu;
    }

    protected ToolKit toolkit;
    public DrawingCanvas drawingCanvas;

    public static void main(String[] args) {
        JFrame frame = new DrawingPad("Drawing Pad");
        frame.setSize(width, height);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width/2 - width/2,
                screenSize.height/2 - height/2);
        frame.setVisible(true);
    }

}
