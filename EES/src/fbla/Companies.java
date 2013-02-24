package fbla;

/**
 * @author Cody
 *
 */
public class Companies extends Page {

	/**
	 * @param p
	 */
	public Companies(PageController p) {
		super(p);
		buttons.add(new Button("Home", 50, 400));
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
