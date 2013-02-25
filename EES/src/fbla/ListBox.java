package fbla;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Cody Swendrowski
 *
 */
public class ListBox {
	
	private int index, size, x_pos, y_pos, height, amountOfData;
	private final int width = 100;

	private ArrayList<ArrayList<String>> data;
	
	private ImageButton up, down;
	
	/**
	 * 
	 */
	public ListBox(int x, int y) {
		index = 0;
		size = 10;
		x_pos = x;
		y_pos = y;
		up = new ImageButton(x+width, y, "Resources\\up.png");
		down = new ImageButton(x+width, y+height, "Resources\\down.png");
		data = new ArrayList<ArrayList<String>>();
	}
	
	public void run() {
		down.set(down.getX(), y_pos+height);
		
		if (index == 0)
			up.setEnabled(false);
		else
			up.setEnabled(true);
		if (index == amountOfData -1)
			down.setEnabled(false);
		else
			down.setEnabled(true);
		
		if (up.isClicked())
			index--;
		else if (down.isClicked()) 
			index++;
		
	}
	
	public void paint(Graphics g) {
		height = (g.getFontMetrics().getHeight()+2)*size;
		//g.drawRect(x_pos-3, y_pos-3, width+6, height+6);
		for (int cnt=0; cnt<size; cnt++){
			try {
				int drawY = y_pos + (g.getFontMetrics().getHeight()+2)*cnt;
				int currentWidth = 0;
				for (int segment=0; segment < data.get(cnt+index).size(); segment++) {
					g.drawString(data.get(cnt+index).get(segment), x_pos + currentWidth, drawY+10);
					currentWidth += g.getFontMetrics().stringWidth(data.get(cnt+index).get(segment)) + 10;
				}
			}
			catch (Exception e) {}
		}
		
		up.draw(g);
		down.draw(g);
	}
	
	public void setListSize(int s) {
		size = s;
	}
	
	public void checkClick(MouseEvent e){
		up.checkClick(e.getX(), e.getY());
		down.checkClick(e.getX(), e.getY());
	}

	public void loadDataSource(String path) {
		Scanner scanner = new Scanner(ListBox.class.getResourceAsStream(path)); //Loads the .txt file
		scanner.useDelimiter("\t"); //Uses tab as an indicator that a new data segment is present. Can not use comma, as commas may be present in comments.
		
		try {
			while (scanner.hasNextLine()) {
				ArrayList<String> line = new ArrayList<String>(); //Gets next line
				while (scanner.hasNext()) {
					String s = scanner.next(); //Gets each section of the line
					line.add(s);
				}
				data.add(line);
				amountOfData++; //Updates the total amount of data available
			}
		} catch (Exception e) {} 
		finally {
			scanner.close();
		}
	}

}