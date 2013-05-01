package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * Displays a table of all the employees in the system. Offers options of
 * viewing, adding, deleting, evaluating, and viewing evaluations of employees.
 * 
 * @author Cody Swendrowski
 * 
 */
@SuppressWarnings("serial")
public class Employees extends JPanel implements ActionListener {
	private static String[][] data = new String[0][0];

	private static String[] names = { "Employee Number",
			"Average Evaluation Score", "First Name", "Last Name", "Email",
			"Phone #", "Cell #", "Address", "City", "State", "ZIP" };

	private static JTable employeesList;

	/**
	 * Creates a new object that shows all Employees in the database.
	 */
	public Employees() {
		super(new BorderLayout());
		JPanel buttons = new JPanel(new GridLayout(2, 2));

		JButton home = new JButton("Home");
		home.addActionListener(this);

		JButton addEmployee = new JButton("Add Employee");
		addEmployee.addActionListener(this);

		JButton evaluate = new JButton("Evaluate Employee");
		evaluate.addActionListener(this);

		JButton view = new JButton("View Employee");
		view.addActionListener(this);

		JButton delete = new JButton("Delete Employee");
		delete.addActionListener(this);

		JButton viewEvals = new JButton("View Evaluations");
		viewEvals.addActionListener(this);

		buttons.add(home);
		buttons.add(addEmployee);
		buttons.add(evaluate);
		buttons.add(view);
		buttons.add(delete);
		buttons.add(viewEvals);

		employeesList = new JTable(new FblaTableModel(data, names));
		reload();

		employeesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		employeesList.setAutoCreateRowSorter(true);

		// Puts list in a scroll pane
		JScrollPane listScroller = new JScrollPane(employeesList);

		add(listScroller);
		add(BorderLayout.PAGE_END, buttons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Home"))
			EES.cl.show(EES.pages, "Home");

		else if (e.getActionCommand().equals("Add Employee"))
			new AddEmployee();

		else if (e.getActionCommand().equals("Evaluate Employee")
				&& (EES.getSelectedNum(employeesList, "Employee Number") != -1)) {

			new Evaluate(EES.getSelectedNum(employeesList, "Employee Number"));

		} else if (e.getActionCommand().equals("View Employee")
				&& (EES.getSelectedNum(employeesList, "Employee Number") != -1)) {

			new ViewEmployee(EES.getSelectedNum(employeesList,
					"Employee Number"));

		} else if (e.getActionCommand().equals("View Evaluations")
				&& (EES.getSelectedNum(employeesList, "Employee Number") != -1)) {

			new ViewEvals(EES.getSelectedNum(employeesList, "Employee Number"));

		} else if (e.getActionCommand().equals("Delete Employee")
				&& (EES.getSelectedNum(employeesList, "Employee Number") != -1)) {

			int n = JOptionPane
					.showConfirmDialog(
							this,
							"Are you sure you want to delete this employee? This action is irreversable.",
							"Confirmation of deletion",
							JOptionPane.YES_NO_OPTION);

			if (n == JOptionPane.YES_OPTION) {
				EES.delete(
						EES.getSelectedNum(employeesList, "Employee Number"),
						EES.employeesPath);
				reload();
			}
		}
	}

	/**
	 * Calls helper method in EES to load data. Called upon initialization or
	 * upon data change.
	 */
	public static void reload() {
		loadDataSource(employeesList, data, EES.employeesPath);
	}

	// Overridden to load evaluation average in addition to normal data. See
	// EES.loadDataSource for more info.
	public static void loadDataSource(JTable table, String[][] storage,
			String path) {

		ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
		File file = new File(path);
		File evals = new File(EES.evalPath);
		try {
			if (!file.exists())
				file.createNewFile();
			if (!evals.exists())
				evals.createNewFile();

			Scanner scanner = new Scanner(file); // Loads the .txt file

			while (scanner.hasNextLine()) {
				String temp = scanner.nextLine();
				ArrayList<String> line = new ArrayList<String>(); // Gets next
																	// line
				Scanner lineScanner = new Scanner(temp);
				lineScanner.useDelimiter(EES.delim);

				String employeenum = lineScanner.next();
				line.add(employeenum);

				int numToFind = Integer.parseInt(employeenum);

				Scanner evalScan = new Scanner(evals);
				String evalAvg = "No Evaluations";
				while (evalScan.hasNextLine()) {
					String tempLine = evalScan.nextLine();

					Scanner avglineScanner = new Scanner(tempLine);
					avglineScanner.useDelimiter(EES.delim);

					avglineScanner.next(); // Discard evaluation number

					int numFound = Integer.parseInt(avglineScanner.next()
							.replace("﻿", "")); // Employee number

					for (int x = 0; x < 11; x++)
						avglineScanner.next(); // Discard other eval info

					if (numFound == numToFind) {
						evalAvg = avglineScanner.next();
						break;
					}
					avglineScanner.close();
				}

				evalScan.close();

				line.add(evalAvg);

				while (lineScanner.hasNext()) {
					line.add(lineScanner.next());
				}

				lineScanner.close();
				input.add(line);
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Store data
			storage = new String[input.size()][input.get(0).size() + 1];
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
	 * Returns data.
	 * 
	 * @return data
	 */
	public static String[][] getData() {
		return data;
	}

	/**
	 * Returns names of columns
	 * 
	 * @return column names
	 */
	public static String[] getNames() {
		return names;
	}

}
