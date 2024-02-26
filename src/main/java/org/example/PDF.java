package org.example;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class PDF implements State{
    @Override
    public String FormatToString(String filePath) {
        String textContent = "";
        try {
            textContent = extractTextFromPdf(filePath);
            System.out.println(textContent);
            return  textContent;

        } catch (IOException e) {
            e.printStackTrace();
        }


        return textContent;
    }

    @Override
    public void StringToFormat(String text,String filePath) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath+"\\ConvertedFile.pdf"));

            document.open();

            document.add(new Paragraph(text));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (com.itextpdf.text.DocumentException e) {
            e.printStackTrace();

        } finally {
            document.close();
        }

    }
    private static String extractTextFromPdf(String pdfFilePath) throws IOException {
        try (PDDocument document = Loader.loadPDF(new File(pdfFilePath))) {

            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            return pdfTextStripper.getText(document);
        }
    }
}
