package com.example.demospringbootopenpdf;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.TextField;
import com.lowagie.text.pdf.*;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

// WORK IN PROGRESS...
public class GeneratePdfWithFormFieldsExample {

    @Test
    void test() throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/test/resources/example.pdf"));

        document.open();

        PdfAcroForm acroForm = writer.getAcroForm();
        document.add(new Paragraph("Hello World"));

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String coffeeImagePath = classLoader.getResource("coffee-icon.png").getPath();
        String reindeerImagePath = classLoader.getResource("reindeer-deer-icon.png").getPath();

        Image reindeerImage = Image.getInstance(reindeerImagePath);
        reindeerImage.scalePercent(50);
        float height = reindeerImage.getScaledHeight();
        float width = reindeerImage.getScaledWidth();
        Rectangle box = new Rectangle(height, width);

        Rectangle rect = new Rectangle(243, 153);
        rect.setBackgroundColor(new Color(0xFF, 0xFF, 0xCC));

        PdfPTable table = new PdfPTable(2);
        PdfPCell cell = new PdfPCell();
        cell.setFixedHeight(height);
        Paragraph paragraph = new Paragraph("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                " ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                " ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat" +
                " non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        cell.addElement(paragraph);
        table.addCell(cell);

        // new Rectangle(40,120)
        //PushbuttonField imageField = new PushbuttonField(writer, box, "image");
        //imageField.setImage(reindeerImage);
        ////imageField.setText("Exemple...");
        //PdfFormField ff = imageField.getField();
        //cell.setCellEvent(new ImageFormField(ff));

        //PdfPCell cell = table.getDefaultCell();
        //cell.setFixedHeight(height);
        //defaultCell.get

        //new Rectangle(100, 100, 200, 200)
        PushbuttonField bt = new PushbuttonField(writer, new Rectangle(200, 200), "Button1");
        //bt.setText("My Caption");
        //bt.setFontSize(0);
        bt.setImage(reindeerImage);

        //bt.setLayout(PushbuttonField.LAYOUT_ICON_TOP_LABEL_BOTTOM);
        bt.setLayout(PushbuttonField.LAYOUT_ICON_ONLY);
        //bt.setScaleIcon(PushbuttonField.SCALE_ICON_IS_TOO_BIG);
        //bt.setProportionalIcon(true);

        bt.setBackgroundColor(Color.cyan);
        bt.setBorderStyle(PdfBorderDictionary.STYLE_SOLID);
        bt.setBorderColor(Color.red);
        bt.setBorderWidth(3);
        PdfFormField ff = bt.getField();

        cell = new PdfPCell();
        cell.setFixedHeight(height);
        cell.setCellEvent(new FormField(ff));

        table.addCell(cell);

        cell = new PdfPCell();
        cell.setColspan(2);
        TextField text = new TextField(writer, new Rectangle(0, 0), "name");
        text.setText("NameByDefaut");
        text.setOptions(TextField.MULTILINE);
        text.setFontSize(8);
        PdfFormField name = text.getTextField();
        cell.setCellEvent(new FormField(name));
        cell.setFixedHeight(60);
        table.addCell(cell);

        document.add(table);

        writer.addAnnotation(ff);
        writer.addAnnotation(name);

        document.close();
        writer.close();

        String pdfInPath = classLoader.getResource("example.pdf").getPath();
        PdfReader reader = new PdfReader(pdfInPath);
        PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream("src/test/resources/modified-example.pdf"));
        AcroFields form1 = pdfStamper.getAcroFields();
        PushbuttonField button = form1.getNewPushbuttonFromField("Button1");
        Image coffeeImage = Image.getInstance(coffeeImagePath);
        button.setImage(coffeeImage);
        button.setText("Modified text");
        PdfFormField buttonFormField = button.getField();
        form1.replacePushbuttonField("Button1", buttonFormField);

        form1.setField("name", "Modified Name");

        pdfStamper.close();


    }

}
