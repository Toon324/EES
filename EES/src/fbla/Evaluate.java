/**
 * 
 */
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
public class Evaluate extends DataInputWindow {

	static int employeeNum;
	
	public Evaluate() {
		super(new BorderLayout());
		JPanel textPanel = new JPanel(new GridLayout(0,2));
		JPanel buttons = new JPanel();
		
		textFields.add(new JTextField("Current Evaluation Date", 20));
		textFields.add(new JTextField("Next Evaluation Date", 20));
		textFields.add(new JTextField("Work Quality Score", 10));
		textFields.add(new JTextField("Work Quality Comments", 50));
		textFields.add(new JTextField("Work Habits Score", 10));
		textFields.add(new JTextField("Work Habits Comments", 50));
		textFields.add(new JTextField("Job Knowledge Score", 10));
		textFields.add(new JTextField("Job Knowledge Comments", 50));
		textFields.add(new JTextField("Behavior Score", 10));
		textFields.add(new JTextField("Behavior Comments", 50));
		textFields.add(new JTextField("Overall Progress Score", 10));
		textFields.add(new JTextField("Overall Progress Comments", 50));
		textFields.add(new JTextField("Would you reccomend?", 20));
		
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
	private static void createAndShowGUI() {
		// Create and set up the window.
		frame = new JFrame("Evaluate Employee " + employeeNum);
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create and set up the content pane.
		Evaluate popup = new Evaluate();
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Cancel"))
			frame.dispose();
		else if (e.getActionCommand().equals("Finish"))
			writeDataAndExit();
	}
	
	public static void setEmployeeNum(int num) {
		employeeNum = num;
	}
	
	private void writeDataAndExit() {
		try {
			//Creates file writer
			PrintWriter out = null;
			File file = new File("src\\fbla\\Resources\\Employer.txt");
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
				
			/*out.println((lastNum+1) + "\t" + name.getText() + "\t" + address.getText() + "\t"
					+ city.getText() + "\t" + state.getText() + "\t"
					+ zip.getText() + "\t" + phoneNum.getText() + "\t"
					+ email.getText() + "\t" + contact.getText());*/
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			frame.dispose();
		}

	}

}
