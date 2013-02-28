/**
 * 
 */
package fbla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * @author Cody
 * 
 */
@SuppressWarnings("serial")
public class AddToCompany extends DataInputWindow {

	static int employerNum = -1;
	static JTable employeesList;

	/**
	 * @param borderLayout
	 */
	public AddToCompany() {
		super(new BorderLayout());
		
		JPanel buttons = new JPanel(new GridLayout(1,2));
		
		JButton finish = new JButton("Finish");
		finish.addActionListener(this);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		
		buttons.add(cancel);
		buttons.add(finish);
		
		FblaTableModel tableModel = new FblaTableModel(Employees.getData(), Employees.getNames());
		employeesList = new JTable(tableModel);
		Employees.loadDataSource(employeesList, tableModel, Employees.getData(), "src\\fbla\\Resources\\Employees.txt");
		JScrollPane listScroller = new JScrollPane(employeesList);
		
		add(BorderLayout.CENTER, listScroller);
		add(BorderLayout.PAGE_END, buttons);
	}

	/**
	 * @param args
	 */
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
		else if (e.getActionCommand().equals("Finish")) {
			writeData("src\\fbla\\Resources\\Field Placements.txt");
			frame.dispose();
		}
	}
	
	//Overridden to write Employee selection
	protected void writeData(String path) {
		try {
			//Creates file writer
			PrintWriter out = null;
			File file = new File(path);
			try {
				if (!file.exists())
					file.createNewFile();
				out = new PrintWriter(
						new FileWriter(file.getAbsoluteFile(),true)); //Append to current data
			} catch (IOException e) {}
			out.println(employeesList.getModel().getValueAt(employeesList.getSelectedRow(), 0) + "\t" + employerNum);
			//System.out.println("Printed: " + list.getModel().getValueAt(list.getSelectedRow(), 0) + "\t" + employerNum);
			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	protected static void createAndShowGUI() {
		// Create and set up the window.
		frame = new JFrame("Select Employee to add to "
				+ EES.getEmployerName(employerNum));
		frame.setSize(500, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Display the window.
		frame.setVisible(true);

		// Create and set up the content pane.
		AddToCompany popup = new AddToCompany();
		popup.setOpaque(true); // content panes must be opaque
		frame.setContentPane(popup);
		frame.pack();
	}

	public static void setEmployerNum(int n) {
		employerNum = n;
	}

}
