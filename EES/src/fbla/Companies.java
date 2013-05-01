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
 * Shows all companies found in the database, and allows user to add new
 * companies, add employees to companies, and view company info.
 * 
 * @author Cody Swendrowski
 */
@SuppressWarnings("serial")
public class Companies extends JPanel implements ActionListener {

	private static String[][] data = new String[0][0];

	private String[] names = { "Company Number", "Company Name", "Address",
			"City", "State", "ZIP", "Phone Number", "Email", "Contact Person" };

	private static JTable companiesList;

	/**
	 * Fills frame with companies list and buttons.
	 */
	public Companies() {
		super(new BorderLayout());

		// Panel that holds components of similar nature
		JPanel buttons = new JPanel(new GridLayout(0, 3));

		// Creates new buttons allowing user to manipulate the data
		JButton home = new JButton("Home");
		JButton addCompany = new JButton("Add Company");
		JButton deleteCompany = new JButton("Delete Company");
		JButton addToCompany = new JButton("Add Employee to Company");
		JButton view = new JButton("View Company");
		JButton viewEmployees = new JButton("View Company Employees");

		// Registers this object as the listener for clicks
		home.addActionListener(this);
		addCompany.addActionListener(this);
		deleteCompany.addActionListener(this);
		addToCompany.addActionListener(this);
		view.addActionListener(this);
		viewEmployees.addActionListener(this);

		// Adds buttons to panel
		buttons.add(home);
		buttons.add(addCompany);
		buttons.add(addToCompany);
		buttons.add(view);
		buttons.add(deleteCompany);
		buttons.add(viewEmployees);

		// Creates a new JTable for displaying data
		companiesList = new JTable(new FblaTableModel(data, names));

		// Loads data from file into JTable
		reload();

		// Sets up autosorter and single selection for JTable
		companiesList.setAutoCreateRowSorter(true);
		companiesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Puts list in a scroll pane
		JScrollPane listScroller = new JScrollPane(companiesList);

		// Adds all components to frame
		add(listScroller);
		add(BorderLayout.PAGE_END, buttons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Home"))
			EES.cl.show(EES.pages, "Home"); // Flip page to Home

		else if (e.getActionCommand().equals("Add Company"))
			new AddCompany();

		else if (e.getActionCommand().equals("Add Employee to Company")
				&& (companiesList.getSelectedRow() != -1)) {

			new AddToCompany(
					EES.getSelectedNum(companiesList, "Company Number"));

		} else if (e.getActionCommand().equals("View Company")
				&& (companiesList.getSelectedRow() != -1)) {

			new ViewCompany(EES.getSelectedNum(companiesList, "Company Number"));

		} else if (e.getActionCommand().equals("View Company Employees")
				&& (companiesList.getSelectedRow() != -1))
			new ViewCompanyEmployees(EES.getSelectedNum(companiesList, "Company Number"));
		
		else if (e.getActionCommand().equals("Delete Company")
				&& (companiesList.getSelectedRow() != -1)) {

			int n = JOptionPane
					.showConfirmDialog(
							this,
							"Are you sure you want to delete this Company? This action is irreversable.",
							"Confirmation of deletion",
							JOptionPane.YES_NO_OPTION);

			if (n == JOptionPane.YES_OPTION) {
				EES.delete(EES.getSelectedNum(companiesList, "Company Number"),
						EES.employerPath);
				reload();
			}
		}
	}

	/**
	 * Returns the current data of Companies.
	 * 
	 * @return list of companies in String[][]
	 */
	public String[][] getData() {
		return data;
	}

	/**
	 * Calls helper method in EES to load data. Called upon initialization or
	 * upon data change.
	 */
	public static void reload() {
		EES.loadDataSource(companiesList, data, EES.employerPath);
	}

}
