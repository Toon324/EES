package fbla;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @author Cody Swendrowski
 * 
 */
public abstract class Page {

	public ArrayList<Button> buttons;
	protected PageController pc;

	public Page(PageController p) {
		pc = p;
		buttons = new ArrayList<Button>();
	}

	/**
	 * Checks to see if mouse clicks a Button on the current page.
	 * 
	 * @param e
	 *            MouseEvent to check
	 */
	public void checkClick(MouseEvent e) {
		for (Button but : buttons) {
			but.checkClick(e.getX(), e.getY());
		}
	}

	/**
	 * Runs the Page logic.
	 */
	public abstract void run();

	/**
	 * Paints the components of the Page.
	 * 
	 * @param g
	 *            Graphics to paint with
	 */
	public void paint(Graphics g) {
		for (Button but : buttons)
			but.draw(g);
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
		} catch (java.lang.NullPointerException ex) {
		}
		return false;
	}
}
