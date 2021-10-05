package com.example.demospringbootopenpdf;

import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;

// Source: https://github.com/LibrePDF/OpenPDF/blob/master/pdf-toolbox/src/test/java/com/lowagie/examples/forms/create/StudentCardForm.java
public class FormField implements PdfPCellEvent {

    /** the writer with the acroform */
    private PdfFormField field;

    public FormField(PdfFormField field) {
        this.field = field;
    }

    @Override
    public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
        field.setWidget(position, null);
    }
}
