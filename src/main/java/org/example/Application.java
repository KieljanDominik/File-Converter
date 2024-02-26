package org.example;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;



public class Application extends JFrame {

    private JButton chooseFileButton;
    private static JLabel selectedFileLabel;
    private JComboBox<String> conversionOptions;
    private JButton confirmConvertion;
    private String absolutePath;
    private State state;

    private static final  String[] conversionTypes = {"PDF", "DOCX", "EPUB"};
    public Application() {
        super("Converter PDF,DOCX,EPUB");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 200);
        setLocationRelativeTo(null);

        chooseFileButton = new JButton("Choose File");
        selectedFileLabel = new JLabel("Selected File: ");

        conversionOptions = new JComboBox<>();
        conversionOptions.setLocation(400,100);
        chooseFileButton.addActionListener(e -> showFileChooser());
        confirmConvertion = new JButton("Convert !");

        conversionOptions.addItem(conversionTypes[0]);
        conversionOptions.addItem(conversionTypes[1]);
        conversionOptions.addItem(conversionTypes[2]);
        JPanel panel = new JPanel();
        panel.add(chooseFileButton);
        panel.add(selectedFileLabel);
        panel.add(new JLabel("Convert to:"));
        panel.add(conversionOptions);
        panel.add(confirmConvertion);
        add(panel);
    }

    private void showFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "PDF, EPUB, DOCX Files", "pdf", "epub", "docx");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileType = getFileType(selectedFile);
            absolutePath = selectedFile.getAbsolutePath();
            if (fileType != null) {
                selectedFileLabel.setText("Selected File: " + selectedFile.getAbsolutePath() +
                        " (Type: " + fileType + ")");
                showConversionOptions(fileType);
            } else {
                selectedFileLabel.setText("Selected File: " + selectedFile.getAbsolutePath() +
                        " (Unknown Type)");
                conversionOptions.setVisible(false);
            }
        }
    }

    private String getFileType(File file) {
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf('.');

        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            String fileExtension = fileName.substring(lastDotIndex + 1).toLowerCase();
            if (fileExtension.equals("pdf") || fileExtension.equals("epub") || fileExtension.equals("docx")) {
                return fileExtension.toUpperCase();
            }
        }

        return null;
    }

    private void showConversionOptions(String fileType) {
        if (fileType.equals("PDF") || fileType.equals("DOCX") || fileType.equals("EPUB")) {
            conversionOptions.setVisible(true);
            confirmConvertion.setVisible(true);
            confirmConvertion.addActionListener(e -> convertFromXtoX());
        } else {
            conversionOptions.setVisible(false);
        }
    }
    private void convertFromXtoX()
    {
    String FileExtension = getFileExtension(absolutePath);
    String tmp = "";
switch (FileExtension)
{
    case "docx":
        state = new DOCX();
        tmp = state.FormatToString(absolutePath);
        break;
    case "pdf":
        state = new PDF();
        tmp = state.FormatToString(absolutePath);
        break;
    case "epub":
        state = new EPUB();
        tmp = state.FormatToString(absolutePath);
        break;

}
String pathToDictionary = getDirectoryPath(absolutePath);
String sourceFormat = (String) conversionOptions.getSelectedItem();
    //    System.out.println(sourceFormat+"@@@@@"+pathToDictionary);
switch (sourceFormat.toLowerCase())
{
    case "docx":
        state = new DOCX();
        state.StringToFormat(tmp,pathToDictionary);
        break;
    case "pdf":
        state = new PDF();
        state.StringToFormat(tmp,pathToDictionary);
        break;
    case "epub":
        state = new EPUB();
        state.StringToFormat(tmp,pathToDictionary);
        break;
}
    }

    private static String getFileExtension(String filePath) {
        try{
            int lastDotIndex = filePath.lastIndexOf('.');
            if (lastDotIndex > 0 && lastDotIndex < filePath.length() - 1) {
                return filePath.substring(lastDotIndex + 1).toLowerCase();
            }
            return "";
        }
        catch (NullPointerException e)
        {
            selectedFileLabel.setText("Wrong path to file!!");
        }
        return "";
    }

    private static String getDirectoryPath(String filePath) {
        int lastSeparatorIndex = filePath.lastIndexOf("\\");
        if (lastSeparatorIndex > 0) {
            return filePath.substring(0, lastSeparatorIndex);
        }
        return "";
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Application().setVisible(true));
    }
}
