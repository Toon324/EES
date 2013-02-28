package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * @author Cody
 * 
 */
@SuppressWarnings("serial")
public class Companies extends JPanel implements ActionListener {

	static String[][] data = new String[0][0];
	static ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
	static String[] names = { "Company Number", "Company Name", "Address",
			"City", "State", "ZIP", "Phone Number", "Email", "Contact Person" };
	static JTable companiesList;
	static FblaTableModel tableModel;

	/**
	 * @param p
	 */
	public Companies() {
		super(new BorderLayout());
		JPanel buttons = new JPanel(new GridLayout(0, 2));

		JButton home = new JButton("Home");
		home.addActionListener(this);

		JButton addCompany = new JButton("Add Company");
		addCompany.addActionListener(this);

		JButton addToCompany = new JButton("Add Employee to Company");
		addToCompany.addActionListener(this);

		JButton view = new JButton("View Company");
		view.addActionListener(this);

		buttons.add(home);
		buttons.add(addCompany);
		buttons.add(addToCompany);
		buttons.add(view);

		tableModel = new FblaTableModel(data, names);
		companiesList = new JTable(tableModel);
		Employees.loadDataSource(companiesList, tableModel, data,
				"src\\fbla\\Resources\\Employer.txt");
		companiesList.setAutoCreateRowSorter(true);

		// Puts list in a scroll pane
		JScrollPane listScroller = new JScrollPane(companiesList);

		add(listScroller);
		add(BorderLayout.PAGE_END, buttons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Home"))
			EES.cl.show(EES.pages, "Home");
		else if (e.getActionCommand().equals("Add Company"))
			AddCompany.main(null);
		else if (e.getActionCommand().equals("Add Employee to Company")
				&& (companiesList.getSelectedRow() != -1)) {
			AddToCompany.main(null);
			AddToCompany.setEmployerNum(getSelectedEmployerNum());
		} else if (e.getActionCommand().equals("View Company")
				&& (companiesList.getSelectedRow() != -1)) {
			ViewCompany.main(null);
			ViewCompany.setEmployerNum(getSelectedEmployerNum());
		}
	}

	public String[][] getData() {
		return data;
	}

	private int getSelectedEmployerNum() {
		int col = -1;
		for (int x = 0; x < companiesList.getColumnCount(); x++)
			if (companiesList.getColumnName(x).equals("Company Number"))
				col = x;
		if (col == -1)
			return -1;
		String s = (String) tableModel.getValueAt(
				companiesList.getSelectedRow(), col);
		int i = java.lang.Integer.parseInt(s);
		return i;
	}

}
