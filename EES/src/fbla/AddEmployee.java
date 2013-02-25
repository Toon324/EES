package fbla;

import java.awt.GridLayout;
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
public class AddEmployee extends JPanel implements ActionListener {

	JTextField firstName, lastName, phoneNum, cellNum, address, city, state,
			zip;
	JFrame frame;

	/**
	 * @param p
	 */
	public AddEmployee(JFrame frame) {
		super(new GridLayout(8, 1));
		this.frame = frame;
		firstName = new JTextField("First Name", 20);
		lastName = new JTextField("Last Name", 20);
		phoneNum = new JTextField("Phone Number", 20);
		cellNum = new JTextField("Cell Number", 20);
		address = new JTextField("Address", 20);
		city = new JTextField("City", 20);
		state = new JTextField("State", 20);
		zip = new JTextField("Zip Code", 20);
		JButton finish = new JButton("Finish");
		finish.addActionListener(this);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		add(firstName);
		add(lastName);
		add(phoneNum);
		add(cellNum);
		add(address);
		add(city);
		add(state);
		add(zip);
		add(cancel);
		add(finish);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Add Employee");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		AddEmployee popup = new AddEmployee(frame);
		popup.setOpaque(true); // content panes must be opaque
		frame.setContentPane(popup);

		// Display the window.
		frame.setVisible(true);
	}

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
			System.exit(0);
		else if (e.getActionCommand().equals("Finish"))
			;
		writeDataAndExit();
	}

	private void writeDataAndExit() {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(AddEmployee.class.getResource("Resources\\Employees.txt").getFile()));
			
			//Gets the last employee number
			Scanner scanner = new Scanner(AddEmployee.class.getResourceAsStream("Resources\\Employees.txt")); 
			String l = "";
			while (scanner.hasNextLine())
				l = scanner.nextLine();
			Scanner lineScanner = new Scanner(l);
			int lastNum = lineScanner.nextInt();
			System.out.println("Lastnum: " + lastNum);
			scanner.close();
			lineScanner.close();
				
			out.println(lastNum + "\t" + firstName.getText() + "\t" + lastName.getText() + "\t"
					+ phoneNum.getText() + "\t" + cellNum.getText() + "\t"
					+ address.getText() + "\t" + city.getText() + "\t"
					+ state.getText() + "\t" + zip.getText());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			System.exit(0);
		}

	}

}
