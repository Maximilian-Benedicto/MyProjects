package tetris;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * A JLabel with special attributs used for Tetris GUI
 * 
 * @author Magnus Benedicto
 * @version 1.0 2011-01-23
 * 
 */

@SuppressWarnings("serial")
public class TLabel extends JLabel {

	/**
	 * A JLabel with special attributs used for Tetris GUI
	 * 
	 * @param text
	 *            button text
	 * @param font
	 *            font for the button text
	 */
	public TLabel(String text, Font font) {
		super(text);
		setFont(font);
		setHorizontalAlignment(SwingConstants.CENTER);
		setForeground(Color.white);
		setBackground(Color.black);
	}

}
