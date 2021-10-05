package com.example.demospringbootopenpdf;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateFirstPdfWithHeaderAndFooterExample {

    void addFooter(PdfWriter writer, Document document) throws IOException {
        // IOException BaseFont.createFont

        PdfContentByte cb = writer.getDirectContent();
        cb.saveState();

        String text = "Page " + writer.getPageNumber() + " of ";
        BaseFont helv = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
        PdfTemplate tpl = writer.getDirectContent().createTemplate(100, 100);
        tpl.setBoundingBox(new Rectangle(-20, -20, 100, 100));
        float textSize = helv.getWidthPoint(text, 12);
        float textBase = document.bottom() - 20;
        cb.beginText();
        cb.setFontAndSize(helv, 12);
        float adjust = helv.getWidthPoint("0", 12);
        cb.setTextMatrix(document.right() - textSize - adjust, textBase); //show the footer at the right
        cb.showText(text);
        cb.endText();
        cb.addTemplate(tpl, document.right() - adjust, textBase);
        cb.saveState();
        cb.stroke();
        cb.restoreState();
        cb.restoreState();
        cb.sanityCheck();
    }

    @Test
    void test() throws IOException {
        Document doc = new Document(PageSize.A4, 30, 30, 130, 50);
        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("src/test/resources/example.pdf"));

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

        addFooter(writer,doc);

        doc.close();
        writer.close();
    }

}
