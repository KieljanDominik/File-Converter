package org.example;

import nl.siegmann.epublib.domain.*;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class EPUB implements State{
    @Override
    public String FormatToString(String filePath) {
        Elements paragraphs = null;
        StringBuilder result = new StringBuilder();
        try {
            Book book = readEpubFile(filePath);

            String textContent = extractTextFromEpub(book);

            Document document = Jsoup.parse(textContent);

            paragraphs = document.select("p.calibre1");

            for (Element paragraph : paragraphs) {
              //  System.out.println(paragraph.text());
                result.append(paragraph.text());
                result.append(System.lineSeparator());
            }
           // System.out.println(result);
            return  result.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    @Override
    public void StringToFormat(String text,String filePath) {

        Book book = new Book();

        book.getMetadata().addTitle("Przykładowy e-book");

        String xhtmlContent = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head></head><body>"
                + text.replace("\n", "<br/>") + "</body></html>";

        book.addSection("Rozdział 1", new Resource(xhtmlContent.getBytes(), MediatypeService.XHTML));

        nl.siegmann.epublib.epub.EpubWriter epubWriter = new nl.siegmann.epublib.epub.EpubWriter();

        try {

            FileOutputStream epubFile = new FileOutputStream(filePath+"\\ConvertedFile.epub");
            epubWriter.write(book, epubFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static Book readEpubFile(String epubFilePath) throws IOException {
        try (FileInputStream epubFile = new FileInputStream(epubFilePath)) {
            return (new EpubReader()).readEpub(epubFile);


        }
    }

    private static String extractTextFromEpub(Book book) {
        StringBuilder textContent = new StringBuilder();


        book.getContents().forEach(content -> {
            try {

                textContent.append(new String(content.getData(), "UTF-8"));
                textContent.append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return textContent.toString();
    }
    public static String extractChapterText(Book book, int chapterIndex) {
        if (book != null && chapterIndex >= 0 && chapterIndex < book.getContents().size()) {
            Resource chapterResource = book.getContents().get(chapterIndex);
            try {
                return new String(chapterResource.getData(), chapterResource.getInputEncoding());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
