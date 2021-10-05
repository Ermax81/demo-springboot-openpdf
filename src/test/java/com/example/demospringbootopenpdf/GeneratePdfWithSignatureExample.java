package com.example.demospringbootopenpdf;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfAcroForm;
import com.lowagie.text.pdf.PdfWriter;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;

public class GeneratePdfWithSignatureExample {

    @Test
    void test() throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/test/resources/example.pdf"));

        document.open();

        PdfAcroForm acroForm = writer.getAcroForm();
        document.add(new Paragraph("Hello World"));

        acroForm.addSignature("mysig", 73, 705, 149, 759);

        document.close();
        writer.close();
    }

    }
