package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Cody Swendrowski
 *
 */
@SuppressWarnings("serial")
public class Home extends JPanel implements ActionListener {

	private JButton employees, companies;
	/**
	 * 
	 */
	public Home() {
		super(new GridLayout(1,2));
		Icon employeesIcon = createImageIcon("Resources\\Employees.png");
		employees = new JButton(employeesIcon);
		employees.setActionCommand("Employees");
		employees.addActionListener(this);
		add(employees);
		
		Icon companiesIcon = createImageIcon("Resources\\Companies.png");
		companies = new JButton(companiesIcon);
		companies.setActionCommand("Companies");
		companies.addActionListener(this);
		add(companies);
	}
	
	/** Returns an ImageIcon, or null if the path was invalid. */
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
