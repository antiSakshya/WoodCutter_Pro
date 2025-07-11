package WCP;

import WCP.View;

public class Main {
	public static void main(String args[]) {
		//Initialize database
		DatabaseHelper.initializeDatabase();
		View view = new View();
		new Controller(view);
	}

}
