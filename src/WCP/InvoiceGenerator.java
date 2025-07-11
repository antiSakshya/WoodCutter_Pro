package WCP;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.util.List;

public class InvoiceGenerator {

    public static void generateInvoice(int customerId, String customerName, List<WoodEntry> entries, double subtotal, double discount, double finalAmount) {
        Document document = new Document();
        try {
            String filename = "Invoice_" + customerId + "_" + customerName.replaceAll("\\s+", "_") + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD);
            Paragraph title = new Paragraph("WoodCutter Pro - Invoice", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            // Customer Info
            document.add(new Paragraph("Invoice ID: " + customerId));
            document.add(new Paragraph("Customer Name: " + customerName));
            document.add(new Paragraph("Date: " + java.time.LocalDate.now()));
            document.add(new Paragraph(" "));

            // Table Header
            PdfPTable table = new PdfPTable(6); // Sr, Length, Roundness, CFT, Rate, Amount
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

            String[] headers = {"Sr No", "Length", "Roundness", "CFT", "Rate", "Amount"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            // Add entry rows
            int srNo = 1;
            for (WoodEntry entry : entries) {
                table.addCell(String.valueOf(srNo++));
                table.addCell(String.format("%.2f", entry.getLength()));
                table.addCell(String.format("%.2f", entry.getRoundness()));
                table.addCell(String.format("%.2f", entry.getCFT()));
                table.addCell(String.format("%.2f", entry.getRate()));
                table.addCell(String.format("%.2f", entry.getCost()));
            }

            document.add(table);

            // Total section
            document.add(new Paragraph("Subtotal: ₹" + String.format("%.2f", subtotal)));
            if (discount > 0) {
                document.add(new Paragraph("Discount: ₹" + String.format("%.2f", discount)));
            }
            document.add(new Paragraph("Final Total: ₹" + String.format("%.2f", finalAmount)));

            document.close();
            System.out.println("Invoice generated: " + filename);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
