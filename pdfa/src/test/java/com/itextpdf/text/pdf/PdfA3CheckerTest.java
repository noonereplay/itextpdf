package com.itextpdf.text.pdf;

import com.itextpdf.text.*;
import junit.framework.Assert;
import org.junit.Test;

import java.io.*;

public class PdfA3CheckerTest {

    @Test
    public void fileSpecCheckTest1() throws DocumentException, IOException {
        Document document = new Document();
        PdfAWriter writer = PdfAWriter.getInstance(document, new FileOutputStream("./target/fileSpecCheckTest1.pdf"), PdfAConformanceLevel.PDF_A_3B);
        writer.createXmpMetadata();
        document.open();

        Font font = FontFactory.getFont("./src/test/resources/com/itextpdf/text/pdf/FreeMonoBold.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED, 12);
        document.add(new Paragraph("Hello World", font));
        ICC_Profile icc = ICC_Profile.getInstance(new FileInputStream("./src/test/resources/com/itextpdf/text/pdf/sRGB Color Space Profile.icm"));
        writer.setOutputIntents("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", icc);

        ByteArrayOutputStream txt = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(txt);
        out.print("<foo><foo2>Hello world</foo2></foo>");
        out.close();
        writer.addFileAttachment("foo file", txt.toByteArray(), "foo.xml", "foo.xml", "application/xml",
                AFRelationshipValue.Source);

        document.close();
    }

    @Test
    public void fileSpecCheckTest2() throws DocumentException, IOException {
        Document document = new Document();
        PdfAWriter writer = PdfAWriter.getInstance(document, new FileOutputStream("./target/fileSpecCheckTest2.pdf"), PdfAConformanceLevel.PDF_A_3B);
        writer.createXmpMetadata();
        document.open();

        Font font = FontFactory.getFont("./src/test/resources/com/itextpdf/text/pdf/FreeMonoBold.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED, 12);
        document.add(new Paragraph("Hello World", font));
        ICC_Profile icc = ICC_Profile.getInstance(new FileInputStream("./src/test/resources/com/itextpdf/text/pdf/sRGB Color Space Profile.icm"));
        writer.setOutputIntents("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", icc);

        ByteArrayOutputStream txt = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(txt);
        out.print("<foo><foo2>Hello world</foo2></foo>");
        out.close();

        PdfFileSpecification fs = PdfFileSpecification.fileEmbedded(writer, null, "foo.xml", txt.toByteArray());
        fs.put(PdfName.AFRELATIONSHIP, AFRelationshipValue.Unspecified);

        writer.addFileAttachment(fs);

        document.close();
    }

    @Test
    public void fileSpecCheckTest3() throws DocumentException, IOException {
        Document document = new Document();
        PdfAWriter writer = PdfAWriter.getInstance(document, new FileOutputStream("./target/fileSpecCheckTest3.pdf"), PdfAConformanceLevel.PDF_A_3B);
        writer.createXmpMetadata();
        document.open();

        Font font = FontFactory.getFont("./src/test/resources/com/itextpdf/text/pdf/FreeMonoBold.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED, 12);
        document.add(new Paragraph("Hello World", font));
        ICC_Profile icc = ICC_Profile.getInstance(new FileInputStream("./src/test/resources/com/itextpdf/text/pdf/sRGB Color Space Profile.icm"));
        writer.setOutputIntents("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", icc);

        byte[] somePdf = new byte[25];
        writer.addFileAttachment("some pdf file", somePdf, "foo.pdf", "foo.pdf", PdfAWriter.MimeTypePdf,
                AFRelationshipValue.Data);

        document.close();
    }

    @Test
    public void fileSpecCheckTest4() throws DocumentException, IOException {
        Document document = new Document();
        PdfAWriter writer = PdfAWriter.getInstance(document, new FileOutputStream("./target/fileSpecCheckTest4.pdf"), PdfAConformanceLevel.PDF_A_3B);
        writer.createXmpMetadata();
        document.open();

        Font font = FontFactory.getFont("./src/test/resources/com/itextpdf/text/pdf/FreeMonoBold.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED, 12);
        document.add(new Paragraph("Hello World", font));
        ICC_Profile icc = ICC_Profile.getInstance(new FileInputStream("./src/test/resources/com/itextpdf/text/pdf/sRGB Color Space Profile.icm"));
        writer.setOutputIntents("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", icc);

        byte[] somePdf = new byte[25];
        writer.addPdfAttachment("some pdf file", somePdf, "foo.pdf", "foo.pdf");

        document.close();
    }

    @Test
    public void fileSpecCheckTest5() throws DocumentException, IOException {
        Document document = new Document();
        PdfAWriter writer = PdfAWriter.getInstance(document, new FileOutputStream("./target/fileSpecCheckTest5.pdf"), PdfAConformanceLevel.PDF_A_3B);
        writer.createXmpMetadata();
        document.open();

        Font font = FontFactory.getFont("./src/test/resources/com/itextpdf/text/pdf/FreeMonoBold.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED, 12);
        document.add(new Paragraph("Hello World", font));
        ICC_Profile icc = ICC_Profile.getInstance(new FileInputStream("./src/test/resources/com/itextpdf/text/pdf/sRGB Color Space Profile.icm"));
        writer.setOutputIntents("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", icc);

        ByteArrayOutputStream txt = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(txt);
        out.print("<foo><foo2>Hello world</foo2></foo>");
        out.close();

        boolean exceptionThrown = false;
        try {
            PdfFileSpecification fs
                    = PdfFileSpecification.fileEmbedded(writer,
                    null, "foo.xml", txt.toByteArray());
            writer.addFileAttachment(fs);
        } catch (PdfAConformanceException e) {
            if (e.getObject() != null && e.getLocalizedMessage().equals("The file specification dictionary for an embedded file shall contain correct AFRelationship key.")) {
                exceptionThrown = true;
            }
        }
        if (!exceptionThrown)
            Assert.fail("PdfAConformanceException with correct message should be thrown.");
    }
}
