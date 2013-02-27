package fbla;

import java.awt.CardLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The class that controls and owns all necessary objects.
 * 
 * @author Cody Swendrowski
 */

public class EES {
	static CardLayout cl = new CardLayout();
	static JPanel pages;

	public static void main(String[] args) {
		JFrame window = new JFrame("Employee Evaluation System");
		window.setSize(800, 600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pages = new JPanel(cl);

		Home home = new Home();
		Companies companies = new Companies();
		Employees employees = new Employees();

		pages.add(companies, "Companies");
		pages.add(employees, "Employees");
		pages.add(home, "Home");

		cl.show(pages, "Home");

		window.add(pages);

		// window.pack();

		// Display the window.
		window.setVisible(true);

		while (true) {
			window.repaint();
		}
	}

	public static int getEmployerNum(int employeeNum) {
		// Creates file writer
		int toReturn = -1;
		File file = new File("src\\fbla\\Resources\\Field Placements.txt");
		try {
			if (!file.exists())
				file.createNewFile();
			
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine())
				if (employeeNum == scanner.nextInt())
					toReturn = scanner.nextInt();
			
			scanner.close();

		} catch (IOException e) {}
		return toReturn;
	}
}
