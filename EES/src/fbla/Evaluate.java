package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/**
 * Allows user to evaluate an employee.
 * 
 * @author Cody Swendrowski
 */
@SuppressWarnings("serial")
public class Evaluate extends DataWindow {

	private ArrayList<JComboBox> scoreBoxes = new ArrayList<JComboBox>();
	private JTextField nextEval;
	private String[] r = { "Yes", "No" };
	private JComboBox reccomend = new JComboBox(r);
	private int employeeNum;

	/**
	 * Creates a new object that allows the user to evaluate an employee.
	 * 
	 * @param num
	 *            The number of the employee to evaluate
	 */
	public Evaluate(int num) {
		super(new BorderLayout());

		employeeNum = num;

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

		frame = EES.createAndShowGUI(this);
		frame.setTitle("Evaluate Employee " + EES.getEmployeeName(employeeNum));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Cancel"))
			frame.dispose();

		else if (e.getActionCommand().equals("Finish")) {
			for (int x = 0; x < textFields.size(); x++) {
				if (textFields.get(x).getText().length() == 0) {
					int n = JOptionPane
							.showConfirmDialog(
									this,
									"Not all data is inputted. Are you sure you want to submit this evaluation?",
									"Warning", JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.NO_OPTION)
						return;
				}
			}
			writeDataAndExit();
		}
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
				lastEvalNum = Integer.parseInt(lineScanner.next().replace(
						"﻿", ""));
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

				// Replaces commas with fillers
				String text = textFields.get(x).getText();
				text = text.replace(",", "<comma>");

				if (text.length() > 256)
					text.substring(0, 256); // makes sure data is only 256
											// characters long max

				toWrite.append(text + EES.delim);
				if (x == textFields.size() - 2) // Add average score before
												// general score
					toWrite.append(averageScore + EES.delim);
			}

			toWrite.append(reccomend.getSelectedItem());

			evalOut.println(toWrite.toString());

			evalOut.close();
			Employees.reload();
		} catch (Exception e) {
		} finally {
			frame.dispose();
		}

	}

}
