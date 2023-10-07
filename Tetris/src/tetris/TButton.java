package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * A JButton with special attributs used for Tetris GUI
 * 
 * @author Magnus Benedicto
 * @version 1.0 2011-01-23
 * 
 */

@SuppressWarnings("serial")
public class TButton extends JButton {

	/**
	 * A JButton with special attributs used for Tetris GUI
	 * 
	 * @param text
	 *            button text
	 * @param font
	 *            font for the button text
	 * @param dim
	 *            dimension of the TSqares
	 */
	public TButton(String text, Font font, Dimension dim) {
		super();
		setText(text);
		setBackground(Color.gray);
		setForeground(Color.white);
		setFont(font);
		setFocusable(false);
		setBorder(BorderFactory.createRaisedBevelBorder());
		setPreferredSize(dim);
	}

}
