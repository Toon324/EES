package fbla;

import java.awt.BorderLayout;
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
	static JFrame frame;

	/**
	 * @param frame
	 */
	public AddEmployee() {
		super(new BorderLayout());
		JPanel textFields = new JPanel(new GridLayout(4,2));
		JPanel buttons = new JPanel(new GridLayout(1,2));
		
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
		textFields.add(firstName);
		textFields.add(lastName);
		textFields.add(phoneNum);
		textFields.add(cellNum);
		textFields.add(address);
		textFields.add(city);
		textFields.add(state);
		textFields.add(zip);
		buttons.add(cancel);
		buttons.add(finish);
		
		add(textFields);
		add(BorderLayout.PAGE_END,buttons);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		frame = new JFrame("Add Employee");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create and set up the content pane.
		AddEmployee popup = new AddEmployee();
		popup.setOpaque(true); // content panes must be opaque
		frame.setContentPane(popup);
		frame.pack();

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
			frame.dispose();
		else if (e.getActionCommand().equals("Finish"))
			writeDataAndExit();
	}

	private void writeDataAndExit() {
		try {
			//Creates file writer
			PrintWriter out = null;
			File file = new File("src\\fbla\\Resources\\Employees.txt");
			try {
				if (!file.exists())
					file.createNewFile();
				out = new PrintWriter(
						new FileWriter(file.getAbsoluteFile(),true)); //Append to current data
			} catch (IOException e) {
				System.out.println("Error creating output stream\n"
						+ System.getProperty("user.dir"));
				e.printStackTrace();
			}
			
			//Gets the last employee number
			Scanner scanner = new Scanner(file); 
			String l = "";
			while (scanner.hasNextLine())
				l = scanner.nextLine();
			Scanner lineScanner = new Scanner(l);
			int lastNum = lineScanner.nextInt();
			scanner.close();
			lineScanner.close();
				
			out.println((lastNum+1) + "\t" + firstName.getText() + "\t" + lastName.getText() + "\t"
					+ phoneNum.getText() + "\t" + cellNum.getText() + "\t"
					+ address.getText() + "\t" + city.getText() + "\t"
					+ state.getText() + "\t" + zip.getText());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			frame.dispose();
		}

	}

}
