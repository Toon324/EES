/**
 * 
 */
package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Cody
 * 
 */
@SuppressWarnings("serial")
public class ViewCompany extends DataInputWindow {

	static int employerNum = -1;

	/**
	 * @param borderLayout
	 */
	public ViewCompany() {
		super(new BorderLayout());
		JButton close = new JButton("Close");
		close.addActionListener(this);
		JPanel info = new JPanel(new GridLayout(0, 1));
		JPanel categories = new JPanel(new GridLayout(0, 1));

		categories.add(new JLabel("Name"));
		// The method frame.pack() sets elements based on max width of contained
		// items. This string is larger to give a padding between data points.
		categories.add(new JLabel("Phone Num                "));
		categories.add(new JLabel("Address"));
		categories.add(new JLabel("City"));
		categories.add(new JLabel("State"));
		categories.add(new JLabel("ZIP"));
		categories.add(new JLabel("Email"));
		categories.add(new JLabel("Contact Person"));

		try {
			File file = new File("src\\fbla\\Resources\\Employer.txt");
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Scanner lineScanner = new Scanner(line);
				lineScanner.useDelimiter(EES.delim);
				if (lineScanner.nextInt() == employerNum)
					while (lineScanner.hasNext())
						info.add(new JLabel(lineScanner.next()));

				lineScanner.close();
			}

			scanner.close();
		} catch (Exception e) {
		}

		add(BorderLayout.WEST, categories);
		add(info);
		add(BorderLayout.PAGE_END, close);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		frame = new JFrame("Displaying Company " + EES.getEmployerName(employerNum));
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create and set up the content pane.
		ViewCompany popup = new ViewCompany();
		popup.setOpaque(true); // content panes must be opaque
		frame.setContentPane(popup);
		frame.pack();
		// Display the window.
		frame.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	public static void setEmployerNum(int i) {
		employerNum = i;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Close"))
			frame.dispose();
	}
}