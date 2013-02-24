package fbla;

/**
 * @author Cody Swendrowski
 *
 */
public class Home extends Page {

	/**
	 * 
	 */
	public Home(PageController p) {
		super(p);
		buttons.add(new ImageButton(50,50,"Resources\\Employees.png"));
		buttons.add(new ImageButton(350,50, "Resources\\Companies.png"));
	}

	
	@Override
	public void run() {
		if (buttons.get(0).isClicked())
			pc.setCurrentPage(pc.employees);
		else if (buttons.get(1).isClicked())
			pc.setCurrentPage(pc.companies);
	}

}
