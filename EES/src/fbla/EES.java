package fbla;

import java.applet.Applet;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * The class that controls and owns all necessary objects.
 * 
 * @author Cody Swendrowski
 */
public class EES extends Applet implements Runnable, MouseListener, MouseMotionListener, KeyListener {
	private static final long serialVersionUID = 42l;
	private Thread th; // app thread
	private Thread close; // Used for closing the app
	private PageController pc;

	/**
	 * Creates a new Trivia app.
	 * 
	 */
	public EES() {
		close = new Thread(new CloseHook(this));
		th = new Thread(this);
		Runtime.getRuntime().addShutdownHook(close);
		pc = new PageController();
	}

	/**
	 * Called when app is first initialized. Sets all values and objects to
	 * default state, and allows this class to listen to Mouse and Keyboard
	 * input.
	 */
	public void init() {
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
	}

	/**
	 * A thread used to close the app correctly.
	 * 
	 * @author Cody Swendrowski
	 */
	public class CloseHook implements Runnable {
		EES e;

		public CloseHook(EES ees) {
			e = ees;
		}

		@Override
		public void run() {
			e.onClose(); // Closes the app from a new thread to avoid errors
			try {
				this.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Starts the app thread.
	 */
	public void start() {
		th.start();
	}

	/**
	 * Called to run the app. Will continue running until app is closed. All
	 * app logic is called from here.
	 */
	public synchronized void run() {
		// run until stopped
		while (true) {
			// controls app flow
			pc.run();
			// repaint applet
			repaint();

			try {
				wait(); // wait for applet to draw
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	/**
	 * Updates the graphics of the app using a double buffer system to avoid
	 * screen flicker.
	 */
	public void update(Graphics g) {
		// Start buffer
		Image dbImage = createImage(getWidth(), getHeight());
		Graphics dbg = dbImage.getGraphics();

		// Clear screen in background
		dbg.setColor(getBackground());
		dbg.fillRect(0, 0, getWidth(), getHeight());

		// Draw app in background
		dbg.setColor(getForeground());
		paint(dbg);

		// Draw Image on screen
		g.drawImage(dbImage, 0, 0, this);
	}

	/**
	 * Only called from update(Graphics g). Paints all objects and menus in the
	 * app.
	 */
	public void paint(Graphics g) {
		pc.paint(g);
		super.paint(g);
		synchronized (this) {
			notifyAll(); // Lets the run() method know that painting is
							// completed
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		pc.checkClick(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	/**
	 * Called when app exits. Properly closes resources.
	 */
	public void onClose() {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (pc.isOver(e))
			setCursor(new Cursor(Cursor.HAND_CURSOR)); // If mouse is over a
														// Button, change to
														// hand cursor
		else
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // If it isn't, change
															// back to default
	}
}
