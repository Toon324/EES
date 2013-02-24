package fbla;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @author Cody
 *
 */
public class Page {

	public ArrayList<Button> buttons;
	
	/**
	 * 
	 */
	public Page() {
		buttons = new ArrayList<Button>();
	}

	
	/**
	 * Checks every button to see if mouse is hovering over a Button.
	 * 
	 * @param x
	 *            X position of mouse
	 * @param y
	 *            Y position of mouse
	 * @return True is mouse is hovering over a Button in the current mode
	 */
	public boolean isOver(MouseEvent e) {
		try {
			for (int i = 0; i < buttons.size(); i++) {
				if (buttons.get(i).checkOver(e.getX(), e.getY()))
					return true;
			}
		} catch (java.lang.NullPointerException ex) {}
		return false;
	}
}
