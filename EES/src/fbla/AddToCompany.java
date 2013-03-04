package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * Pulls up a list of all employees and allows the user to add one to the
 * currently selected company.
 * 
 * @author Cody Swendrowski
 */
@SuppressWarnings("serial")
public class AddToCompany extends DataWindow {

	private static int employerNum = -1;
	private static JTable employeesList;

	/**
	 * Fills frame with capability of adding an employee to a company.
	 */
	public AddToCompany() {
		super(new BorderLayout());

		// Panel that holds components of similar nature
		JPanel buttons = new JPanel(new GridLayout(1, 2));

		// Creates new buttons allowing user to cancel or finish
		JButton finish = new JButton("Finish");
		JButton cancel = new JButton("Cancel");

		// Registers this object as the listener for clicks
		finish.addActionListener(this);
		cancel.addActionListener(this);

		// Adds buttons to panel
		buttons.add(cancel);
		buttons.add(finish);

		// Creates a new JTable for displaying data
		employeesList = new JTable(new FblaTableModel(Employees.getData(),
				Employees.getNames()));
		
		// Loads data from file into JTable
		EES.loadDataSource(employeesList, Employees.getData(), EES.employeesPath);

		// Sets up autosorter and single selection for JTable
		employeesList.setAutoCreateRowSorter(true);
		employeesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Puts JTable into a JScrollPane, allowing it to be scrolled
		JScrollPane listScroller = new JScrollPane(employeesList);

		// Adds all components to frame
		add(BorderLayout.CENTER, listScroller);
		add(BorderLayout.PAGE_END, buttons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Cancel"))
			frame.dispose();

		// If user is happy with input, write the new data out
		else if (e.getActionCommand().equals("Finish")) {
			writeData(EES.fieldPath);
			frame.dispose();
		}
	}

	// Overridden to write Employee selection
	protected void writeData(String path) {
		try {
			// Creates file writer
			PrintWriter out = null;
			File file = new File(path);
			try {
				if (!file.exists())
					file.createNewFile(); // If there is no file, create it

				out = new PrintWriter(new FileWriter(file.getAbsoluteFile(),
						true)); // Append to current data
			} catch (IOException e) {
			}

			// Prints data
			out.println(employeesList.getModel().getValueAt(
					employeesList.getSelectedRow(), 0)
					+ EES.delim + employerNum);

			// Closes stream
			out.close();
		} catch (Exception e) {
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 * 
	 * @param n
	 *            Number of employer to add to
	 */
	protected static void createAndShowGUI(int n) {
		employerNum = n;
		// Create and set up the window.
		frame = new JFrame("Select Employee to add to "
				+ EES.getEmployerName(employerNum));
		frame.setSize(500, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Display the window.
		frame.setVisible(true);

		// Create and set up the content pane.
		AddToCompany popup = new AddToCompany();
		popup.setOpaque(true); // content panes must be opaque
		frame.setContentPane(popup);
		frame.pack();
	}

}
