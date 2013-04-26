package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Creates new window for displaying employee data.
 * 
 * @author Cody Swendrowski
 */
@SuppressWarnings("serial")
public class ViewEmployee extends DataWindow {
	protected static JFrame frame = new JFrame(); // Frame to display

	private static int employeeNum = -1;

	/**
	 * Creates new object for displaying employee data.
	 */
	public ViewEmployee() {
		super(new BorderLayout());

		JButton close = new JButton("Close");
		JButton viewEvals = new JButton("View Evaluations");
		
		close.addActionListener(this);
		viewEvals.addActionListener(this);
		
		JPanel buttons = new JPanel(new GridLayout(0,1));
		JPanel info = new JPanel(new GridLayout(0, 1));
		JPanel categories = new JPanel(new GridLayout(0, 1));

		buttons.add(viewEvals);
		buttons.add(close);
		
		// The method frame.pack() sets elements based on max width of contained
		// items. This string is larger to give a padding between data points.
		categories.add(new JLabel("Average Evaluation Score     "));
		categories.add(new JLabel("First Name"));
		categories.add(new JLabel("Last Name"));
		categories.add(new JLabel("Phone Num"));
		categories.add(new JLabel("Cell Num"));
		categories.add(new JLabel("Address"));
		categories.add(new JLabel("City"));
		categories.add(new JLabel("State"));
		categories.add(new JLabel("ZIP"));

		// Loads in employee data
		try {
			File file = new File(EES.employeesPath);
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Scanner lineScanner = new Scanner(line);
				lineScanner.useDelimiter(EES.delim);
				if (lineScanner.nextInt() == employeeNum)
					while (lineScanner.hasNext())
						info.add(new JLabel(lineScanner.next()));

				lineScanner.close();
			}

			scanner.close();
		} catch (Exception e) {
		}

		// Adds components to frame
		add(BorderLayout.WEST, categories);
		add(info);
		add(BorderLayout.PAGE_END, buttons);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	public static void createAndShowGUI(int n) {
		employeeNum = n;
		// Create and set up the window.
		frame = new JFrame("Displaying Employee "
				+ EES.getEmployeeName(employeeNum));
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create and set up the content pane.
		ViewEmployee popup = new ViewEmployee();
		popup.setOpaque(true); // content panes must be opaque
		frame.setContentPane(popup);
		frame.pack();
		// Display the window.
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Close"))
			frame.dispose();
		else if (e.getActionCommand().equals("View Evaluations"))
			ViewEvals.createAndShowGUI(employeeNum);
	}
}
