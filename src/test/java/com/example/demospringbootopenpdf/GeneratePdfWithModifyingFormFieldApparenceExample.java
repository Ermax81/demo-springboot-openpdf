package com.example.demospringbootopenpdf;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class GeneratePdfWithModifyingFormFieldApparenceExample {

    @Test
    void test() throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/test/resources/example.pdf"));

        document.open();

        document.add(new Paragraph("Hello World"));

        //Annotation example
        BaseFont helv = BaseFont.createFont("Helvetica", "winansi", false);
        PdfContentByte cb = writer.getDirectContent();
        cb.moveTo(0, 0);
        String textString = "Some start text";
        float fontSize = 12;
        Color textColor = new GrayColor(0f);
        PdfFormField field = PdfFormField.createTextField(writer, false, false, 0);
        field.setWidget(new Rectangle(171, 750, 342, 769), PdfAnnotation.HIGHLIGHT_INVERT);
        field.setFlags(PdfAnnotation.FLAGS_PRINT);
        field.setFieldName("ATextField");
        field.setValueAsString(textString);
        field.setDefaultValueAsString(textString);
        field.setBorderStyle(new PdfBorderDictionary(2, PdfBorderDictionary.STYLE_SOLID));
        field.setPage();
        PdfAppearance tp = cb.createAppearance(171, 19);
        PdfAppearance da = (PdfAppearance) tp.getDuplicate();
        da.setFontAndSize(helv, fontSize);
        da.setColorFill(textColor);
        field.setDefaultAppearanceString(da);
        tp.beginVariableText();
        tp.saveState();
        tp.rectangle(2, 2, 167, 15);
        tp.clip();
        tp.newPath();
        tp.beginText();
        tp.setFontAndSize(helv, fontSize);
        tp.setColorFill(textColor);
        tp.setTextMatrix(4, 5);
        tp.showText(textString);
        tp.endText();
        tp.restoreState();
        tp.endVariableText();
        field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, tp);
        writer.addAnnotation(field);

        document.close();
        writer.close();
    }
}
