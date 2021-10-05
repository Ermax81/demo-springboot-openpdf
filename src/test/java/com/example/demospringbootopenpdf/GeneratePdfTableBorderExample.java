package com.example.demospringbootopenpdf;

import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.FileOutputStream;

// Source: https://github.com/LibrePDF/OpenPDF/blob/master/pdf-toolbox/src/test/java/com/lowagie/examples/objects/tables/TableBorders.java
public class GeneratePdfTableBorderExample {

    @Test
    void test() throws Exception {
        Document document = new Document(PageSize.A4, 30, 30, 130, 50);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/test/resources/example.pdf"));
        writer.setPageEvent(new PDFGenerator() );
        document.open();

        // page 1
        Font tableFont = FontFactory.getFont("Helvetica", 8, Font.BOLD, Color.BLACK);
        float padding = 0f;
        Rectangle border = new Rectangle(0f, 0f);
        border.setBorderWidthLeft(6f);
        border.setBorderWidthBottom(5f);
        border.setBorderWidthRight(4f);
        border.setBorderWidthTop(2f);
        border.setBorderColorLeft(Color.RED);
        border.setBorderColorBottom(Color.ORANGE);
        border.setBorderColorRight(Color.YELLOW);
        border.setBorderColorTop(Color.GREEN);
        makeTestPage(tableFont, border, writer, document, padding, true, true);
        Font font = FontFactory.getFont("Helvetica", 10);
        Paragraph p;
        p = new Paragraph("\nVarious border widths and colors\nuseAscender=true, useDescender=true", font);
        document.add(p);

        document.newPage();

        // page 2
        padding = 2f;
        border = new Rectangle(0f, 0f);
        border.setBorderWidthLeft(1f);
        border.setBorderWidthBottom(2f);
        border.setBorderWidthRight(1f);
        border.setBorderWidthTop(2f);
        border.setBorderColor(Color.BLACK);
        makeTestPage(tableFont, border, writer, document, padding, true, true);
        p = new Paragraph("More typical use - padding of 2\nuseBorderPadding=true, useAscender=true, useDescender=true", font);
        document.add(p);

        document.newPage();

        // page 3
        padding = 0f;
        border = new Rectangle(0f, 0f);
        border.setBorderWidthLeft(1f);
        border.setBorderWidthBottom(2f);
        border.setBorderWidthRight(1f);
        border.setBorderWidthTop(2f);
        border.setBorderColor(Color.BLACK);
        makeTestPage(tableFont, border, writer, document, padding, false, true);
        p = new Paragraph("\nuseBorderPadding=true, useAscender=false, useDescender=true", font);
        document.add(p);

        document.newPage();

        // page 4
        padding = 0f;
        border = new Rectangle(0f, 0f);
        border.setBorderWidthLeft(1f);
        border.setBorderWidthBottom(2f);
        border.setBorderWidthRight(1f);
        border.setBorderWidthTop(2f);
        border.setBorderColor(Color.BLACK);
        makeTestPage(tableFont, border, writer, document, padding, false, false);
        p = new Paragraph("\nuseBorderPadding=true, useAscender=false, useDescender=false", font);
        document.add(p);

        document.newPage();

        // page 5
        padding = 0f;
        border = new Rectangle(0f, 0f);
        border.setBorderWidthLeft(1f);
        border.setBorderWidthBottom(2f);
        border.setBorderWidthRight(1f);
        border.setBorderWidthTop(2f);
        border.setBorderColor(Color.BLACK);
        makeTestPage(tableFont, border, writer, document, padding, true, false);
        p = new Paragraph("\nuseBorderPadding=true, useAscender=true, useDescender=false", font);
        document.add(p);

        document.close();
        writer.close();
    }

    private void makeTestPage(Font tableFont, Rectangle borders, PdfWriter writer, Document document,
                                     float padding, boolean ascender, boolean descender) throws DocumentException {
        document.newPage();
        PdfPTable table = null;
        table = new PdfPTable(4);
        table.setWidthPercentage(100f);

        float leading = tableFont.getSize() * 1.2f;

        table.addCell(makeCell("1-Top", Element.ALIGN_TOP, Element.ALIGN_LEFT, tableFont, leading, padding, borders, ascender, descender));
        table.addCell(makeCell("2-Middle", Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, tableFont, leading, padding, borders, ascender, descender));
        table.addCell(makeCell("3-Bottom", Element.ALIGN_BOTTOM, Element.ALIGN_LEFT, tableFont, leading, padding, borders, ascender, descender));
        table.addCell(makeCell("4-Has a y", Element.ALIGN_TOP, Element.ALIGN_LEFT, tableFont, leading, padding, borders, ascender, descender));

        table.addCell(makeCell("5-Abcdy", Element.ALIGN_TOP, Element.ALIGN_LEFT, tableFont, leading, padding, borders, ascender, descender));
        table.addCell(makeCell("6-Abcdy", Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, tableFont, leading, padding, borders, ascender, descender));
        table.addCell(makeCell("7-Abcdy", Element.ALIGN_BOTTOM, Element.ALIGN_LEFT, tableFont, leading, padding, borders, ascender, descender));
        table.addCell(makeCell("8-This\nis\na little\ntaller", Element.ALIGN_TOP, Element.ALIGN_LEFT, tableFont, leading, padding, borders, ascender, descender));
        document.add(table);
    }

    private PdfPCell makeCell(String text, int vAlignment, int hAlignment, Font font, float leading, float padding, Rectangle borders, boolean ascender, boolean descender) {
        Paragraph p = new Paragraph(text, font);
        p.setLeading(leading);

        PdfPCell cell = new PdfPCell(p);
        cell.setLeading(leading, 0);
        cell.setVerticalAlignment(vAlignment);
        cell.setHorizontalAlignment(hAlignment);
        cell.cloneNonPositionParameters(borders);
        cell.setUseAscender(ascender);
        cell.setUseDescender(descender);
        cell.setUseBorderPadding(true);
        cell.setPadding(padding);
        return cell;
    }
}
