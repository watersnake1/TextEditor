
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.net.*;


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
    private JMenu changesMenu;
    private JMenuItem save;
    private JMenuItem clear;
    private JMenuItem newFile;
    private JMenuItem open;
    private JMenuItem saveAs;
    private JMenuItem undo;
    private JMenuItem createSource;
    private JMenuItem about;
    private JMenuItem changelog;
    private JTextArea textArea;
    private String recievedText;
    private String fileName;
    private PrintWriter writer;
    private String autoSave;
    private File selectedFile;
    private File fileToWrite;
    private String aboutText;

    
    /**
    * create the View object and initialize everything that is needed
    */
    public View() {
        window = new JFrame("Text Edit");
        panel = new JPanel();
        menuBar = new JMenuBar();
        fileMenu = new JMenu("file");
        editMenu = new JMenu("edit");
        codeMenu = new JMenu("code");
        changesMenu = new JMenu("changes");
        save = new JMenuItem("save");
        newFile = new JMenuItem("new");
        open = new JMenuItem("open");
        saveAs = new JMenuItem("Save As");
        clear = new JMenuItem("clear");
        undo = new JMenuItem("undo");
        about = new JMenuItem("about");
        changelog = new JMenuItem("changelog");
        createSource = new JMenuItem("make '.java' file");
        aboutText = "A java text editor created by Christian Murray";
        textArea = new JTextArea();


    }

    /**
     * if the dialogue method is ready then proceed with the process of 
     * creating the window that will take in the text input
     */
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
            menuBar.add(changesMenu);
            fileMenu.add(save);
            fileMenu.add(clear);
            fileMenu.add(newFile);
            fileMenu.add(open);
            fileMenu.add(saveAs);
            fileMenu.add(about);
            editMenu.add(undo);
            codeMenu.add(createSource);
            changesMenu.add(changelog);
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
            //error.printStackTrace();
            //window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            window.dispose();
        }
        return false;

    }

    /**
     * reads the text file line by line and then prints each line to the text field
     * currently does not read the first line and inserts 'null' to the end of the arraylist
     */
    public void getSelectedFileText() {
        try {
            String line = "";
            ArrayList<String> fullText = new ArrayList<String>();
            FileReader reader = new FileReader(selectedFile);
            BufferedReader opener = new BufferedReader(reader);
            line = opener.readLine();
            while(line != null) {
                line = opener.readLine();
                fullText.add(line);
                //System.out.println(line);
            }
            opener.close();
            //System.out.println(fullText);
            for(String s: fullText) {
                textArea.setText(textArea.getText() + s + "\n");
                System.out.println(s);
            }
            textArea.repaint();
            panel.updateUI();
        }
        catch(java.io.FileNotFoundException error) {
            System.out.println("there was a file not found exception. Here's the details:");
            error.printStackTrace();
        }
        catch(java.io.IOException error1) {
            error1.printStackTrace();
        }
    }

    /**
     * the method that is called when the open button is clicked
     */
    public void recreateAndShowGUI() {
        window.setTitle("Text Edit ~ " + fileName);
        getSelectedFileText();
    }

    public String getAboutText() {
        return aboutText;
    }
    //all the action listeners. java 1-6 compatible, no lamda //expressions

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
                    recreateAndShowGUI();
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

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame aboutFrame = new JFrame("About TextEditor");
                JPanel aboutPanel = new JPanel();
                JLabel aboutLabel = new JLabel(getAboutText());
                aboutFrame.setVisible(true);
                aboutFrame.setPreferredSize(new Dimension(400, 100));
                aboutFrame.setResizable(false);
                aboutFrame.add(aboutPanel);
                aboutPanel.add(aboutLabel);
                aboutFrame.pack();

            }
        });

        //this doesnt work, trying to use update.txt as an in app changelog
        changelog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                URL fileURL = null;
                try {
                    fileURL = new URL("../update/");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                String directory = fileURL.getPath();
                try {
                    directory = URLDecoder.decode(directory, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                File update = new File(directory);

                fileName = update.getName();
                recreateAndShowGUI();
            }
        });
    }
}
