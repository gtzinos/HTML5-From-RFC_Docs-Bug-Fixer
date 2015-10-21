
import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author George
 */
public class startFormView_Model {

    public void NonSelectedConvertionType(Component a, Component b) {
        a.setEnabled(false);
        b.setEnabled(false);
    }

    public void SelectedConvertionType(Component a, Component b) {
        a.setEnabled(true);
        b.setEnabled(true);
    }

    public String selectionFolderPath() {
        JFileChooser chooser = new JFileChooser();
        //FileNameExtensionFilter filter = new FileNameExtensionFilter("Folder", "html", "html");
        //chooser.setFileFilter(filter);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                if (!new File(chooser.getSelectedFile().getAbsolutePath()).exists()) {
                    JOptionPane.showMessageDialog(null, "<ERROR>Your path doesn't exist");
                    return "";
                }
                else if (!new File(chooser.getSelectedFile().getAbsolutePath()).isDirectory()) {
                    JOptionPane.showMessageDialog(null, "<ERROR>Please select a folder not a file");
                    return "";
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "<ERROR>Something going wrong !!!");
                    
                return "";
            }
            return chooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }

    public String selectionFilePath() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Html files", "html", "html");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                if (!new File(chooser.getSelectedFile().getAbsolutePath()).exists()) {
                    JOptionPane.showMessageDialog(null, "<ERROR>Your file doesn't exist");
                    return "";
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "<ERROR>Something going wrong !!!");
                    
                return "";
            }
            return chooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }
}
