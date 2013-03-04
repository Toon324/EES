package fbla;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Allows user to view either companies or employees by pressing image buttons.
 * 
 * @author Cody Swendrowski
 */
@SuppressWarnings("serial")
public class Home extends JPanel implements ActionListener {

	/**
	 * Creates a new Home page.
	 */
	public Home() {
		super(new GridLayout(1, 2));

		// Loads in image for Employees and creates a button out of it
		ImageIcon employeesIcon = new ImageIcon("src\\fbla\\Resources\\Employees.png");
		JButton employees = new JButton(employeesIcon);
		employees.setActionCommand("Employees");
		employees.addActionListener(this);
		add(employees);

		// Loads in image for Companies and creates a button out of it
		ImageIcon companiesIcon = new ImageIcon("src\\fbla\\Resources\\Companies.png");
		JButton companies = new JButton(companiesIcon);
		companies.setActionCommand("Companies");
		companies.addActionListener(this);
		add(companies);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		EES.cl.show(EES.pages, e.getActionCommand());
	}

}
