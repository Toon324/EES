package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/**
 * Allows the user to provide data, creating a new company.
 * 
 * @author Cody Swendrowski
 */
@SuppressWarnings("serial")
public class AddCompany extends DataWindow {

	/**
	 * Creates a new popup for adding a company.
	 */
	public AddCompany() {
		super(new BorderLayout());

		// Panels that hold components of similar nature
		JPanel labelPanel = new JPanel(new GridLayout(0, 1));
		JPanel textPanel = new JPanel(new GridLayout(0, 1));
		JPanel buttons = new JPanel(new GridLayout(1, 2));

		// Creates new text fields for input. Some are formatted with
		// MaskFormatter to limit what input is valid. For reference, # allows
		// any valid int from 0 to 9 to be inputed.
		try {
			textFields.add(new JTextField(20)); // Sets width for autosize
			textFields.add(new JTextField());
			textFields.add(new JTextField());
			textFields.add(new JTextField());
			textFields.add(new JFormattedTextField(new MaskFormatter("#####")));
			textFields.add(new JFormattedTextField(new MaskFormatter(
					"(###) ###-####")));
			textFields.add(new JTextField());
			textFields.add(new JTextField());
		} catch (Exception e) {
		}

		// Adds created textFields to panel
		for (JTextField tf : textFields)
			textPanel.add(tf);

		// Labels provide the user with an easy reference to what each field is
		// requesting.
		labels.add(new JLabel("Company Name    "));
		labels.add(new JLabel("Address"));
		labels.add(new JLabel("City"));
		labels.add(new JLabel("State"));
		labels.add(new JLabel("ZIP"));
		labels.add(new JLabel("Phone Number"));
		labels.add(new JLabel("Email Address"));
		labels.add(new JLabel("Contact Person"));

		// Adds labels to panel
		for (JLabel l : labels)
			labelPanel.add(l);

		// Creates new buttons allowing user to cancel or finish
		JButton cancel = new JButton("Cancel");
		JButton finish = new JButton("Finish");

		// Registers this object as the listener for clicks
		cancel.addActionListener(this);
		finish.addActionListener(this);

		// Adds buttons to panel
		buttons.add(cancel);
		buttons.add(finish);

		// Adds all panels to frame
		add(BorderLayout.WEST, labelPanel);
		add(BorderLayout.EAST, textPanel);
		add(BorderLayout.PAGE_END, buttons);

		frame = EES.createAndShowGUI(this);
		frame.setTitle("Add a Company");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Cancel"))
			frame.dispose();

		// If user is happy with input, write the new data out then reload the
		// companies list.
		else if (e.getActionCommand().equals("Finish")) {
			writeData(EES.employerPath);
			Companies.reload(); // Refresh the data table
			frame.dispose();
		}
	}

}
