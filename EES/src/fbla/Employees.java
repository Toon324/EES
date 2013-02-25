/**
 * 
 */
package fbla;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 * @author Cody
 *
 */
@SuppressWarnings("serial")
public class Employees extends JPanel implements ActionListener {
	private Object[] data = new Object[0];
	ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
	
	public Employees() {
		super(new GridLayout(3,1));
		JButton home = new JButton("Home");
		home.addActionListener(this);
		JButton addEmployee = new JButton("Add Employee");
		addEmployee.addActionListener(this);
		JList employeesList = new JList();
		employeesList.setVisibleRowCount(10);
		employeesList.setSize(300, 500);
		loadDataSource("Resources\\Employees.txt");
		employeesList.setListData(data);
		add("North", employeesList);
		add(home);
		add(addEmployee);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Home"))
			EES.cl.show(EES.pages, "Home");
		if (e.getActionCommand().equals("Add Employee"))
			AddEmployee.main(null);
		
	}
	
	public void loadDataSource(String path) {
		Scanner scanner = new Scanner(ListBox.class.getResourceAsStream(path)); //Loads the .txt file
		scanner.useDelimiter("\t"); //Uses tab as an indicator that a new data segment is present. Can not use comma, as commas may be present in comments.
		
		try {
			while (scanner.hasNextLine()) {
				String temp = scanner.nextLine();
				ArrayList<String> line = new ArrayList<String>(); //Gets next line
				Scanner lineScanner = new Scanner(temp);
				lineScanner.useDelimiter("\t");
				while (lineScanner.hasNext()) {
					String s = lineScanner.next(); //Gets each section of the line
					line.add(s);
				}
				input.add(line);
			}
		} catch (Exception e) {} 
		finally {
			scanner.close();
			dataSort(0);
		}
	}

	private void dataSort(int i) {
		
		data = new Object[input.size()];
		for (int x=0; x<input.size(); x++) {
			StringBuilder line = new StringBuilder();
			for (String s: input.get(x))
				line.append(s + " ");
			data[x] = line;
		}
		//data = input.toArray();
		
	}
}
