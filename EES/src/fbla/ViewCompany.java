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
 * Displays data of selected company.
 * 
 * @author Cody Swendrowski
 */
@SuppressWarnings("serial")
public class ViewCompany extends DataWindow {

	private int employerNum = -1;

	/**
	 * Creates new object for viewing company data.
	 */
	public ViewCompany(int num) {
		super(new BorderLayout());
		
		employerNum = num;
		
		JButton close = new JButton("Close");
		close.addActionListener(this);

		JPanel info = new JPanel(new GridLayout(0, 1));
		JPanel categories = new JPanel(new GridLayout(0, 1));

		categories.add(new JLabel("Name"));
		// The method JFrame.pack() sets elements based on max width of
		// contained
		// items. This string is larger to give a padding between data points.
		categories.add(new JLabel("Phone Num                "));
		categories.add(new JLabel("Address"));
		categories.add(new JLabel("City"));
		categories.add(new JLabel("State"));
		categories.add(new JLabel("ZIP"));
		categories.add(new JLabel("Email"));
		categories.add(new JLabel("Contact Person"));

		// Loads in data
		try {
			File file = new File(EES.employerPath);
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

		// Adds components to frame
		add(BorderLayout.WEST, categories);
		add(info);
		add(BorderLayout.PAGE_END, close);
		
		frame = EES.createAndShowGUI(this);
		frame.setTitle("Displaying Company "
				+ EES.getEmployerName(employerNum));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Close"))
			frame.dispose();
	}
}
