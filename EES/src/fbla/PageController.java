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
public class PageController {
	
	private Page currentPage;
	public Home home;
	public Employees employees;
	public Companies companies;
	
	public PageController() {
		home = new Home(this);
		employees = new Employees(this);
		companies = new Companies(this);
		currentPage = home;
	}

	public boolean isOver(MouseEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void checkClick(MouseEvent e) {
		currentPage.checkClick(e);
	}

	public void paint(Graphics g) {
		currentPage.paint(g);
	}

	public void run() {
		currentPage.run();
	}

	public void setCurrentPage(Page p) {
		currentPage = p;
	}
}
