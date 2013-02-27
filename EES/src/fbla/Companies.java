package fbla;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class Companies extends JPanel implements ActionListener{

	private static String[] data = new String[0];
	static ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
	
	/**
	 * @param p
	 */
	public Companies() {
		super(new BorderLayout());
		JPanel buttons = new JPanel(new GridLayout(1,2));
		
		JButton home = new JButton("Home");
		home.addActionListener(this);
		
		JButton addCompany = new JButton("Add Company");
		addCompany.addActionListener(this);
		
		buttons.add(home);
		buttons.add(addCompany);
		
		JList companiesList = new JList();
		companiesList.setVisibleRowCount(10);
		companiesList.setSize(300, 500);
		loadDataSource("Resources\\Employer.txt");
		companiesList.setListData(data);
		add(companiesList);
		add(BorderLayout.PAGE_END, buttons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Home"))
			EES.cl.show(EES.pages, "Home");
		else if (e.getActionCommand().equals("Add Company"))
			AddCompany.main(null);
		
	}
	
	public static void loadDataSource(String path) {
		Scanner scanner = new Scanner(Employees.class.getResourceAsStream(path)); //Loads the .txt file
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
				lineScanner.close();
				input.add(line);
			}
		} catch (Exception e) {} 
		finally {
			scanner.close();
			dataSort(0);
		}
	}

	private static void dataSort(int i) {
		
		data = new String[input.size()];
		
		switch (i) {
		case 0: //By Employee ID
			for (int x=0; x<input.size(); x++) {
				StringBuilder line = new StringBuilder();
				for (String s: input.get(x))
					line.append(s + "    " + "\t");
				data[x] = line.toString();
			}
			break;
		case 1: //By name
			//TODO: Sort by name
			break;
		}
	}

}
