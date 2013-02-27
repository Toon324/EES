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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Cody
 *
 */
@SuppressWarnings("serial")
public class Evaluate extends DataInputWindow {

	ArrayList<JComboBox<Object>> scoreBoxes = new ArrayList<JComboBox<Object>>();
	JTextField nextEval;
	String[] r = {"Yes", "No"};
	JComboBox<Object> reccomend = new JComboBox<Object>(r);
	static int employeeNum;
	
	public Evaluate() {
		super(new BorderLayout());
		String[] scoreOptions = { Integer.toString(1), Integer.toString(2), Integer.toString(3), Integer.toString(4), Integer.toString(5)};
		JPanel textPanel = new JPanel(new GridLayout(0,1));
		JPanel scoresPanel = new JPanel(new GridLayout(0,1));
		JPanel buttons = new JPanel();
		
		scoreBoxes.add(new JComboBox<Object>(scoreOptions));
		scoreBoxes.add(new JComboBox<Object>(scoreOptions));
		scoreBoxes.add(new JComboBox<Object>(scoreOptions));
		scoreBoxes.add(new JComboBox<Object>(scoreOptions));
		scoreBoxes.add(new JComboBox<Object>(scoreOptions));
		
		nextEval = new JTextField("Next Evaluation Date", 20);
		add(BorderLayout.PAGE_START, nextEval);
		
		textFields.add(new JTextField("Work Quality Comments", 50));
		textFields.add(new JTextField("Work Habits Comments", 50));
		textFields.add(new JTextField("Job Knowledge Comments", 50));
		textFields.add(new JTextField("Behavior Comments", 50));
		textFields.add(new JTextField("Overall Progress Comments", 50));
		
		Font text = new Font("Arial", Font.PLAIN, 12);
		
		for (JTextField tf : textFields) {
			tf.setFont(text);
			textPanel.add(tf);
		}
		
		for(JComboBox<?> cb : scoreBoxes)
			scoresPanel.add(cb);

		JButton cancel = new JButton("Cancel");
		JButton finish = new JButton("Finish");
	
		JLabel wouldReccomend = new JLabel("Reccomend?");

		cancel.addActionListener(this);
		finish.addActionListener(this);

		buttons.add(wouldReccomend);
		buttons.add(reccomend);
		buttons.add(cancel);
		buttons.add(finish);

		add(BorderLayout.EAST, textPanel);
		add(BorderLayout.WEST, scoresPanel);
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
			File file = new File("src\\fbla\\Resources\\Evaluation Results.txt");
			try {
				if (!file.exists())
					file.createNewFile();
				out = new PrintWriter(
						new FileWriter(file.getAbsoluteFile(),true)); //Append to current data
			} catch (IOException e) {}
			
			int lastEvalNum = -1;
			try {

			//Gets the last employee number
			Scanner scanner = new Scanner(file); 
			String l = "";
			while (scanner.hasNextLine())
				l = scanner.nextLine();
			Scanner lineScanner = new Scanner(l);
			lastEvalNum = lineScanner.nextInt();
			scanner.close();
			lineScanner.close();
			}
			catch (Exception e) {}
				
			if (lastEvalNum == -1) //No evaluations found
				lastEvalNum = 0;
			StringBuilder toWrite = new StringBuilder();
			toWrite.append((lastEvalNum +1) + "\t");
			toWrite.append(employeeNum + "\t");
			
			//Fetches employerNum based off of employeeNum
			int employerNum = EES.getEmployerNum(employeeNum);
			if (employerNum == -1)
				toWrite.append("No Employer" + "\t");
			else
				toWrite.append(employerNum + "\t");
			
			//Gets current date
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
			Calendar cal = Calendar.getInstance();
			toWrite.append(dateFormat.format(cal.getTime()) + "\t");
			
			toWrite.append(nextEval.getText() + "\t");
			
			for (int x=0; x<textFields.size(); x++) {
				toWrite.append(scoreBoxes.get(x).getSelectedItem() + "\t");
				toWrite.append(textFields.get(0).getText() + "\t");
			}
			
			toWrite.append(reccomend.getSelectedItem());
			
			out.println(toWrite.toString());
			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			frame.dispose();
		}

	}

}
