package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Allows user to view a specific evaluation.
 * 
 * @author Cody Swendrowski
 */
public class ViewEvaluation extends DataWindow {

	private static final long serialVersionUID = 3540312383716216831L;
	private int evalNum = -1;

	/**
	 * Creates a new window that shows the evaluation data.
	 * 
	 * @param n
	 *            Number of evaluation to display
	 */
	public ViewEvaluation(int n) {
		super(new BorderLayout());

		evalNum = n;

		File evaluationResults = new File(EES.evalPath);

		Scanner scanner;
		try {
			scanner = new Scanner(evaluationResults);
		} catch (FileNotFoundException e) {
			return;
		}
		scanner.useDelimiter(EES.delim);
		Scanner lineScanner = null;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			lineScanner = new Scanner(line);
			lineScanner.useDelimiter(EES.delim);

			String num = lineScanner.next();
			if (num.contains("﻿"))
				num = num.substring(3); // Remove ﻿ from String (byproduct of
										// being the first element)

			if (Integer.parseInt(num) == evalNum)
				break;
		}
		lineScanner.next(); // Discard employee number
		lineScanner.next(); // Discard employer number

		// Panels that hold components of similar nature
		JPanel textPanel = new JPanel(new GridLayout(0, 1));
		JPanel scoresPanel = new JPanel(new GridLayout(0, 1));
		JPanel buttons = new JPanel();
		JPanel labelPanel = new JPanel(new GridLayout(0, 1));

		// Adds textFields to container

		// Dates
		textFields.add(new JTextField(lineScanner.next()));
		textFields.add(new JTextField(lineScanner.next()));

		// Evaluation scores
		textFields.add(new JTextField(lineScanner.next() + " - "
				+ lineScanner.next().replace("<comma>", ",")));
		textFields.add(new JTextField(lineScanner.next() + " - "
				+ lineScanner.next().replace("<comma>", ",")));
		textFields.add(new JTextField(lineScanner.next() + " - "
				+ lineScanner.next().replace("<comma>", ",")));
		textFields.add(new JTextField(lineScanner.next() + " - "
				+ lineScanner.next().replace("<comma>", ",")));

		// Average Score
		textFields.add(new JTextField(lineScanner.next()));

		// Overall Score
		textFields.add(new JTextField(lineScanner.next() + " - "
				+ lineScanner.next().replace("<comma>", ",")));

		// Recommendation
		textFields.add(new JTextField(lineScanner.next()));

		labels.add(new JLabel("Evaluation Date"));
		labels.add(new JLabel("Next Evaluation Date"));
		labels.add(new JLabel("Work Quality"));
		labels.add(new JLabel("Work Habits"));
		labels.add(new JLabel("Job Knowledge"));
		labels.add(new JLabel("Behavior"));
		labels.add(new JLabel("Average Evaluation Score"));
		labels.add(new JLabel("Overall Progress"));
		labels.add(new JLabel("Reccomend?"));

		// Adds all container components to panels

		for (JTextField tf : textFields)
			textPanel.add(tf);

		for (JLabel l : labels)
			labelPanel.add(l);

		JButton cancel = new JButton("Exit");

		cancel.addActionListener(this);

		buttons.add(cancel);

		add(BorderLayout.WEST, labelPanel);
		add(BorderLayout.EAST, textPanel);
		add(BorderLayout.CENTER, scoresPanel);
		// add(BorderLayout.CENTER, new JTextArea());
		add(BorderLayout.PAGE_END, buttons);

		frame = EES.createAndShowGUI(this);
		frame.setTitle("Viewing Evaluation " + evalNum);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Exit"))
			frame.dispose();
	}

}
