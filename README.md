✅ Polished README.md for WoodCutter Pro
markdown
Copy code
# 🌲 WoodCutter Pro

**WoodCutter Pro** is a Java Swing desktop application designed for woodcutting businesses to manage customer transactions, calculate wood measurements in CFT (Cubic Feet), generate invoices, and store all data in a local SQLite database.

This project is built using Java’s Swing framework, follows the MVC (Model-View-Controller) architecture, and demonstrates real-world use of Java OOP, database integration, and PDF generation.

---

## 🚀 Features

- 🖥️ **User-friendly GUI** built with Java Swing
- 📏 **Wood measurement calculator** (Length × Roundness → CFT)
- 📊 **Transaction management** (Add, delete log entries)
- 👤 **Customer data storage** using SQLite database
- 📄 **PDF invoice generation** using iTextPDF
- 💸 **Discount system** for final totals
- 🧩 **Responsive layout** that works in full-screen mode
- ✅ **Input validation** and clear error messages

---

## 📦 Prerequisites

- Java JDK 8 or later
- SQLite JDBC driver (bundled in most JDK setups)
- iTextPDF library (for invoice generation)

---

## 💻 How to Run

1. **Clone the repository**
2. **Compile the project:**
   ```bash
   javac WCP/*.java
Run the main class:

bash
Copy code
java WCP.Main
🗃️ Database Details
The app automatically creates a local SQLite database file:
woodcutter.db with the following tables:

customers: Stores customer info

logs: Stores wood entries linked to each customer via foreign key

🧾 Invoice Generation
Invoices are saved as PDFs with filenames like:
Invoice_[customerID]_[customerName].pdf

Generated using iTextPDF for a clean, professional layout

Includes all transaction details and totals

🧠 Code Structure & Key Classes
📁 Package: WCP/
less
Copy code
WCP/
├── Controller.java         // Main controller logic
├── DatabaseHelper.java     // Handles all DB operations
├── InvoiceGenerator.java   // PDF creation logic
├── Main.java               // Entry point of the app
├── View.java               // GUI components and layout
└── WoodEntry.java          // Data model class
🧩 Key Classes Explained
1. WoodEntry.java
Purpose: Represents a single wood log entry

CFT Calculation:
(
𝑙
𝑒
𝑛
𝑔
𝑡
ℎ
×
𝑟
𝑜
𝑢
𝑛
𝑑
𝑛
𝑒
𝑠
𝑠
2
)
/
2304
(length×roundness 
2
 )/2304

Methods:

calculateCFT(), getters, cost calculation

2. View.java
Purpose: Builds the GUI interface

Highlights:

Full-screen layout

Input validation

JTable for log display

3. Controller.java
Purpose: Manages user actions and ties View & Model

Highlights:

Handles button events

Adds and deletes log entries

Saves transactions with optional discount

4. DatabaseHelper.java
Purpose: Manages all database interactions

Highlights:

Creates DB/tables if not present

Uses prepared statements

Supports transactions for safe data handling

5. InvoiceGenerator.java
Purpose: Creates detailed PDF invoices

Highlights:

Professional layout

Includes customer and log info

Currency formatting

🎯 Design Patterns Used
MVC Pattern:

Model: WoodEntry

View: View

Controller: Controller

Singleton Pattern (implicit):

Used in DatabaseHelper for managing a single DB connection

Observer Pattern:

Swing uses this natively for handling UI events (button clicks etc.)

🔬 Technical Highlights
Database: SQLite (lightweight, embedded)

GUI: Java Swing

PDF: iTextPDF for professional invoices

MVC architecture for clean separation of concerns

Prepared statements for security

Responsive, full-screen design

📐 Core Formulas
CFT Calculation:

CFT
=
Length
×
Roundness
2
2304
CFT= 
2304
Length×Roundness 
2
 
​
 
Cost Calculation:

Cost
=
CFT
×
Rate
Cost=CFT×Rate
📚 Learning Outcomes
Java Swing GUI development

JDBC & SQLite integration

Real-world OOP principles

PDF generation with external libraries

MVC architecture implementation

Error handling and input validation

🙌 Credits
Made with ❤️ by Sakshya
This is my first complete Java project and a major step in my academic comeback journey.

📄 License
This project is open source and available under the MIT License.

yaml
Copy code

---

Would you like me to generate the actual `README.md` file for download? Or help you
