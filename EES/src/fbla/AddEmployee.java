package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/**
 * @author Cody
 * 
 */
@SuppressWarnings("serial")
public class AddEmployee extends DataInputWindow {


	/**
	 * @param frame
	 */
	public AddEmployee() {
		super(new BorderLayout());
		JPanel textPanel = new JPanel(new GridLayout(0,1));
		JPanel labelPanel = new JPanel(new GridLayout(0,1));
		JPanel buttons = new JPanel(new GridLayout(1,2));
		
		JButton finish = new JButton("Finish");
		finish.addActionListener(this);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		
		JTextField eval = new JTextField("0"); //Hidden field for writing purposes
		eval.setVisible(false);
		
		try {
		textFields.add(new JTextField(20));
		textFields.add(eval);
		textFields.add(new JTextField(20));
		textFields.add(new JFormattedTextField(new MaskFormatter("(###) ###-####")));
		textFields.add(new JFormattedTextField(new MaskFormatter("(###) ###-####")));
		textFields.add(new JTextField(20));
		textFields.add(new JTextField(20));
		textFields.add(new JTextField(20));
		textFields.add(new JFormattedTextField(new MaskFormatter("#####")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (JTextField tf : textFields)
			if (!tf.getText().equals("0")) //Does not display hidden field
				textPanel.add(tf);

		labels.add(new JLabel("First Name"));
		labels.add(new JLabel("Last Name"));
		labels.add(new JLabel("Phone Number"));
		labels.add(new JLabel("Cell Number"));
		labels.add(new JLabel("Address"));
		labels.add(new JLabel("City"));
		labels.add(new JLabel("State"));
		labels.add(new JLabel("ZIP"));
		
		for (JLabel l : labels) 
			labelPanel.add(l);
		
		buttons.add(cancel);
		buttons.add(finish);
		
		add(BorderLayout.WEST, labelPanel);
		add(BorderLayout.CENTER, textPanel);
		add(BorderLayout.PAGE_END,buttons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Cancel"))
			frame.dispose();
		else if (e.getActionCommand().equals("Finish")) {
			writeData("src\\fbla\\Resources\\Employees.txt");
			Employees.loadDataSource(Employees.employeesList, Employees.tableModel, Employees.data, "src\\fbla\\Resources\\Employees.txt");
			frame.dispose();
		}
	}
	
	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	protected static void createAndShowGUI() {
		// Create and set up the window.
		frame = new JFrame("Add Employee");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Display the window.
		frame.setVisible(true);
		
		// Create and set up the content pane.
		AddEmployee popup = new AddEmployee();
		popup.setOpaque(true); // content panes must be opaque
		frame.setContentPane(popup);
		frame.pack();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
