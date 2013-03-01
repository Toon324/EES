package fbla;

import java.awt.BorderLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * @author Cody
 *
 */
@SuppressWarnings("serial")
public class Employees extends JPanel implements ActionListener {
	static String[][] data = new String[0][0];
	static String[] names = {"Employee Number", "First Name", "Last Name", "Phone #", "Cell #", "Address", "City", "State", "ZIP"};
	static JTable employeesList;
	static FblaTableModel tableModel;
	static JScrollPane listScroller;
	static ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
	
	public Employees() {
		super(new BorderLayout());
		JPanel buttons = new JPanel(new GridLayout(2,2));
		
		JButton home = new JButton("Home");
		home.addActionListener(this);
		
		JButton addEmployee = new JButton("Add Employee");
		addEmployee.addActionListener(this);
		
		JButton evaluate = new JButton("Evaluate Employee");
		evaluate.addActionListener(this);
		
		JButton view = new JButton("View Employee");
		view.addActionListener(this);
		
		JButton delete = new JButton("Delete Employee");
		delete.addActionListener(this);
		
		JButton viewEvals = new JButton("View Evaluations");
		viewEvals.addActionListener(this);
		
		buttons.add(home);
		buttons.add(addEmployee);
		buttons.add(evaluate);
		buttons.add(view);
		buttons.add(delete);
		buttons.add(viewEvals);

		tableModel = new FblaTableModel(data, names);
		employeesList = new JTable(tableModel);
		loadDataSource(employeesList, tableModel, data, "src\\fbla\\Resources\\Employees.txt");
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
		else if (e.getActionCommand().equals("Evaluate Employee") && (getSelectedNum(employeesList, "Employee Number") != -1)) {
			Evaluate.main(null);
			Evaluate.setEmployeeNum(getSelectedNum(employeesList, "Employee Number"));
		}
		else if (e.getActionCommand().equals("View Employee") && (getSelectedNum(employeesList, "Employee Number") != -1)) {
			ViewEmployee.main(null);
			ViewEmployee.setEmployeeNum(getSelectedNum(employeesList, "Employee Number"));
		}
		else if (e.getActionCommand().equals("View Evaluations") && (getSelectedNum(employeesList, "Employee Number") != -1)) {
			ViewEvals.main(null);
			ViewEvals.setEmployeeNum(getSelectedNum(employeesList, "Employee Number"));
		}
		else if (e.getActionCommand().equals("Delete Employee") && (getSelectedNum(employeesList, "Employee Number") != -1)) {
			int n = JOptionPane.showConfirmDialog(
                    this, "Are you sure you want to delete this employee? This action is irreversable.",
                    "Confirmation of deletion",
                    JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                delete(getSelectedNum(employeesList, "Employee Number"));
                loadDataSource(employeesList, tableModel, data, "src\\fbla\\Resources\\Employees.txt");
            }
            }
		
	}
	
	private void delete(int employeeNum) {
		ArrayList<String> toWrite = new ArrayList<String>();
		File file = new File("src\\fbla\\Resources\\Employees.txt");
		try {
			if (!file.exists())
				file.createNewFile();
			
			Scanner scanner = new Scanner(file); //Loads the .txt file
			while (scanner.hasNextLine()) {
				String temp = scanner.nextLine();
				
				Scanner lineScanner = new Scanner(temp);
				lineScanner.useDelimiter(EES.delim);
				
				if (lineScanner.nextInt() != employeeNum)
					toWrite.add(temp);
				
				lineScanner.close();
			}
			scanner.close();
		} catch (Exception e) { e.printStackTrace();} 
		finally {
			//Write updated file
			
			try {
				PrintWriter out = new PrintWriter(new FileWriter(file));
				for (String s : toWrite)
					out.println(s);
				out.close();
			} catch (IOException e) {
			}
			
		}
		
	}

	public static String[][] getData() {
		return data;
	}
	
	public static String[] getNames() {
		return names;
	}
	
	public static int getSelectedNum(JTable table, String nameOfNum) {
		int col = -1;
		for (int x=0; x<table.getColumnCount(); x++)
			if (table.getColumnName(x).equals(nameOfNum))
				col = x;
		if (col == -1)
			return -1;
		String s = (String) table.getModel().getValueAt(table.getSelectedRow(), col);
		int i = java.lang.Integer.parseInt(s);
		return i;
	}
	
	public static void loadDataSource(JTable table, FblaTableModel model, String[][] storage, String path) {
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
			storage = new String[input.size()][input.get(0).size()];
			for (int x=0; x<input.size(); x++) {
				int cnt=0;
				for (String s: input.get(x)) {
					storage[x][cnt] = s;
					cnt++;
				}
			}
			model.setData(storage);
			table.setModel(model);
		}
	}
}
