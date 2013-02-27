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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

		JPanel textPanel = new JPanel(new GridLayout(4, 2));
		JPanel buttons = new JPanel(new GridLayout(1, 2));

		textFields.add(new JTextField("Name", 20));
		textFields.add(new JTextField("Address", 20));
		textFields.add(new JTextField("City", 20));
		textFields.add(new JTextField("State", 20));
		textFields.add(new JTextField("ZIP", 20));
		textFields.add(new JTextField("Phone Number", 20));
		textFields.add(new JTextField("Email Address", 20));
		textFields.add(new JTextField("Contact Person", 20));

		for (JTextField tf : textFields)
			textPanel.add(tf);

		JButton cancel = new JButton("Cancel");
		JButton finish = new JButton("Finish");

		cancel.addActionListener(this);
		finish.addActionListener(this);

		buttons.add(cancel);
		buttons.add(finish);

		add(textPanel);
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
			Companies.loadDataSource("src\\fbla\\Resources\\Employer.txt");
			frame.dispose();
	}

}
