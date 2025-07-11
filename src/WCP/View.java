package WCP;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.JTextComponent;

public class View {
	
	public JFrame frame;
	public JTextField lengthField, roundnessField, rateField;
	public JButton addButton, saveButton, deleteButton;
	public JTable table;
	public javax.swing.table.DefaultTableModel tableModel; //this will help to edit the table
	public JPanel totalPanel;
	JLabel totalLabel;
	
	public View() {
		frame = new JFrame("WoodCutter Pro");
		//Hey frame, please go full screen when you appear
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		//Header
		JPanel header = new JPanel();
		header.setBackground(Color.DARK_GRAY);
		JLabel titleLabel = new JLabel("WoodCutter PRO");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 50)); //set the Font
        header.add(titleLabel);
        header.add(Box.createVerticalStrut(80)); //Give some vertical space
        
        //Center middle part
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel heading = new JLabel("Enter Logs Length and Roundness");
        heading.setAlignmentX(Component.CENTER_ALIGNMENT); //Center horizontally
        heading.setFont(new Font("SansSerif", Font.BOLD, 30)); //Set the font
        heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); //Spacing below
        container.add(heading);
        
        //Form Panel better to use GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Less padding vertically
        gbc.anchor = GridBagConstraints.WEST; // Align left

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lengthLabel = new JLabel("Enter Length:");
        lengthLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        formPanel.add(lengthLabel, gbc);

        gbc.gridx = 1;
        lengthField = new JTextField();
        lengthField.setPreferredSize(new Dimension(120, 25));
        formPanel.add(lengthField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel roundnessLabel = new JLabel("Enter Roundness:");
        roundnessLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        formPanel.add(roundnessLabel, gbc);

        gbc.gridx = 1;
        roundnessField = new JTextField();
        roundnessField.setPreferredSize(new Dimension(120, 25));
        formPanel.add(roundnessField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel rateLabel = new JLabel("Enter Rate:");
        rateLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        formPanel.add(rateLabel, gbc);
        
        gbc.gridx = 1;
        rateField =new JTextField();
        rateField.setPreferredSize(new Dimension(120, 25));
        formPanel.add(rateField, gbc);
        
        container.add(formPanel);
        
        //Add Save and Delete Button
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        saveButton = new JButton("Save");
        deleteButton = new JButton("Delete");
        Dimension buttonSize = new Dimension(120, 35);
        addButton.setPreferredSize(buttonSize);
        saveButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        
        buttonPanel.add(addButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        
        container.add(buttonPanel);

        //Footer
        JPanel footer = new JPanel(new BorderLayout());
        footer.setPreferredSize(new Dimension(frame.getWidth(), 500));
        footer.setBackground(Color.LIGHT_GRAY);
        
        //JTable
        String[] columns = {"Sr No", "Length", "Roundness", "CFT","Rate", "Amount"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setPreferredSize(new Dimension(frame.getWidth(),500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getColumnModel().getColumn(0).setPreferredWidth(-800);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setRowHeight(30);
        
        //Total calculated panel
        totalPanel = new JPanel();
        totalPanel.setBackground(Color.CYAN);
        totalLabel = new JLabel("Total Logs : 0  |  Total Amount: ₹0.00");
		totalLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        totalPanel.add(totalLabel);
        footer.add(totalPanel, BorderLayout.SOUTH);
        
        JScrollPane tableScrollPane = new JScrollPane(table);
        footer.add(tableScrollPane, BorderLayout.CENTER);
      
        frame.add(header, BorderLayout.NORTH);
        frame.add(container, BorderLayout.CENTER);
        frame.add(footer, BorderLayout.SOUTH);
        frame.setVisible(true);

	}
	
	
	/*
	 * Getter methods retrieve data from UI components (like text fields or combo boxes).
They help the Controller class get input values from the View.

It is like Getter methods retrieve data from UI components (like text fields or combo boxes).
They help the Controller class get input values from the View.
	 */
	
	public String getLengthText() {
		return lengthField.getText();
	}
	
	public String getroundnessText() {
		return roundnessField.getText();
	}
	
	public String getrateText() {
		return rateField.getText();
	}
	
	public int getSelectedTableRow() {
		return table.getSelectedRow();
	}
	
	//add entry in table row-
	public void addTableRow(Object[] rowData) {
		tableModel.addRow(rowData);
	}
	
	//delete entry in table row-
	public void removeTableRow(int row) {
		tableModel.removeRow(row);
	}
	
	//To update total Amount-
	public void updateTotal(double total, int count) {
	totalLabel.setText(String.format("Total Logs: %d  |  Total Amount: ₹%.2f", count, total));
	}
	
	//to clear inputField after every entry
	public void clearInputField() {
		lengthField.setText("");
		roundnessField.setText("");
		lengthField.requestFocus();
	}
	
	//Function to show error-
	public void showError(String message) {
		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void clearTable() {
	    tableModel.setRowCount(0);
	}

}
