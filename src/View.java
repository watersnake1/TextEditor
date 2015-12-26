
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Created by cmurray17 on 10/2/15.
 *
 */

public class View implements Globals {

    private JFrame window;
    private JPanel panel;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu codeMenu;
    private JMenuItem save;
    private JMenuItem clear;
    private JMenuItem newFile;
    private JMenuItem open;
    private JMenuItem saveAs;
    private JMenuItem undo;
    private JMenuItem createSource;
    private JTextArea textArea;
    private String recievedText;
    private String fileName;
    private PrintWriter writer;
    private String autoSave;
    private File selectedFile;
    private File fileToWrite;

    public View() {
        window = new JFrame("Text Edit");
        panel = new JPanel();
        menuBar = new JMenuBar();
        fileMenu = new JMenu("file");
        editMenu = new JMenu("edit");
        codeMenu = new JMenu("code");
        save = new JMenuItem("save");
        newFile = new JMenuItem("new");
        open = new JMenuItem("open");
        saveAs = new JMenuItem("Save As");
        clear = new JMenuItem("clear");
        undo = new JMenuItem("undo");
        createSource = new JMenuItem("make '.java' file");
        textArea = new JTextArea();


    }

    public void createAndShowGUI()  {
        if(showDialogue()) {
            window.setTitle("Text Edit ~ " + fileName);
            window.setVisible(true);
            window.setPreferredSize(new Dimension(550, 500));
            window.setJMenuBar(menuBar);
            textArea.setPreferredSize(new Dimension(500, 500));
            window.add(panel);
            panel.add(textArea);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            menuBar.add(fileMenu);
            menuBar.add(editMenu);
            menuBar.add(codeMenu);
            fileMenu.add(save);
            fileMenu.add(clear);
            fileMenu.add(newFile);
            fileMenu.add(open);
            fileMenu.add(saveAs);
            editMenu.add(undo);
            codeMenu.add(createSource);
            window.pack();
        }
        else {
            window.dispose();
        }

    }

    /**
     * create a popup dialogue for the user to specify the name and extension of a new file
     */
    public boolean showDialogue()  {
        this.fileName = JOptionPane.showInputDialog(window, "Enter File Name and Extension");
        try {
            if (fileName != null) {
                fileToWrite = new File(System.getProperty("user.home"), fileName);
                writer = new PrintWriter(fileToWrite);
                return true;
            } else {
                JOptionPane.showMessageDialog(window, "No file name provided");
                return false;
            }
        }
        catch (FileNotFoundException error){
            JOptionPane.showMessageDialog(window, "No file name provided");
            error.printStackTrace();
            //window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            window.dispose();
        }
        return false;

    }

    public void getSelectedFileText() {

    }

    //all the action listeners. java 1-6 compatible

    public void buttonHandler() {
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                recievedText =  textArea.getText();
                writer.println(recievedText);
                writer.close();

            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoSave = textArea.getText();
                textArea.setText(" ");
                panel.updateUI();
            }
        });

        newFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                autoSave = textArea.getText();
                textArea.setText("");
                panel.updateUI();
                createAndShowGUI();
            }
        });

        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(autoSave);
                panel.updateUI();
            }
        });

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //JOptionPane.showMessageDialog(window, "open a file");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(window);
                if(result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    fileName = selectedFile.getName();
                    createAndShowGUI();
                }
            }
        });

        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String saveText = textArea.getText();
                showDialogue();
                writer.println(saveText);
                writer.close();

            }
        });

        createSource.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recievedText =  textArea.getText();
                writer.println(recievedText);
                writer.close();
                String nameString = fileToWrite.getName();
                String noFileType = nameString.substring(0, nameString.length() - 4);
                String newFileName = noFileType + ".java";
                File sourceFile = new File(System.getProperty("user.home"), newFileName);
                fileToWrite.renameTo(sourceFile);
            }
        });
    }
}
