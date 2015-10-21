package Model;


import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
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
                } else if (!new File(chooser.getSelectedFile().getAbsolutePath()).isDirectory()) {
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

    public void fixIt(File file[]) {
        try {
            BufferedReader br;
            BufferedWriter bw;
            for (int i = 0; i < file.length; i++) {
                br = new BufferedReader(new FileReader(file[i]));
                
                String line = br.readLine();
                String text="";
                while (line != null) {
                    text+=line;
                    text+="\n";
                    line = br.readLine();
                }
                text=removeFromFile(text); 
               text=text.replace(text.substring(text.indexOf("<style"), text.indexOf("</style>") + 8),fixStyles(text));
                br.close();
                bw = new BufferedWriter(new FileWriter(file[i]));
                bw.write(text);
                bw.close();
                
            }

        } catch (Exception e) {

        }
    }
/*
   // BACK UP
    text=text.replaceAll("<br />", "");
    text=text.replaceAll("text-align:center;", "");
    text=text.replaceAll("<span class=\"st3\">&nbsp;</span>", "");
    text=text.replaceAll("line-height:normal;", "");
    text=text.replaceAll("<div style=\"margin:0pt 0pt 0pt 50\\.4pt;text-indent:-21\\.6pt;\"><span class=\"st15\">&nbsp;</span></div>", "");
    text=text.replaceAll("margin-left:25\\.2pt;text-indent:-25\\.2pt;", "");
    text=text.replaceAll("<div style=\"margin:0pt 0pt 0pt 50\\.4pt;text-indent:-21\\.6pt;line-height:normal;\"><span class=\"st15\">&nbsp;</span></div>", "");
    text=text.replaceAll("<div style=\"margin:0pt 0pt 0pt 25\\.2pt;text-indent:-25\\.2pt;line-height:normal;\"><span class=\"st3\">&nbsp;</span></div>", "");
    text=text.replaceAll("<div style=\"text-align:center;\">The trial version of &laquo;RTF to HTML \\.Net&raquo; can convert up to 10000 symbols\\.<br><a href=\"http://www\\.sautinsoft\\.com/convert-rtf-to-html/order\\.php\">Get the full featured version!</a></div>", "");

    */
    public String removeFromFile(String text) {        
        text=text.replaceAll("<br />", "");
        text=text.replaceAll("text-align:center;", "");
        text=text.replaceAll("<span class=\"st3\">&nbsp;</span>", "");
        text=text.replaceAll("line-height:normal;", "");
        text=text.replaceAll("margin-left:25\\.2pt;text-indent:-25\\.2pt;", "");
        text=text.replaceAll("<div style=\"margin:0pt 0pt 0pt 50\\.4pt;text-indent:-21\\.6pt;\"><span class=\"st15\">&nbsp;</span></div>", "");
        text=text.replaceAll("<div style=\"margin:0pt 0pt 0pt 25\\.2pt;text-indent:-25\\.2pt;\"></div>", "");
        text=text.replaceAll("</div><div style=\"\">The trial version of &laquo;RTF to HTML \\.Net&raquo; can convert up to 10000 symbols\\.<br><a href=\"http://www\\.sautinsoft\\.com/convert-rtf-to-html/order\\.php\">Get the full featured version!</a></div>", "");
        return text;
    }
    public String fixStyles(String text) {
        
        text = text.substring(text.indexOf("<style"), text.indexOf("</style>")+8);

        String splits[] = text.split("\\.");
        System.out.println(Arrays.toString(splits));
        String newStyle;
        
        for(int i=1;i<splits.length;i++) {
            newStyle="{ font-family:Calibri;font-size:11pt;color:#000000;";
            if(splits[i].contains("li:before")) {
                newStyle+="content: \"■\";";
            }
            else if(splits[i].contains("st1{") || splits[i].contains("st2{")) {
                newStyle+="font-weight:bold;";
            }
            newStyle+="}";
            
            int start=splits[i].indexOf("{");
            int end=splits[i].indexOf("}");
            splits[i]="." + splits[i].replace(splits[i].substring(start, end + 1), newStyle);
            
                
        }
        
        text=Arrays.toString(splits);   
        text=text.replaceAll(",", "");
        text=text.replaceAll("\\[", "");
        text=text.replaceAll("\\]", "");
        return text;
    }
    public int counter(String text,char c) {
        int counter=0;
        char array[]=text.toCharArray();
        for(int i=0;i<array.length;i++){
            if(array[i] == c) {
                counter++;
            }
        }
        return counter;
    }
}
