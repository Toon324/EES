package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
public class ViewEvaluation extends DataWindow {
	protected static JFrame frame = new JFrame(); // Frame to display

	/**
	 * 
	 */
	private static final long serialVersionUID = 3540312383716216831L;

	private static int evalNum = -1;

	/**
	 * @param borderLayout
	 */
	public ViewEvaluation() {
		super(new BorderLayout());

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
		
		//Evaluation scores
		textFields.add(new JTextField(lineScanner.next() + " - "
				+ lineScanner.next().replace("<comma>", ",")));
		textFields.add(new JTextField(lineScanner.next() + " - "
				+ lineScanner.next().replace("<comma>", ",")));
		textFields.add(new JTextField(lineScanner.next() + " - "
				+ lineScanner.next().replace("<comma>", ",")));
		textFields.add(new JTextField(lineScanner.next() + " - "
				+ lineScanner.next().replace("<comma>", ",")));
		
		//Average Score
		textFields.add(new JTextField(lineScanner.next()));
		
		//Overall Score
		textFields.add(new JTextField(lineScanner.next() + " - "
				+ lineScanner.next().replace("<comma>", ",")));
		
		//Recommendation
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fbla.DataWindow#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Exit"))
			frame.dispose();
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 * 
	 * @param n
	 *            Number of employee to evaluate
	 */
	public static void createAndShowGUI(int n) {
		evalNum = n;
		// Create and set up the window.
		frame = new JFrame("Viewing Evaluation " + evalNum);
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create and set up the content pane.
		ViewEvaluation popup = new ViewEvaluation();
		popup.setOpaque(true); // content panes must be opaque
		frame.setContentPane(popup);
		frame.pack();

		// Display the window.
		frame.setVisible(true);
	}

}
