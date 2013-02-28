package fbla;

import java.awt.CardLayout;
import java.io.File;
import java.io.IOException;
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
	static final String delim = "\t";

	public static void main(String[] args) {
		JFrame window = new JFrame("Employee Evaluation System");
		window.setSize(1000, 600);
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

		} catch (IOException e) {
		}
		return toReturn;
	}

	public static String getEmployeeName(int employeeNum) {
		// Creates file writer
		String toReturn = "Null Company";
		File file = new File("src\\fbla\\Resources\\Employees.txt");
		try {
			if (!file.exists())
				file.createNewFile();

			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				
				Scanner lineScanner = new Scanner(line);
				lineScanner.useDelimiter(delim);
				
				if (employeeNum == lineScanner.nextInt()) {
					lineScanner.next(); //Discard average evaluation score
					toReturn = lineScanner.next() + " " + lineScanner.next(); //Get both first and last name
				}
				lineScanner.close();
			}

			scanner.close();

		} catch (IOException e) {
		}
		return toReturn;
	}

	public static String getEmployerName(int employerNum) {
		// Creates file writer
		String toReturn = "Null Company";
		File file = new File("src\\fbla\\Resources\\Employer.txt");
		try {
			if (!file.exists())
				file.createNewFile();

			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Scanner lineScanner = new Scanner(line);
				lineScanner.useDelimiter(delim);
				if (employerNum == lineScanner.nextInt())
					toReturn = lineScanner.next();
				lineScanner.close();
			}

			scanner.close();

		} catch (IOException e) {
		}
		return toReturn;
	}
}
