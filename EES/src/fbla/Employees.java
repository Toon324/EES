/**
 * 
 */
package fbla;

/**
 * @author Cody
 *
 */
public class Employees extends Page {

	public Employees(PageController p) {
		super(p);
		buttons.add(new Button("Home", 30, 500));
	}

	/* (non-Javadoc)
	 * @see fbla.Page#run()
	 */
	@Override
	public void run() {
		if (buttons.get(0).isClicked())
			pc.setCurrentPage(pc.home);
	}

}
