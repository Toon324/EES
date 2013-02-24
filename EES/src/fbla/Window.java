package fbla;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 * Starts the employee evaluation system (EES) and displays the Applet.
 * 
 * @author Cody Swendrowski
 */ 
public class Window {
	public static void main(String[] args) {
		// Sets up game
		EES ees = new EES();
		JFrame frame = new JFrame("Trivia");

		// Initializes game
		ees.init();

		// Add applet to frame
		frame.add(ees, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setResizable(false);
		frame.setVisible(true);

		// Runs game
		ees.run();
	}
}
