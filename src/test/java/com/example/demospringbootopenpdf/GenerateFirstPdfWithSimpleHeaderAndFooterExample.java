package com.example.demospringbootopenpdf;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateFirstPdfWithSimpleHeaderAndFooterExample {

    @Test
    void test() throws IOException {
        Document doc = new Document(PageSize.A4, 30, 30, 130, 50);
        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("src/test/resources/example.pdf"));

        Font font = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.BLACK);

        // Uniquement pour footer simple
        // headers and footers must be added before the document is opened
        HeaderFooter footer = new HeaderFooter(
                new Phrase("This is page: ", font), true);
        footer.setBorder(Rectangle.NO_BORDER);
        footer.setAlignment(Element.ALIGN_CENTER);
        doc.setFooter(footer);

        // Uniquement pour header simple
        HeaderFooter header = new HeaderFooter(
                new Phrase("This is a header without a page number", font), false);
        header.setAlignment(Element.ALIGN_CENTER);
        doc.setHeader(header);

        doc.open();

        String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                " ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                " ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat" +
                " non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

        Paragraph paragraph = new Paragraph(text);
        doc.add(paragraph);

        doc.close();
        writer.close();
    }
}
