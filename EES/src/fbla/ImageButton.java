package fbla;

import java.awt.Graphics;
import java.awt.Image;

/**
 * @author Cody Swendrowski
 *
 */
public class ImageButton extends Button{
	
	private Image image;

	public ImageButton(String text, int x, int y, Image i) {
		super(text, x, y);
		image = i;
		width = i.getWidth(null);
		height = i.getHeight(null);
	} 
	
	/* (non-Javadoc)
	 * @see fbla.Button#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		g.drawImage(image, x_pos, y_pos, width, height, null);
		super.draw(g);
	}
	
}
