package com.javalearnings.securitydemo.events;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class FooterEventHandler implements IEventHandler {
    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        PdfCanvas canvas = new PdfCanvas(page);

        int pageNumber = pdfDoc.getPageNumber(page);
        int totalPages = pdfDoc.getNumberOfPages();
        String footertext = "Test footer is an organization " +
                "This is for the demonstration purpose.";
        try {
            PdfFont font = PdfFontFactory.createFont();
            float width = pdfDoc.getDefaultPageSize().getWidth();
            float height = pdfDoc.getDefaultPageSize().getHeight();
            float margin = 30;
            float x = pdfDoc.getDefaultPageSize().getWidth() / 2;

            // Calculate the width of the text
            float textWidth = font.getWidth(footertext, 6);

            // Calculate the x-coordinate to center the text
            float y = (width - textWidth) / 2;

            // Add footer text
            canvas.beginText()
                    .setFontAndSize(font, 6)
                    .moveText(y, margin)
                    .showText(footertext)
                    .endText();

            // Add page number
            float pageTextWidth = font.getWidth(String.format("Page %d of %d", pageNumber, totalPages)) * 0.001f * 10;
            canvas.beginText()
                    .setFontAndSize(font, 10)
                    .moveText(x, 60)//.moveText(width - margin - pageTextWidth, margin)
                    .showText(String.format("Page %d of %d", pageNumber, totalPages))
                    .endText();

            canvas.release();
        } catch (IOException e) {
            log.error("Error in the FooterEventHandler {} : ", e.toString());
        }

    }
}

