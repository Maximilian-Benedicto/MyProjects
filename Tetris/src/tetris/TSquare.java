package tetris;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * A JPanel with special attributs used for Tetris GUI
 * 
 * @author Magnus Benedicto
 * @version 1.0 2011-01-23
 * 
 */
@SuppressWarnings("serial")
public class TSquare extends JPanel {

	/**
	 * A JPanel with special attributs used for Tetris GUI
	 * 
	 * @param dim
	 *            dimension of the TSqares
	 */
	public TSquare(Dimension dim) {
		super();
		setPreferredSize(dim);
		setBorder(BorderFactory.createRaisedBevelBorder());
		setBackground(Color.darkGray);
	}

}
