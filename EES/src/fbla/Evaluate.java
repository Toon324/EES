/**
 * 
 */
package fbla;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/**
 * Allows user to evaluate an employee.
 * 
 * @author Cody Swendrowski
 */
@SuppressWarnings("serial")
public class Evaluate extends DataWindow {
	protected static JFrame frame = new JFrame(); // Frame to display

	private ArrayList<JComboBox> scoreBoxes = new ArrayList<JComboBox>();
	private JTextField nextEval;
	private String[] r = { "Yes", "No" };
	private JComboBox reccomend = new JComboBox(r);
	static int employeeNum;

	public Evaluate() {
		super(new BorderLayout());
		// Gives option to enter score from 1 to 5
		String[] scoreOptions = { Integer.toString(1), Integer.toString(2),
				Integer.toString(3), Integer.toString(4), Integer.toString(5) };

		// Panels that hold components of similar nature
		JPanel textPanel = new JPanel(new GridLayout(0, 1));
		JPanel scoresPanel = new JPanel(new GridLayout(0, 1));
		JPanel buttons = new JPanel();
		JPanel labelPanel = new JPanel(new GridLayout(0, 1));
		JPanel nextEvalPanel = new JPanel(new BorderLayout());

		// Adds scoreboxes to container
		scoreBoxes.add(new JComboBox(scoreOptions));
		scoreBoxes.add(new JComboBox(scoreOptions));
		scoreBoxes.add(new JComboBox(scoreOptions));
		scoreBoxes.add(new JComboBox(scoreOptions));
		scoreBoxes.add(new JComboBox(scoreOptions));

		try {
			nextEval = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (ParseException e) {
		}

		// Adds space to enter next eval date
		nextEvalPanel.add(BorderLayout.WEST, new JLabel(
				"Next Evaluation Date     "));
		nextEvalPanel.add(BorderLayout.CENTER, nextEval);

		// Adds textFields to container
		textFields.add(new JTextField(50));
		textFields.add(new JTextField(50));
		textFields.add(new JTextField(50));
		textFields.add(new JTextField(50));
		textFields.add(new JTextField(50));

		labels.add(new JLabel("Work Quality"));
		labels.add(new JLabel("Work Habits"));
		labels.add(new JLabel("Job Knowledge"));
		labels.add(new JLabel("Behavior"));
		labels.add(new JLabel("Overall Progress"));

		// Adds all container components to panels

		for (JTextField tf : textFields)
			textPanel.add(tf);

		for (JComboBox cb : scoreBoxes)
			scoresPanel.add(cb);

		for (JLabel l : labels)
			labelPanel.add(l);

		JButton cancel = new JButton("Cancel");
		JButton finish = new JButton("Finish");

		JLabel wouldReccomend = new JLabel("Reccomend?");

		cancel.addActionListener(this);
		finish.addActionListener(this);

		buttons.add(wouldReccomend);
		buttons.add(reccomend);
		buttons.add(cancel);
		buttons.add(finish);

		add(BorderLayout.PAGE_START, nextEvalPanel);
		add(BorderLayout.WEST, labelPanel);
		add(BorderLayout.EAST, textPanel);
		add(BorderLayout.CENTER, scoresPanel);
		// add(BorderLayout.CENTER, new JTextArea());
		add(BorderLayout.PAGE_END, buttons);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 * 
	 * @param n
	 *            Number of employee to evaluate
	 */
	public static void createAndShowGUI(int n) {
		employeeNum = n;
		// Create and set up the window.
		frame = new JFrame("Evaluate Employee "
				+ EES.getEmployeeName(employeeNum));
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Cancel"))
			frame.dispose();

		else if (e.getActionCommand().equals("Finish"))
			writeDataAndExit();
	}

	/**
	 * Uses a custom writer method to print out scores and comments.
	 */
	private void writeDataAndExit() {
		try {
			// Creates file writer
			PrintWriter evalOut = null;
			File eval = new File("src\\fbla\\Resources\\Evaluation Results.txt");

			evalOut = new PrintWriter(new FileWriter(eval.getAbsoluteFile(),
					true)); // Append to current data

			int lastEvalNum = -1;
			try {
				// Gets the last evaluation number
				Scanner scanner = new Scanner(eval);
				String l = "";
				while (scanner.hasNextLine())
					l = scanner.nextLine();
				Scanner lineScanner = new Scanner(l);
				lastEvalNum = lineScanner.nextInt();
				scanner.close();
				lineScanner.close();
			} catch (Exception e) {
			}

			if (lastEvalNum == -1) // No evaluations found
				lastEvalNum = 0;

			StringBuilder toWrite = new StringBuilder();
			toWrite.append((lastEvalNum + 1) + EES.delim);
			toWrite.append(employeeNum + EES.delim);

			// Calculates average score of evaluation
			int averageScore = 0;
			for (int x = 0; x < scoreBoxes.size() - 1; x++) {
				averageScore += java.lang.Integer.parseInt((String) scoreBoxes
						.get(x).getSelectedItem());
			}
			averageScore /= 5;

			// Fetches employerNum based off of employeeNum
			int employerNum = EES.getEmployerNum(employeeNum);
			if (employerNum == -1)
				toWrite.append("No Employer" + EES.delim);
			else
				toWrite.append(employerNum + EES.delim);

			// Gets current date
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
			Calendar cal = Calendar.getInstance();
			toWrite.append(dateFormat.format(cal.getTime()) + EES.delim);

			toWrite.append(nextEval.getText() + EES.delim);

			for (int x = 0; x < textFields.size(); x++) {
				toWrite.append(scoreBoxes.get(x).getSelectedItem() + EES.delim);
				
				//Replaces commas with fillers
				String text = textFields.get(x).getText();
				text = text.replace(",", "<comma>");
				
				toWrite.append(text + EES.delim);
				if (x == textFields.size() - 2) // Add average score before
												// general score
					toWrite.append(averageScore + EES.delim);
			}

			toWrite.append(reccomend.getSelectedItem());

			evalOut.println(toWrite.toString());

			evalOut.close();
		} catch (Exception e) {
		} finally {
			frame.dispose();
		}

	}

}
