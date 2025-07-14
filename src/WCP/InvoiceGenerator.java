package WCP;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.util.List;

public class InvoiceGenerator {

    public static void generateInvoice(int customerId, String customerName, List<WoodEntry> entries,
                                       double subtotal, double discount, double finalAmount, double totalCFT) {
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

            // Entry Table
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

            // Totals Table - Right aligned
            PdfPTable totalsTable = new PdfPTable(2);
            totalsTable.setWidthPercentage(40); // adjust width as needed
            totalsTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

            Font labelFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font valueFont = new Font(Font.FontFamily.HELVETICA, 12);

            // Add total rows
            addTotalRow(totalsTable, "Total CFT", totalCFT, labelFont, valueFont);
            addTotalRow(totalsTable, "Subtotal", subtotal, labelFont, valueFont);
            if (discount > 0) {
                addTotalRow(totalsTable, "Discount", discount, labelFont, valueFont);
            }
            addTotalRow(totalsTable, "Final Total", finalAmount, labelFont, valueFont);

            document.add(totalsTable);

            document.close();
            System.out.println("Invoice generated: " + filename);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method for rows in totals table
    private static void addTotalRow(PdfPTable table, String label, double amount, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label + ":", labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        PdfPCell valueCell = new PdfPCell(new Phrase(String.format("₹%.2f", amount), valueFont));
        if (label.equals("Total CFT")) {
            valueCell = new PdfPCell(new Phrase(String.format("%.2f", amount), valueFont)); // no ₹ for CFT
        }
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }
}
