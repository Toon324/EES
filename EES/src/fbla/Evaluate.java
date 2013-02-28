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
 * @author Cody
 * 
 */
@SuppressWarnings("serial")
public class Evaluate extends DataInputWindow {

	ArrayList<JComboBox> scoreBoxes = new ArrayList<JComboBox>();
	JTextField nextEval;
	String[] r = { "Yes", "No" };
	JComboBox reccomend = new JComboBox(r);
	static int employeeNum;

	public Evaluate() {
		super(new BorderLayout());
		String[] scoreOptions = { Integer.toString(1), Integer.toString(2),
				Integer.toString(3), Integer.toString(4), Integer.toString(5) };
		JPanel textPanel = new JPanel(new GridLayout(0, 1));
		JPanel scoresPanel = new JPanel(new GridLayout(0, 1));
		JPanel buttons = new JPanel();
		JPanel labelPanel = new JPanel(new GridLayout(0, 1));
		JPanel nextEvalPanel = new JPanel(new BorderLayout());

		scoreBoxes.add(new JComboBox(scoreOptions));
		scoreBoxes.add(new JComboBox(scoreOptions));
		scoreBoxes.add(new JComboBox(scoreOptions));
		scoreBoxes.add(new JComboBox(scoreOptions));
		scoreBoxes.add(new JComboBox(scoreOptions));

		try {
			nextEval = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		nextEvalPanel.add(BorderLayout.WEST, new JLabel(
				"Next Evaluation Date     "));
		nextEvalPanel.add(BorderLayout.CENTER, nextEval);

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
	 */
	private static void createAndShowGUI() {
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
			// Creates file writer
			PrintWriter evalOut = null;
			PrintWriter empOut = null;
			File eval = new File("src\\fbla\\Resources\\Evaluation Results.txt");
			File employee = new File("src\\fbla\\Resources\\Employees.txt");
			
			evalOut = new PrintWriter(new FileWriter(eval.getAbsoluteFile(), true)); // Append
																					// to
																					// current
																					// data
			empOut = new PrintWriter(new FileWriter(employee.getAbsoluteFile(), true));

			int lastEvalNum = -1;
			int lastAvgScore = 0;
			try {
				// Gets the last evaluation number
				Scanner scanner = new Scanner(eval);
				String l = "";
				while (scanner.hasNextLine())
					l = scanner.nextLine();
				Scanner lineScanner = new Scanner(l);
				lastEvalNum = lineScanner.nextInt();
				
				scanner = new Scanner(employee);
				while (scanner.hasNextLine()) {
					l = scanner.nextLine();
					lineScanner = new Scanner(l);
					
					if (lineScanner.nextInt() == employeeNum)
						lastAvgScore = lineScanner.nextInt();
				}
				
				scanner.close();
				lineScanner.close();
			} catch (Exception e) {
			}
			
			if (lastEvalNum == -1) // No evaluations found
				lastEvalNum = 0;

			StringBuilder toWrite = new StringBuilder();
			toWrite.append((lastEvalNum + 1) + "\t");
			toWrite.append(employeeNum + "\t");

			int averageScore = 0;
			for (int x = 0; x < scoreBoxes.size() - 1; x++) {
				averageScore += java.lang.Integer.parseInt((String) scoreBoxes
						.get(x).getSelectedItem());
			}
			averageScore /= 5;

			toWrite.append(((lastAvgScore + averageScore) / 2) + "\t"); // Writes
																		// the
																		// average
																		// score

			// Fetches employerNum based off of employeeNum
			int employerNum = EES.getEmployerNum(employeeNum);
			if (employerNum == -1)
				toWrite.append("No Employer" + "\t");
			else
				toWrite.append(employerNum + "\t");

			// Gets current date
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
			Calendar cal = Calendar.getInstance();
			toWrite.append(dateFormat.format(cal.getTime()) + "\t");

			toWrite.append(nextEval.getText() + "\t");

			for (int x = 0; x < textFields.size(); x++) {
				toWrite.append(scoreBoxes.get(x).getSelectedItem() + "\t");
				toWrite.append(textFields.get(x).getText() + "\t");
			}

			toWrite.append(reccomend.getSelectedItem());

			evalOut.println(toWrite.toString());

			evalOut.close();
			empOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			frame.dispose();
		}

	}

}
