package fbla;

import javax.swing.JFrame;

/**
 * The class that controls and owns all necessary objects.
 * 
 * @author Cody Swendrowski
 */

public class EES{
	
	public static void main(String[] args){
		JFrame window = new JFrame("Employee Evaluation System");
		window.setSize(800,600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Home home = new Home();
		Companies companies = new Companies();
		Employees employees = new Employees();
		
		home.setVisible(true);
		companies.setVisible(false);
		employees.setVisible(false);
		
		window.add(companies);
		window.add(employees);
		window.add(home);
		
		//window.pack();

		// Display the window.
		window.setVisible(true);
	}	
}
