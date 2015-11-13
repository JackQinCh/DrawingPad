package Zhonghua;

import java.io.File;

/**
 * Created by jack on 15/11/13.
 */
public class FileFilter extends javax.swing.filechooser.FileFilter{

    /**
     * Whether the given file is accepted by this filter.
     *
     * @param f
     */
    @Override
    public boolean accept(File f) {
        if (f.isDirectory())
            return true;
        return f.getName().endsWith(".zhonghua");
    }

    /**
     * The description of this filter. For example: "JPG and GIF Images"
     *
     * @see javax.swing.filechooser.FileView#getName
     */
    @Override
    public String getDescription() {
        return ".zhonghua";
    }
}
