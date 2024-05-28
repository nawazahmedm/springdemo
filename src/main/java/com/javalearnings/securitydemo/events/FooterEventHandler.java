package com.javalearnings.securitydemo.events;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.SneakyThrows;

public class FooterEventHandler implements IEventHandler {
    @SneakyThrows
    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfCanvas canvas = new PdfCanvas(docEvent.getPage());

        int pageNumber = pdfDoc.getPageNumber(docEvent.getPage());
        Paragraph footer = new Paragraph(String.format("Page %d", pageNumber))
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER);

        float x = (pdfDoc.getDefaultPageSize().getWidth() - 60) / 2;
        float y = 30;

        canvas.beginText()
                .setFontAndSize(com.itextpdf.kernel.font.PdfFontFactory.createFont(), 10)
                .moveText(x, y)
                .showText(String.format("Page %d", pageNumber))
                .endText()
                .release();
    }
}
