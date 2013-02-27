/**
 * 
 */
package fbla;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * @author Cody
 *
 */
@SuppressWarnings("serial")
public class Employees extends JPanel implements ActionListener {
	private static String[][] data = new String[0][0];
	static String[] names = {"Employee #", "First Name", "Last Name", "Phone #", "Cell #", "Address", "City", "State", "ZIP"};
	static JTable employeesList;
	static FblaTableModel tableModel;
	JScrollPane listScroller;
	static ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
	
	public Employees() {
		super(new BorderLayout());
		JPanel buttons = new JPanel(new GridLayout(2,2));
		
		JButton home = new JButton("Home");
		home.addActionListener(this);
		
		JButton addEmployee = new JButton("Add Employee");
		addEmployee.addActionListener(this);
		
		JButton evaluate = new JButton("Evaluate");
		evaluate.addActionListener(this);
		
		JButton view = new JButton("View Info");
		view.addActionListener(this);
		
		buttons.add(home);
		buttons.add(addEmployee);
		buttons.add(evaluate);
		buttons.add(view);

		tableModel = new FblaTableModel(data, names);
		employeesList = new JTable(tableModel);
		loadDataSource("src\\fbla\\Resources\\Employees.txt");
		employeesList.setAutoCreateRowSorter(true);
		
		//Puts list in a scroll pane
		listScroller = new JScrollPane(employeesList);
		
		add(listScroller);
		add(BorderLayout.PAGE_END, buttons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Home"))
			EES.cl.show(EES.pages, "Home");
		else if (e.getActionCommand().equals("Add Employee"))
			AddEmployee.main(null);
		else if (e.getActionCommand().equals("Evaluate") && (employeesList.getSelectedRow() != -1)) {
			Evaluate.main(null);
			Evaluate.setEmployeeNum(employeesList.getSelectedRow()+1);
		}
		//else if (e.getActionCommand().equals("View Info"));
			//ViewInfo.main(null);
		
	}
	
	public static void loadDataSource(String path) {
		input.clear();
		File file = new File(path);
		try {
			if (!file.exists())
				file.createNewFile();
			
			Scanner scanner = new Scanner(file); //Loads the .txt file
			scanner.useDelimiter("\t"); //Uses tab as an indicator that a new data segment is present. Can not use comma, as commas may be present in comments.
			while (scanner.hasNextLine()) {
				String temp = scanner.nextLine();
				ArrayList<String> line = new ArrayList<String>(); //Gets next line
				Scanner lineScanner = new Scanner(temp);
				lineScanner.useDelimiter("\t");
				while (lineScanner.hasNext()) {
					line.add(lineScanner.next());
				}
				lineScanner.close();
				input.add(line);
			}
			scanner.close();
		} catch (Exception e) {} 
		finally {
			//Store data
			data = new String[input.size()][10];
			for (int x=0; x<input.size(); x++) {
				int cnt=0;
				for (String s: input.get(x)) {
					data[x][cnt] = s;
					cnt++;
				}
			}
			tableModel.setData(data);
			employeesList.setModel(tableModel);
		}
	}
}
