package fbla;

import java.awt.Graphics;

import javax.swing.ImageIcon;

/**
 * @author Cody Swendrowski
 *
 */
public class ImageButton extends Button{
	
	private ImageIcon image = new ImageIcon();

	public ImageButton(String text, int height, int width, ImageIcon i) {
		super(text, height, width);
		image = i;
	} 
	
	/* (non-Javadoc)
	 * @see fbla.Button#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		super.draw(g);
	}

	

}
