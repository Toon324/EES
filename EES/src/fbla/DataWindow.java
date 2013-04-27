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
 * A structure for all popup windows that either accept or show data. Offers
 * convenience structures for holding components and writing them to a .txt
 * file.
 * 
 * @author Cody Swendrowski
 */
@SuppressWarnings("serial")
public abstract class DataWindow extends JPanel implements ActionListener {
	
	protected JFrame frame = new JFrame(); // Frame to display
	protected ArrayList<JTextField> textFields = new ArrayList<JTextField>();
	protected ArrayList<JLabel> labels = new ArrayList<JLabel>();

	/**
	 * Creates a new DataWindow with set layout.
	 * 
	 * @param borderLayout
	 *            Layout to display
	 */
	public DataWindow(BorderLayout borderLayout) {
		super(borderLayout);
	}

	@Override
	public abstract void actionPerformed(ActionEvent arg0);

	/**
	 * Given a path, writes all data present in textFields to .txt file located
	 * at path.
	 * 
	 * @param path
	 *            Path to .txt file to write data to
	 */
	protected void writeData(String path) {
		// If path is null or empty, do nothing
		if (path == null || path.equals(""))
			return;

		try {
			// Creates file writer
			PrintWriter out = null;
			File file = new File(path);
			try {
				if (!file.exists())
					file.createNewFile(); // If file does not exist, create it
				out = new PrintWriter(new FileWriter(file.getAbsoluteFile(),
						true)); // Append to current data
			} catch (IOException e) {
			}

			// Gets the last number
			Scanner scanner = new Scanner(file);
			String l = ""; // Holds line
			while (scanner.hasNextLine())
				// Sets l to last line in .txt file
				l = scanner.nextLine();
			// Scans last line for last number
			Scanner lineScanner = new Scanner(l);
			lineScanner.useDelimiter(EES.delim);
			int lastNum = lineScanner.nextInt();
			// Closes scanners
			scanner.close();
			lineScanner.close();

			// Adds all data to a StringBuilder for printing
			StringBuilder toPrint = new StringBuilder();
			toPrint.append((lastNum + 1) + EES.delim); // Adds next valid number to
													// toPrint

			for (JTextField tf : textFields) {
				String line = tf.getText();
				//If text contains a comma, replace it with a filler so it isn't confused as a delim
				line = line.replace("," , "<comma>");
				line = line + EES.delim; //Add delim
				toPrint.append(line);
			}

			// Prints data and closes writer.
			out.println(toPrint.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
