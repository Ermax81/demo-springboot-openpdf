package com.example.demospringbootopenpdf;

import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import lombok.Data;

import java.awt.*;

@Data
public class PDFGenerator extends PdfPageEventHelper {
    /**
     * An Image that goes in the header.
     */
    public Image headerImage;
    /**
     * The headertable.
     */
    public PdfPTable table;
    /**
     * The Graphic state
     */
    public PdfGState gstate;
    /**
     * A template that will hold the total number of pages.
     */
    public PdfTemplate tpl;
    /**
     * The font that will be used.
     */
    public BaseFont helv;
    private int footerFontSize = 10;

    /**
     * @see PdfPageEventHelper#onOpenDocument(PdfWriter, Document)
     */
    public void onOpenDocument(PdfWriter writer, Document document) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = classLoader.getResource("coffee-icon.png").getPath();
            Image headerImage = Image.getInstance(path);

            // initialization of the header table
            headerImage.scalePercent(50);
            table = new PdfPTable(1);
            table.setSpacingBefore(0);
            table.setSpacingAfter(0);

            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            table.setSkipFirstHeader(true);
            table.addCell(new Phrase(new Chunk(headerImage, 0, 0))); //offsetX=-40
            // initialization of the Graphic State
            gstate = new PdfGState();
            // initialization of the template
            tpl = writer.getDirectContent().createTemplate(70, 70);
            tpl.setBoundingBox(new Rectangle(0, 0, 100, 100));

            // TODO Mettre en place l'encodage UTF-8 (ajouter la librairie)
            // initialization of the font
            helv = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    /**
     * @see PdfPageEventHelper#onEndPage(PdfWriter, Document)
     */
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        cb.saveState();
        // write the headertable
        table.setTotalWidth(document.right() - document.left());
        table.writeSelectedRows(0, -1, document.left(), document.getPageSize().getHeight() - 20, cb);

        // compose the footer
        String text = "Page " + writer.getPageNumber() + "/";
        float textSize = helv.getWidthPoint(text, footerFontSize);
        float textBase = document.bottom() - 20;
        cb.beginText();
        cb.setFontAndSize(helv, footerFontSize);
        // for odd pagenumbers, show the footer at the left
/*        if ((writer.getPageNumber() & 1) == 1) {
            cb.setTextMatrix(document.left(), textBase);
            cb.showText(text);
            cb.endText();
            cb.addTemplate(tpl, document.left() + textSize, textBase);
        }
        // for even numbers, show the footer at the right
        else {*/
        float adjust = helv.getWidthPoint("0", footerFontSize);
        cb.setTextMatrix(document.right() - textSize - adjust, textBase);
        cb.showText(text);
        cb.endText();
        cb.addTemplate(tpl, document.right() - adjust, textBase);
        /*}*/

        // Footer Orange Company
        String companyLine1 = "Société Anonyme au capital de xxxx € - adresse ligne1,";
        String companyLine2 = "YYYYY Ville";
        String companyLine3 = "Version xx/yy/zzzz";
        cb.beginText();
        cb.setTextMatrix(document.left() + textSize + adjust, textBase);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, companyLine1, 20 + adjust, 20 + 10 + textSize, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, companyLine2, 20 + adjust, 20 + 10 + textSize / 2, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, companyLine3, 20 + adjust, 20 + textSize / 3, 0);
        cb.endText();

        cb.saveState();

        // draw a Rectangle around the page
//        cb.setColorStroke(Color.orange);
//        cb.setLineWidth(2);
//        cb.rectangle(20, 20, document.getPageSize().getWidth() - 40, document.getPageSize().getHeight() - 40);
        cb.stroke();
        cb.restoreState();

//        // starting on page 3, a watermark with an Image that is made transparent
//        if (writer.getPageNumber() >= 3) {
//            cb.setGState(gstate);
//            cb.setColorFill(Color.red);
//            cb.beginText();
//            cb.setFontAndSize(helv, 48);
//            cb.endText();
//        }

        cb.restoreState();
        cb.sanityCheck();
    }

    /**
     * @see PdfPageEventHelper#onStartPage(PdfWriter, Document)
     */
    public void onStartPage(PdfWriter writer, Document document) {
        if (writer.getPageNumber() < 3) {
            PdfContentByte cb = writer.getDirectContentUnder();
            cb.saveState();
            cb.setColorFill(Color.pink);
            cb.beginText();
            cb.setFontAndSize(helv, 48);
            //cb.showText("TEMPLATE");  //Permet d'avoir un texte en bas de page
            cb.endText();
            cb.restoreState();
        }
    }

    /**
     * @see PdfPageEventHelper#onCloseDocument(PdfWriter, Document)
     */
    public void onCloseDocument(PdfWriter writer, Document document) {
        tpl.beginText();
        tpl.setFontAndSize(helv, footerFontSize);
        tpl.setTextMatrix(0, 0);
        // Footer NbTotalPage
        tpl.showText(Integer.toString(writer.getPageNumber() - 1));
        tpl.endText();
        tpl.sanityCheck();
    }
}
