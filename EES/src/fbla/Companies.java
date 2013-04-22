package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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

	private static String[] names = { "Company Number", "Company Name",
			"Address", "City", "State", "ZIP", "Phone Number", "Email",
			"Contact Person" };

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
		JButton deleteEmployee = new JButton("Remove Employee from Company");
		JButton addToCompany = new JButton("Add Employee to Company");
		JButton view = new JButton("View Company");

		// Registers this object as the listener for clicks
		home.addActionListener(this);
		addCompany.addActionListener(this);
		deleteCompany.addActionListener(this);
		addToCompany.addActionListener(this);
		deleteEmployee.addActionListener(this);
		view.addActionListener(this);

		// Adds buttons to panel
		buttons.add(home);
		buttons.add(addCompany);
		buttons.add(addToCompany);
		buttons.add(view);
		buttons.add(deleteCompany);
		buttons.add(deleteEmployee);

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
			AddCompany.createAndShowGUI();

		else if (e.getActionCommand().equals("Add Employee to Company")
				&& (companiesList.getSelectedRow() != -1)) {

			AddToCompany.createAndShowGUI(EES.getSelectedNum(companiesList,
					"Company Number"));

		} else if (e.getActionCommand().equals("View Company")
				&& (companiesList.getSelectedRow() != -1)) {

			ViewCompany.createAndShowGUI(EES.getSelectedNum(companiesList,
					"Company Number"));
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
