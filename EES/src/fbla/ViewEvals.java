/**
 * 
 */
package fbla;

import java.awt.BorderLayout;

import javax.swing.JButton;

/**
 * @author Cody
 *
 */
@SuppressWarnings("serial")
public class ViewEvals extends DataInputWindow {

	/**
	 * @param borderLayout
	 */
	public ViewEvals() {
		super(new BorderLayout());

		JButton close = new JButton("Close");
		close.addActionListener(this);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
