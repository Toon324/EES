/**
 * 
 */
package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

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
public class AddCompany extends DataInputWindow {

	/**
	 * 
	 */
	public AddCompany() {
		super(new BorderLayout());

		JPanel labelPanel = new JPanel(new GridLayout(0,1));
		JPanel textPanel = new JPanel(new GridLayout(0,1));
		JPanel buttons = new JPanel(new GridLayout(1, 2));

		try {
		textFields.add(new JTextField(20)); //Sets width for autosize
		textFields.add(new JTextField());
		textFields.add(new JTextField());
		textFields.add(new JTextField());
		textFields.add(new JFormattedTextField(new MaskFormatter("#####")));
		textFields.add(new JFormattedTextField(new MaskFormatter("(###) ###-####")));
		textFields.add(new JTextField());
		textFields.add(new JTextField());
		}
		catch (Exception e){}

		for (JTextField tf : textFields)
			textPanel.add(tf);
		
		labels.add(new JLabel("Company Name    "));
		labels.add(new JLabel("Address"));
		labels.add(new JLabel("City"));
		labels.add(new JLabel("State"));
		labels.add(new JLabel("ZIP"));
		labels.add(new JLabel("Phone Number"));
		labels.add(new JLabel("Email Address"));
		labels.add(new JLabel("Contact Person"));
		
		for (JLabel l : labels)
			labelPanel.add(l);

		JButton cancel = new JButton("Cancel");
		JButton finish = new JButton("Finish");

		cancel.addActionListener(this);
		finish.addActionListener(this);

		buttons.add(cancel);
		buttons.add(finish);

		add(BorderLayout.WEST, labelPanel);
		add(BorderLayout.EAST, textPanel);
		add(BorderLayout.PAGE_END, buttons);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	protected static void createAndShowGUI() {
		// Create and set up the window.
		frame = new JFrame("AddCompany");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Display the window.
		frame.setVisible(true);
		
		// Create and set up the content pane.
		AddCompany popup = new AddCompany();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Cancel"))
			frame.dispose();
		else if (e.getActionCommand().equals("Finish"))
			writeData("src\\fbla\\Resources\\Employer.txt");
			Employees.loadDataSource(Companies.companiesList, Companies.tableModel, Companies.data, "src\\fbla\\Resources\\Employer.txt");
			frame.dispose();
	}

}
