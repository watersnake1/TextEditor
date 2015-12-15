
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
    private JMenuItem save;
    private JMenuItem clear;
    private JMenuItem newFile;
    private JMenuItem open;
    private JMenuItem undo;
    private JTextArea textArea;
    private String recievedText;
    private String fileName;
    private PrintWriter writer;
    private String autoSave;
    private File selectedFile;

    public View() {
        window = new JFrame("Text Edit");
        panel = new JPanel();
        menuBar = new JMenuBar();
        fileMenu = new JMenu("file");
        editMenu = new JMenu("edit");
        save = new JMenuItem("save");
        newFile = new JMenuItem("new");
        open = new JMenuItem("open");
        clear = new JMenuItem("clear");
        undo = new JMenuItem("undo");
        textArea = new JTextArea();


    }

    public void createAndShowGUI()    {
        try {
            showDialogue();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        fileMenu.add(save);
        fileMenu.add(clear);
        fileMenu.add(newFile);
        fileMenu.add(open);
        editMenu.add(undo);
        window.pack();


    }


    public void showDialogue() throws  UnsupportedEncodingException {
        this.fileName = JOptionPane.showInputDialog(window, "Enter File Name and Extension");
        try {
            if (fileName != null) {
                writer = new PrintWriter(fileName, "UTF-8");
            } else {
                JOptionPane.showMessageDialog(window, "No file name provided");
            }
        }
        catch (FileNotFoundException error){
            JOptionPane.showMessageDialog(window, "No file name provided");
            error.printStackTrace();
            window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void getSelectedFileText() {

    }

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
    }
}
