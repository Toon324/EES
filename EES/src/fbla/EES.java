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
	public static CardLayout cl = new CardLayout(); //Allows program to flip between different pages (each page being a panel)
	public static JPanel pages; //Panel
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
					lineScanner.next(); // Discard average evaluation score
					toReturn = lineScanner.next() + " " + lineScanner.next(); // Get
																				// both
																				// first
																				// and
																				// last
																				// name
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

	public static void loadDataSource(JTable table, String[][] storage,
			String path) {

		ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
		File file = new File(path);
		try {
			if (!file.exists())
				file.createNewFile();

			Scanner scanner = new Scanner(file); // Loads the .txt file
			scanner.useDelimiter("\t"); // Uses tab as an indicator that a new
										// data segment is present. Can not use
										// comma, as commas may be present in
										// comments.
			while (scanner.hasNextLine()) {
				String temp = scanner.nextLine();
				ArrayList<String> line = new ArrayList<String>(); // Gets next
																	// line
				Scanner lineScanner = new Scanner(temp);
				lineScanner.useDelimiter("\t");
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
