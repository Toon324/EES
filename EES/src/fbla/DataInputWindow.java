/**
 * 
 */
package fbla;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Cody
 * 
 */
@SuppressWarnings("serial")
public class DataInputWindow extends JPanel implements ActionListener {

	ArrayList<JTextField> textFields = new ArrayList<JTextField>();
	ArrayList<JLabel> labels = new ArrayList<JLabel>();
	protected static JFrame frame = new JFrame();

	public DataInputWindow(BorderLayout borderLayout) {
		super(borderLayout);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Overrided in extending classes to maintain layout
	}

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
			
			//Gets the last employee number
			Scanner scanner = new Scanner(file); 
			String l = "";
			while (scanner.hasNextLine())
				l = scanner.nextLine();
			Scanner lineScanner = new Scanner(l);
			int lastNum = lineScanner.nextInt();
			scanner.close();
			lineScanner.close();
				
			StringBuilder toPrint = new StringBuilder();
			toPrint.append((lastNum+1) + "\t");
			for (JTextField tf : textFields) 
				toPrint.append(tf.getText() + "\t");
			
			out.println(toPrint.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
