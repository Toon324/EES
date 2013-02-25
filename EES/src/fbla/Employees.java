/**
 * 
 */
package fbla;

import java.awt.BorderLayout;
import java.awt.Graphics;
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
	private Object[] data;
	
	public Employees() {
		super(new BorderLayout());
		JButton home = new JButton("Home");
		home.addActionListener(this);
		JButton addEmployee = new JButton("Add Employee");
		addEmployee.addActionListener(this);
		JList employeesList = new JList();
		employeesList.setListData(data);
		add(employeesList);
		add(home);
		add(addEmployee);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void loadDataSource(String path) {
		ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
		Scanner scanner = new Scanner(ListBox.class.getResourceAsStream(path)); //Loads the .txt file
		scanner.useDelimiter("\t"); //Uses tab as an indicator that a new data segment is present. Can not use comma, as commas may be present in comments.
		
		try {
			while (scanner.hasNextLine()) {
				ArrayList<String> line = new ArrayList<String>(); //Gets next line
				while (scanner.hasNext()) {
					String s = scanner.next(); //Gets each section of the line
					line.add(s);
				}
				input.add(line);
			}
		} catch (Exception e) {} 
		finally {
			scanner.close();
			data = input.toArray();
		}
	}
}
