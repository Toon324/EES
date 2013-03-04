package fbla;

import java.awt.CardLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 * The class that controls and owns all necessary objects.
 * 
 * @author Cody Swendrowski
 */

public class EES {
	public static CardLayout cl = new CardLayout(); // Allows program to flip
													// between different pages
													// (each page being a panel)
	public static JPanel pages; // Panel
	public static final String delim = ",",
			employeesPath = "src\\fbla\\Resources\\Employees.txt",
			employerPath = "src\\fbla\\Resources\\Employer.txt",
			evalPath = "src\\fbla\\Resources\\Evaluation Results.txt",
			fieldPath = "src\\fbla\\Resources\\Field Placements.txt";

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

	/**
	 * Given an employee number, returns the employer number from file.
	 * 
	 * @param employeeNum
	 *            Number of employee to find
	 * @return -1 if no employer found; else, employer number.
	 */
	public static int getEmployerNum(int employeeNum) {
		// Creates file writer
		int toReturn = -1;
		File file = new File("src\\fbla\\Resources\\Field Placements.txt");
		try {
			if (!file.exists())
				file.createNewFile();

			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Scanner lineScanner = new Scanner(line);
				lineScanner.useDelimiter(delim);
				if (employeeNum == lineScanner.nextInt())
					toReturn = lineScanner.nextInt();
				lineScanner.close();
			}

			scanner.close();

		} catch (IOException e) {
		}
		return toReturn;
	}

	/**
	 * Given an employee number, returns the first and last name of the
	 * Employee.
	 * 
	 * @param employeeNum
	 *            Number of employee to get name of
	 * @return First and last name of employee, or "No Name" if no name is found
	 */
	public static String getEmployeeName(int employeeNum) {
		// Creates file writer
		String toReturn = "No Name";
		File file = new File(EES.employeesPath);
		try {
			if (!file.exists())
				file.createNewFile();

			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				//Replaces any fillers with actual char
				line = line.replace("<comma>",",");
				Scanner lineScanner = new Scanner(line);
				lineScanner.useDelimiter(delim);

				if (employeeNum == lineScanner.nextInt()) {
					// Get both first and last name
					toReturn = lineScanner.next() + " " + lineScanner.next();
				}
				lineScanner.close();
			}

			// Close the scanner
			scanner.close();

		} catch (IOException e) {
		}
		return toReturn;
	}

	/**
	 * Given an employer number, returns the name of the company.
	 * 
	 * @param employerNum
	 *            Number of employer to find name of
	 * @return "Null Company" if no name found; else, name of company
	 */
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

	/**
	 * Given a path, loads file data into given String[][] storage, then sets
	 * that as the data for given JTable.
	 * 
	 * @param table
	 *            JTable to display data
	 * @param storage
	 *            String[][] to hold data
	 * @param path
	 *            Path to file to load
	 */
	public static void loadDataSource(JTable table, String[][] storage,
			String path) {

		ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
		File file = new File(path);
		try {
			if (!file.exists())
				file.createNewFile();

			Scanner scanner = new Scanner(file); // Loads the .txt file

			while (scanner.hasNextLine()) {
				String temp = scanner.nextLine();
				ArrayList<String> line = new ArrayList<String>(); // Gets next
																	// line
				Scanner lineScanner = new Scanner(temp);
				lineScanner.useDelimiter(EES.delim);
				while (lineScanner.hasNext()) {
					line.add(lineScanner.next());
				}
				lineScanner.close();
				input.add(line);
			}
			scanner.close();
		} catch (Exception e) {
		} finally {
			// Store data
			storage = new String[input.size()][input.get(0).size()];
			for (int x = 0; x < input.size(); x++) {
				int cnt = 0;
				for (String s : input.get(x)) {
					storage[x][cnt] = s;
					cnt++;
				}
			}
			((FblaTableModel) table.getModel()).setData(storage);
		}
	}

	/**
	 * Given the name of a collumn, returns the data of the selected row that
	 * matches that collumn name from given JTable.
	 * 
	 * @param table
	 *            JTable to load data from
	 * @param nameOfNum
	 *            Collumn name to load data of
	 * @return data in selected row and collumn
	 */
	public static int getSelectedNum(JTable table, String nameOfNum) {
		int col = -1;
		for (int x = 0; x < table.getColumnCount(); x++)
			if (table.getColumnName(x).equals(nameOfNum))
				col = x;
		if (col == -1)
			return -1;
		String s = (String) table.getModel().getValueAt(table.getSelectedRow(),
				col);
		int i = java.lang.Integer.parseInt(s);
		return i;
	}
}
