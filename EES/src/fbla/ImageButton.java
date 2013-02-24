package fbla;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;

import javax.imageio.ImageIO;

/**
 * @author Cody Swendrowski
 * 
 */
public class ImageButton extends Button {

	private Image image;

	public ImageButton(int x, int y, String path) {
		super("", x, y);
		image = getImage(path);
		width = image.getWidth(null);
		height = image.getHeight(null);
		Polygon temp = new Polygon();
		temp.addPoint(x, y);
		temp.addPoint(x + width, y);
		temp.addPoint(x + width, y + height);
		temp.addPoint(x, y + height);
		bounds = temp;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, x_pos, y_pos, width, height, null);
	}

	/**
	 * Helper method. Gets an image from relative position of this class.
	 * 
	 * @param path
	 *            Path to Image
	 * @return Image located at path
	 */
	private Image getImage(String path) {
		try {
			return ImageIO.read(ImageButton.class.getResourceAsStream(path));
		} catch (Exception e) {
			System.out.println("Null image");
			return null;
		}
	}
}
