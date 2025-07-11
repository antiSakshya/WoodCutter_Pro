package WCP;

import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Controller {
	private View view;
	private ArrayList<WoodEntry> entries = new ArrayList<>();
	private int serial = 1;
	
	public Controller(View view) {
		this.view = view;
		view.addButton.addActionListener(e -> addEntry());
		view.deleteButton.addActionListener(e -> deleteEntry());
		view.saveButton.addActionListener(e -> saveWithOptionalDiscount());
	}
	
	private void addEntry() {
		try {
			double length = Double.parseDouble(view.getLengthText());
			double roundness = Double.parseDouble(view.getroundnessText());
			double rate = Double.parseDouble(view.getrateText());
			
			WoodEntry entry = new WoodEntry(length, roundness, rate);
			entries.add(entry);
			
			Object[] row = {
					serial++,
					entry.getLength(),
					entry.getRoundness(),
					entry.getCFT(),
					entry.getRate(),
					entry.getCost(),
			};
			view.addTableRow(row);
			updateTotal();
			view.clearInputField();
			
		}
		catch(NumberFormatException ex) {
			view.showError("Please enter valid number");
		}
	}
	
	private void deleteEntry() {
		int row = view.getSelectedTableRow();
		if(row != -1) {
			entries.remove(row);
			view.removeTableRow(row);
			updateTotal();
		}
		else {
			view.showError("Please select a row to delete.");
		}
	}
	
	//private String currentCustomerName = "";
	private String customerName = "";
	private double discount = 0;
	
	private void saveWithOptionalDiscount() {
		double subtotal = totalAmount();
		double finalAmount = 0;
		
		//ask customer name -
		customerName = JOptionPane.showInputDialog(view.frame, "Enter Customer Name:");
		
		if (customerName == null || customerName.trim().isEmpty()) {
			view.showError("Customer name cannot be empty.");
			return;
		}
		
		//this.currentCustomerName =  customerName.trim();
		
		discount = 0;
		finalAmount = subtotal;
		
		int option = JOptionPane.showConfirmDialog(view.frame, "Subtotal is ₹" + 
		String.format("%.2f", subtotal) + "\nDo you want to apply a discount?", 
		"Conferm Discount", JOptionPane.YES_NO_OPTION);
		

	    if (option == JOptionPane.YES_OPTION) {
	        String discountStr = JOptionPane.showInputDialog(view.frame, "Enter Discount:", "0");
	        try {
	            discount = Double.parseDouble(discountStr);
	            if (discount < 0 || discount > subtotal) {
	                view.showError("Invalid discount value.");
	                return;
	            }
	            finalAmount = subtotal - discount;
	        } catch (NumberFormatException ex) {
	            view.showError("Please enter a valid number for discount.");
	            return;
	        }
	    }
	    
	    //Save it to database which i created DatabaseHelper class
	    int customerId = DatabaseHelper.saveCustomerWithLogs(customerName, entries);
	    if (customerId != -1) {
	        JOptionPane.showMessageDialog(view.frame,
	                "Transaction saved successfully!\n" +
	                "Customer: " + customerName + "\n" +
	                (discount > 0 ? "Discount: ₹" + String.format("%.2f", discount) + "\n" : "") +
	                "Final Total: ₹" + String.format("%.2f", finalAmount),
	                "Success", JOptionPane.INFORMATION_MESSAGE);
	        
	        //calling the invoice generator here
	        InvoiceGenerator.generateInvoice(customerId, customerName, entries, subtotal, discount, finalAmount);
	        
	        // Reset for next customer
	        entries.clear();
	        view.clearTable();
	        serial = 1;
	        updateTotal();
	    } else {
	        view.showError("Failed to save transaction to database.");
	    }
	}
	
	private double totalAmount() {
		double total = 0;
		for(WoodEntry e : entries) {
			total += e.getCost();
		}
		return total;
	}
	
	private void updateTotal() {
		double total = totalAmount();
		/*for(WoodEntry e : entries) {
			total += e.getCost();
		}*/
		view.updateTotal(total, entries.size());
	}
}
