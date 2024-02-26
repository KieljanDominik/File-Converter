package org.example;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class DOCX implements State {

    @Override
    public String FormatToString(String filePath) {
        StringBuilder textContent = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            XWPFDocument document = new XWPFDocument(fis);

            textContent = new StringBuilder();

            List<XWPFParagraph> paragraphs = document.getParagraphs();

            for (XWPFParagraph paragraph : paragraphs) {
                textContent.append(paragraph.getText()).append("\n");
            }
          //  System.out.println(textContent.toString());

            fis.close();

            return textContent.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return textContent.toString();
    }

    @Override
    public void StringToFormat(String text,String filePath) {

        try {
            XWPFDocument document = new XWPFDocument();

            String[] lines = text.split("\n");

            for (String line : lines) {
                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText(line);
            }
            FileOutputStream fos = new FileOutputStream(filePath+"\\ConvertedFile.docx");
            document.write(fos);
            fos.close();

          //  System.out.println("Dokument Word został utworzony pomyślnie.");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
