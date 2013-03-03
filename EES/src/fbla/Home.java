package fbla;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
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
		Icon employeesIcon = createImageIcon("Resources\\Employees.png");
		JButton employees = new JButton(employeesIcon);
		employees.setActionCommand("Employees");
		employees.addActionListener(this);
		add(employees);

		// Loads in image for Companies and creates a button out of it
		Icon companiesIcon = createImageIcon("Resources\\Companies.png");
		JButton companies = new JButton(companiesIcon);
		companies.setActionCommand("Companies");
		companies.addActionListener(this);
		add(companies);
	}

	/**
	 * Given a path, returns an ImageIcon or null if no Image found.
	 * 
	 * @param path
	 *            Path to Image
	 * @return ImageIcon of image at path
	 */
	protected ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, "");
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		EES.cl.show(EES.pages, e.getActionCommand());
	}

}
