/**
 * 
 */
package fbla;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * @author Cody
 *
 */
public class Employees extends Page {
	private ListBox employeesList;
	
	public Employees(PageController p) {
		super(p);
		buttons.add(new Button("Home", 30, 500));
		buttons.add(new Button("Add Employee", 400, 500));
		employeesList = new ListBox(30, 30);
		employeesList.loadDataSource("Resources\\Employees.txt");
	}

	/* (non-Javadoc)
	 * @see fbla.Page#run()
	 */
	@Override
	public void run() {
		if (buttons.get(0).isClicked())
			pc.setCurrentPage(pc.home);
		else if (buttons.get(1).isClicked())
			AddEmployee.main(null);
		employeesList.run();
	}

	public void paint(Graphics g) {
		super.paint(g);
		employeesList.paint(g);
	}
	
	public void checkClick(MouseEvent e) {
		super.checkClick(e);
		employeesList.checkClick(e);
	}
}
