package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
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
		JPanel textPanel = new JPanel(new GridLayout(4,2));
		JPanel buttons = new JPanel(new GridLayout(1,2));
		
		JButton finish = new JButton("Finish");
		finish.addActionListener(this);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		
		textFields.add(new JTextField("First Name", 20));
		textFields.add(new JTextField("Last Name", 20));
		textFields.add(new JTextField("Phone Number", 20));
		textFields.add(new JTextField("Cell Number", 20));
		textFields.add(new JTextField("Address", 20));
		textFields.add(new JTextField("City", 20));
		textFields.add(new JTextField("State", 20));
		try {
			textFields.add(new JFormattedTextField(new MaskFormatter("#####")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (JTextField tf : textFields)
			textPanel.add(tf);
		
		buttons.add(cancel);
		buttons.add(finish);
		
		add(textPanel);
		add(BorderLayout.PAGE_END,buttons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Cancel"))
			frame.dispose();
		else if (e.getActionCommand().equals("Finish")) {
			writeData("src\\fbla\\Resources\\Employees.txt");
			Employees.loadDataSource("src\\fbla\\Resources\\Employees.txt");
			frame.dispose();
		}
	}
	
	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	protected static void createAndShowGUI() {
		// Create and set up the window.
		frame = new JFrame("AddEmployee");
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
