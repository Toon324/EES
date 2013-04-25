package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Creates new window that shows a table of all evaluations for a given
 * employee.
 * 
 * @author Cody Swendrowski
 */
@SuppressWarnings("serial")
public class ViewEvals extends DataWindow {
	protected static JFrame frame = new JFrame(); // Frame to display

	private static String[][] data = new String[0][0];

	private static String[] names = { "Evaluation Number", "Date", "Next Evaluation",
			"Work Quality", "Work Quality Comments", "Work Habits",
			"Work Habits Comments", "Job Knowledge", "Job Knowledge Comments",
			"Behavior", "Behavior Comments", "Average Score", "Overall",
			"Overall Comments", "Recommendation" };

	private static JTable evals;

	private static int employeeNum = -1;

	public ViewEvals() {
		super(new BorderLayout());
		// Panel that holds components of similar nature
		JPanel buttons = new JPanel(new GridLayout(2, 1));

		JButton close = new JButton("Close");
		JButton view = new JButton("View Evaluation");

		view.addActionListener(this);
		close.addActionListener(this);
		
		buttons.add(view);
		buttons.add(close);

		FblaTableModel tableModel = new FblaTableModel(data, names);
		evals = new JTable(tableModel);
		loadDataSource(EES.evalPath);
		evals.setAutoCreateRowSorter(true);

		// Puts list in a scroll pane
		JScrollPane listScroller = new JScrollPane(evals);

		// Add components to frame
		add(listScroller);
		add(BorderLayout.PAGE_END, buttons);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 * 
	 * @param n
	 *            Number of Employee to display evals of
	 */
	public static void createAndShowGUI(int n) {
		employeeNum = n;
		// Create and set up the window.
		frame = new JFrame("Viewing Evaluations for "
				+ EES.getEmployeeName(employeeNum));
		frame.setSize(800, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create and set up the content pane.
		ViewEvals popup = new ViewEvals();
		popup.setOpaque(true); // content panes must be opaque
		frame.setContentPane(popup);

		// Display the window.
		frame.setVisible(true);
	}

	/**
	 * Uses a custom data loader method to load only the evals that match the
	 * employee number.
	 * 
	 * @param path
	 *            Path to evaluation results
	 */
	public static void loadDataSource(String path) {
		ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
		File file = new File(path);
		boolean oneFound = false;
		try {
			if (!file.exists())
				file.createNewFile();

			Scanner scanner = new Scanner(file); // Loads the .txt file

			while (scanner.hasNextLine()) {
				String temp = scanner.nextLine();
				ArrayList<String> line = new ArrayList<String>();
				Scanner lineScanner = new Scanner(temp);
				lineScanner.useDelimiter(EES.delim);

				String num = "" + lineScanner.next();
				if (num.contains("﻿"))
					num = num.substring(3); //Remove ﻿ from String (byproduct of being the first element)
				line.add(num); // Store eval number
				int empNum = lineScanner.nextInt();

				if (empNum == employeeNum) {
					oneFound = true;

					lineScanner.next(); // Discard employer number
					for (int x = 0; x < names.length - 1; x++) {
						String l = lineScanner.next();
						// Replace any fillers with actual char
						l = l.replace("<comma>", ",");
						line.add(l);
					}

					input.add(line);
				}

				lineScanner.close();
			}

			scanner.close();
		} catch (Exception e) {
		} finally {
			// Store data
			if (!oneFound)
				return;

			data = new String[input.size()][input.get(0).size()];
			for (int x = 0; x < input.size(); x++) {
				int cnt = 0;
				for (String s : input.get(x)) {
					data[x][cnt] = s;
					cnt++;
				}
			}

			// Sets data for Eval table
			((FblaTableModel) evals.getModel()).setData(data);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		if (e.getActionCommand().equals("Close"))
			frame.dispose();
		else if (e.getActionCommand().equals("View Evaluation"))
			ViewEvaluation.createAndShowGUI(EES.getSelectedNum(evals, "Evaluation Number"));
	}

}
