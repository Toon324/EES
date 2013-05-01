package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * Allows the user to view all the employees that currently belong to a company.
 * 
 * @author Cody Swendrowski
 */
public class ViewCompanyEmployees extends DataWindow {

	private static final long serialVersionUID = -6160370664259801240L;
	private JTable employeesList;
	private int employerNum;

	/**
	 * Creates a new window that shows all employees belonging to a specific
	 * company.
	 * 
	 * @param num
	 *            Company number to pull employees from
	 */
	public ViewCompanyEmployees(int num) {
		super(new BorderLayout());

		employerNum = num;

		// Panel that holds components of similar nature
		JPanel buttons = new JPanel(new GridLayout(1, 2));

		// Creates new buttons allowing user to close or view employee
		JButton view = new JButton("View Employee");
		JButton close = new JButton("Close");

		// Registers this object as the listener for clicks
		view.addActionListener(this);
		close.addActionListener(this);

		// Adds buttons to panel
		buttons.add(view);
		buttons.add(close);

		// Creates a new JTable for displaying data
		employeesList = new JTable(new FblaTableModel(Employees.getData(),
				Employees.getNames()));

		// Loads data from file into JTable
		loadDataSource(employeesList, Employees.getData());

		// Sets up autosorter and single selection for JTable
		employeesList.setAutoCreateRowSorter(true);
		employeesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Puts JTable into a JScrollPane, allowing it to be scrolled
		JScrollPane listScroller = new JScrollPane(employeesList);

		// Adds all components to frame
		add(BorderLayout.CENTER, listScroller);
		add(BorderLayout.PAGE_END, buttons);

		frame = EES.createAndShowGUI(this);
		frame.setTitle("Select Employee to add to "
				+ EES.getEmployerName(employerNum));
	}

	/*
	 * Overridden to load only the employees that belong to the company.
	 */
	private void loadDataSource(JTable table, String[][] storage) {
		ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
		File employees = new File(EES.employeesPath);
		File placements = new File(EES.fieldPath);
		try {
			ArrayList<String> foundEmployees = new ArrayList<String>();

			Scanner placementScanner = new Scanner(placements);
			Scanner employeeScanner = new Scanner(employees); // Loads the .txt
																// file

			while (placementScanner.hasNextLine()) {
				String temp = placementScanner.nextLine();
				Scanner lineScanner = new Scanner(temp);
				lineScanner.useDelimiter(EES.delim);

				int employeeFoundNum = lineScanner.nextInt();
				int employerFoundNum = lineScanner.nextInt();
				if (employerFoundNum == employerNum)
					foundEmployees.add(Integer.toString(employeeFoundNum));

				lineScanner.close();
			}
			placementScanner.close();

			while (employeeScanner.hasNextLine()) {
				String temp = employeeScanner.nextLine();
				ArrayList<String> line = new ArrayList<String>(); // Gets next
				// line
				Scanner lineScanner = new Scanner(temp);
				lineScanner.useDelimiter(EES.delim);

				int foundEmployeeNum = lineScanner.nextInt();
				for (String s : foundEmployees) {
					System.out.println("Found: " + foundEmployeeNum + "?"
							+ Integer.parseInt(s));
					if (foundEmployeeNum == Integer.parseInt(s)) {
						line.add(s);
						while (lineScanner.hasNext())
							line.add(lineScanner.next());
						input.add(line);
					}
				}
				lineScanner.close();
			}
			employeeScanner.close();
		} catch (Exception e) {
		} finally {
			if (input.size() == 0) { // No employees found
				storage = new String[0][0];
				((FblaTableModel) table.getModel()).setData(storage);
				return;
			}
			// Store data
			storage = new String[input.size()][input.get(0).size()];
			System.out.println("Storage size: " + input.size() + " by "
					+ input.get(0).size());
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Close"))
			frame.dispose();

		else if (e.getActionCommand().equals("View Employee")
				&& EES.getSelectedNum(employeesList, "Employee Number") != -1)
			new ViewEmployee(EES.getSelectedNum(employeesList,
					"Employee Number"));
	}
}
