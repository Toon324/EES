package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Creates new window for displaying employee data.
 * 
 * @author Cody Swendrowski
 */
@SuppressWarnings("serial")
public class ViewEmployee extends DataWindow {

	private int employeeNum = -1;

	/**
	 * Creates new object for displaying employee data.
	 */
	public ViewEmployee(int num) {
		super(new BorderLayout());

		employeeNum = num;

		JButton close = new JButton("Close");
		JButton viewEvals = new JButton("View Evaluations");

		close.addActionListener(this);
		viewEvals.addActionListener(this);

		JPanel buttons = new JPanel(new GridLayout(0, 1));
		JPanel info = new JPanel(new GridLayout(0, 1));
		JPanel categories = new JPanel(new GridLayout(0, 1));

		buttons.add(viewEvals);
		buttons.add(close);

		// The method frame.pack() sets elements based on max width of contained
		// items. This string is larger to give a padding between data points.
		categories.add(new JLabel("Average Evaluation Score     "));
		categories.add(new JLabel("First Name"));
		categories.add(new JLabel("Last Name"));
		categories.add(new JLabel("Email"));
		categories.add(new JLabel("Phone Num"));
		categories.add(new JLabel("Cell Num"));
		categories.add(new JLabel("Address"));
		categories.add(new JLabel("City"));
		categories.add(new JLabel("State"));
		categories.add(new JLabel("ZIP"));

		// Loads in employee data
		try {
			File file = new File(EES.employeesPath);
			File evals = new File(EES.evalPath);
			Scanner scanner = new Scanner(file);
			Scanner evalScanner = new Scanner(evals);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Scanner lineScanner = new Scanner(line);
				lineScanner.useDelimiter(EES.delim);
				int foundNum = lineScanner.nextInt();
				if (foundNum == employeeNum) {
					String evalAvg = "No Evaluations";
					while (evalScanner.hasNextLine()) {
						String tempLine = evalScanner.nextLine();
						
						Scanner avglineScanner = new Scanner(tempLine);
						avglineScanner.useDelimiter(EES.delim);
						
						avglineScanner.next(); //Discard evaluation number
						
						int numFound = Integer.parseInt(avglineScanner.next().replace("﻿", "")); //Employee number
						
						for (int x=0; x<11; x++)
							avglineScanner.next(); //Discard other eval info
						
						if (numFound == employeeNum) {
							evalAvg = avglineScanner.next();
							break;
						}
						avglineScanner.close();
					}
					
					info.add(new JLabel(evalAvg));
					evalScanner.close();
					
					while (lineScanner.hasNext())
						info.add(new JLabel(lineScanner.next()));

				}
				lineScanner.close();
			}

			scanner.close();
		} catch (Exception e) {
		}

		// Adds components to frame
		add(BorderLayout.WEST, categories);
		add(info);
		add(BorderLayout.PAGE_END, buttons);

		frame = EES.createAndShowGUI(this);
		frame.setTitle("Displaying Employee "
				+ EES.getEmployeeName(employeeNum));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Close"))
			frame.dispose();
		else if (e.getActionCommand().equals("View Evaluations"))
			new ViewEvals(employeeNum);
	}
}
