package fbla;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Cody
 *
 */
@SuppressWarnings("serial")
public class Companies extends JPanel implements ActionListener{

	/**
	 * @param p
	 */
	public Companies() {
		super(new BorderLayout());
		JButton home = new JButton("Home");
		home.addActionListener(this);
		add(home);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
