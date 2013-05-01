package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/**
 * Generates a pop up window that accepts input, then writes employee data to
 * file and refreshes employees list.
 * 
 * @author Cody Swendrowski
 */
@SuppressWarnings("serial")
public class AddEmployee extends DataWindow {

	/**
	 * Creates a new input window for adding an employee.
	 */
	public AddEmployee() {
		super(new BorderLayout());

		// Panels that hold components of similar nature
		JPanel textPanel = new JPanel(new GridLayout(0, 1));
		JPanel labelPanel = new JPanel(new GridLayout(0, 1));
		JPanel buttons = new JPanel(new GridLayout(1, 2));

		// Creates new buttons allowing user to cancel or finish
		JButton finish = new JButton("Finish");
		JButton cancel = new JButton("Cancel");

		// Registers this object as the listener for clicks
		finish.addActionListener(this);
		cancel.addActionListener(this);

		// Adds buttons to panel
		buttons.add(cancel);
		buttons.add(finish);

		// Creates new text fields for input. Some are formatted with
		// MaskFormatter to limit what input is valid. For reference, # allows
		// any valid int from 0 to 9 to be inputed.
		try {
			textFields.add(new JTextField(20));
			textFields.add(new JTextField(20));
			textFields.add(new JTextField(20));
			textFields.add(new JFormattedTextField(new MaskFormatter(
					"(###) ###-####")));
			textFields.add(new JFormattedTextField(new MaskFormatter(
					"(###) ###-####")));
			textFields.add(new JTextField(20));
			textFields.add(new JTextField(20));
			textFields.add(new JTextField(20));
			textFields.add(new JFormattedTextField(new MaskFormatter("#####")));
		} catch (ParseException e) {
		}

		// Adds created textFields to panel
		for (JTextField tf : textFields)
			textPanel.add(tf);

		// Labels provide the user with an easy reference to what each field is
		// requesting.
		labels.add(new JLabel("First Name"));
		labels.add(new JLabel("Last Name"));
		labels.add(new JLabel("Email"));
		labels.add(new JLabel("Phone Number"));
		labels.add(new JLabel("Cell Number"));
		labels.add(new JLabel("Address"));
		labels.add(new JLabel("City"));
		labels.add(new JLabel("State"));
		labels.add(new JLabel("ZIP"));

		// Adds labels to panel
		for (JLabel l : labels)
			labelPanel.add(l);

		// Adds all panels to frame
		add(BorderLayout.WEST, labelPanel);
		add(BorderLayout.CENTER, textPanel);
		add(BorderLayout.PAGE_END, buttons);
		
		frame = EES.createAndShowGUI(this);
		frame.setTitle("Add Employee");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Cancel"))
			frame.dispose();

		// If user is happy with input, write the new data out then reload the
		// employees list.
		else if (e.getActionCommand().equals("Finish")) {
			for (int x = 0; x < textFields.size(); x++) {
				if (textFields.get(x).getText().length() == 0) {
					int n = JOptionPane.showConfirmDialog(this,
							"Not all data is inputted. Are you sure you want to create this Employee?", "Warning",
							JOptionPane.WARNING_MESSAGE);
					if (n == JOptionPane.CANCEL_OPTION)
						return;
				}
			}
			writeData(EES.employeesPath);
			Employees.reload();
			frame.dispose();
		}
	}
}
