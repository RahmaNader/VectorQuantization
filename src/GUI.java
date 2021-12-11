package src;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI extends Component implements ActionListener {
    public static int vectorHeight;
    public static int vectorWidth;
    public static int codeBookSize;
    public static String path;
    public static String compressPath;
    public static String decompressPath;
    private JTextArea hInput;
    private JTextArea wInput;
    private JTextArea cbInput;
    private JTextField filePath;
    JTextField before ;
    JFrame mainFrame = new JFrame("Vector Quantization");
    JPanel codeInput = new JPanel();
    JPanel buttonsPanel = new JPanel();
    JButton chooseFile = new JButton("choose file");
    JPanel filePanel = new JPanel();
    JPanel sizePanel = new JPanel();
    private JTextField after;

    //choosing desired file
    public String fileChoose() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            return selectedFile.getAbsolutePath();
        } else return null;
    }

    public void startGUI() {
        mainFrame.setSize(450, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(dim.width / 2 - mainFrame.getSize().width / 2, 0);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        codeInput.setLayout(new FlowLayout());

        hInput = new JTextArea(2, 2);
        hInput.setEditable(true);
        codeInput.add(new JLabel("Vector height:"));
        codeInput.add(hInput);

        wInput = new JTextArea(2, 2);
        wInput.setEditable(true);
        codeInput.add(new JLabel("Vector width:"));
        codeInput.add(wInput);

        cbInput = new JTextArea(2, 2);
        cbInput.setEditable(true);
        codeInput.add(new JLabel("Code book size:"));
        codeInput.add(cbInput);

        buttonsPanel.setLayout(new FlowLayout());
        JButton start = new JButton("Start");
        start.addActionListener(this);
        chooseFile.addActionListener(this);
        buttonsPanel.add(start);

        filePath = new JTextField(25);
        filePath.setEditable(false);
        filePanel.setVisible(true);
        filePanel.setLayout(new FlowLayout());
        filePanel.add(chooseFile);
        filePath.setText("No file selected");
        filePanel.add(filePath);

        before = new JTextField(18);
        before.setText("Size before: ");
        before.setEditable(false);
        before.setVisible(true);
        after = new JTextField(18);
        after.setText("Size after: ");
        after.setEditable(false);
        after.setVisible(true);
        sizePanel.setLayout(new FlowLayout());
        sizePanel.add(before);
        sizePanel.add(after);

        mainFrame.add(codeInput, BorderLayout.WEST);
        mainFrame.add(filePanel, BorderLayout.WEST);
        mainFrame.add(sizePanel,BorderLayout.WEST);
        mainFrame.add(buttonsPanel, BorderLayout.WEST);
        mainFrame.setVisible(true);
    }

    private static String getFileSizeKiloBytes(File file) {
        return (double) file.length() / 1024 + "  kb";
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start")) {
            System.out.println("Start");
            vectorHeight = Integer.parseInt(hInput.getText());
            vectorWidth = Integer.parseInt(wInput.getText());
            codeBookSize = Integer.parseInt(cbInput.getText());
            decompressPath = GUI.path + "_" + GUI.vectorWidth + "_" + GUI.vectorHeight + "_" + GUI.codeBookSize+ ".jpg";
            compressPath = GUI.path + "_" + GUI.vectorWidth + "_" + GUI.vectorHeight + "_" + GUI.codeBookSize+ ".txt";
            File f = new File(path);
            try {
                Compress.compress();
                before.setText("Size before: "+getFileSizeKiloBytes(f));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                Decompress.decompress();
                f = new File(decompressPath);
                after.setText("Size after: "+getFileSizeKiloBytes(f));
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getActionCommand().equals("choose file")) {
            path = this.fileChoose();
            filePath.setText(path);
            try {
                displayBefore(path);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void displayBefore(String s) throws IOException {
        JPanel panel = new JPanel();
        BufferedImage image = ImageIO.read(new File(s));
        JLabel label = new JLabel(new ImageIcon(image));
        panel.add(label);
        JFrame frame = new JFrame("Before");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(0, dim.height - frame.getHeight());
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void displayAfter(String s) throws IOException {
        JPanel panel = new JPanel();
        BufferedImage image = ImageIO.read(new File(s));
        JLabel label = new JLabel(new ImageIcon(image));
        panel.add(label);
        JFrame frame = new JFrame("After");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width, dim.height - frame.getHeight());
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}