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
public class ViewEmployee extends DataInputWindow {

	static int employeeNum = -1;

	/**
	 * @param borderLayout
	 */
	public ViewEmployee() {
		super(new BorderLayout());
		JButton close = new JButton("Close");
		close.addActionListener(this);
		JPanel info = new JPanel(new GridLayout(0, 1));
		JPanel categories = new JPanel(new GridLayout(0, 1));

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

		try {
			File file = new File("src\\fbla\\Resources\\Employees.txt");
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

	public static void setEmployeeNum(int i) {
		employeeNum = i;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Close"))
			frame.dispose();
	}
}
