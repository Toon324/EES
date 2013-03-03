package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

	private static String[] names = { "Employee Number", "First Name",
			"Last Name", "Email", "Phone #", "Cell #", "Address", "City", "State", "ZIP" };

	private static JTable employeesList;

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
			AddEmployee.createAndShowGUI();

		else if (e.getActionCommand().equals("Evaluate Employee")
				&& (EES.getSelectedNum(employeesList, "Employee Number") != -1)) {
			
			Evaluate.createAndShowGUI(EES.getSelectedNum(employeesList,
					"Employee Number"));

		} else if (e.getActionCommand().equals("View Employee")
				&& (EES.getSelectedNum(employeesList, "Employee Number") != -1)) {
			
			ViewEmployee.createAndShowGUI(EES.getSelectedNum(employeesList,
					"Employee Number"));

		} else if (e.getActionCommand().equals("View Evaluations")
				&& (EES.getSelectedNum(employeesList, "Employee Number") != -1)) {
			
			ViewEvals.createAndShowGUI(EES.getSelectedNum(employeesList,
					"Employee Number"));

		} else if (e.getActionCommand().equals("Delete Employee")
				&& (EES.getSelectedNum(employeesList, "Employee Number") != -1)) {
			
			int n = JOptionPane
					.showConfirmDialog(
							this,
							"Are you sure you want to delete this employee? This action is irreversable.",
							"Confirmation of deletion",
							JOptionPane.YES_NO_OPTION);

			if (n == JOptionPane.YES_OPTION) {
				delete(EES.getSelectedNum(employeesList, "Employee Number"));
				reload();
			}
		}
	}

	/**
	 * Given an employee number, deletes that employee from file.
	 * 
	 * @param employeeNum
	 *            Number of employee to delete
	 */
	private void delete(int employeeNum) {
		ArrayList<String> toWrite = new ArrayList<String>();
		File file = new File("src\\fbla\\Resources\\Employees.txt");
		try {
			if (!file.exists())
				file.createNewFile();

			Scanner scanner = new Scanner(file); // Loads the .txt file
			while (scanner.hasNextLine()) {
				String temp = scanner.nextLine();

				Scanner lineScanner = new Scanner(temp);
				lineScanner.useDelimiter(EES.delim);

				// Unless found employee matches delete number, add to output
				if (lineScanner.nextInt() != employeeNum)
					toWrite.add(temp);

				lineScanner.close();
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// Write updated file
			try {
				PrintWriter out = new PrintWriter(new FileWriter(file));

				// Writes all remaining lines to file
				for (String s : toWrite)
					out.println(s);

				// Closes writer
				out.close();
			} catch (IOException e) {
			}

		}
	}

	/**
	 * Calls helper method in EES to load data. Called upon initialization or
	 * upon data change.
	 */
	public static void reload() {
		EES.loadDataSource(employeesList, data, EES.employeesPath);
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
