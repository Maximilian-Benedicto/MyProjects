package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class is used to create a grid of panels(TSquare0)
 * 
 * @author Magnus Benedicto
 * @version 1.0 2011-01-23
 * 
 */
@SuppressWarnings("serial")
public class TGridPanel extends JPanel {

	private int mXClick, mYClick;
	private JDialog dialog;
	private JFrame frame;

	/**
	 * Creates a grid of panels with the TSquare type with chooseble dimension
	 * 
	 * @param rows
	 *            number of rows
	 * @param cols
	 *            number of cols
	 * @param dim
	 *            dimension of each panel
	 */
	public TGridPanel(int rows, int cols, Dimension dim) {
		super(new GridLayout(rows, cols));
		for (int i = 0; i < rows * cols; i++) {
			TSquare ts = new TSquare(dim);
			add(ts);
		}
	}

	/**
	 * Creates a grid of panels with the TSquare type with chooseble dimension.
	 * Used for top panel of a undecorated JFrame or JDialog. You are able to
	 * choose font and title name. Also a function to make parent undecorated
	 * JFrame or JDialog able to move using mouse
	 * 
	 * @param movementSource
	 *            JDialog or JFrame you want to move with mouse
	 * @param rows
	 *            number of rows
	 * @param cols
	 *            number of cols
	 * @param dim
	 *            dimension of each panel
	 * @param text
	 *            title name
	 * @param font
	 *            font for title
	 * 
	 */
	public TGridPanel(Object movementSource, int rows, int cols, Dimension dim,
			String text, Font font) {
		super(new GridLayout(rows, cols));
		if (movementSource instanceof JDialog) {
			dialog = (JDialog) movementSource;
		} else if (movementSource instanceof JFrame) {
			frame = (JFrame) movementSource;
		}
		int l = text.length();
		int startPos = cols / 2 - l / 2;
		for (int i = 0; i < rows * cols; i++) {
			TSquare ts = new TSquare(dim);
			if ((text != null) && (cols >= l)) {
				if (i >= startPos && i < (startPos + text.length())) {
					ts.setLayout(new BorderLayout());
					JLabel jl = new JLabel(" " + text.charAt(i - startPos));
					jl.setFont(font);
					jl.setForeground(Color.white);
					ts.add(jl, BorderLayout.CENTER);
				}
			}
			add(ts);
		}
		// Using panel to move entire frame or dialog on screen with mouse
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent me) {
			}

			@Override
			public void mouseDragged(MouseEvent me) {
				winMove(me);
			}
		});
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent me) {
			}

			@Override
			public void mouseEntered(MouseEvent me) {
			}

			@Override
			public void mouseExited(MouseEvent me) {
			}

			@Override
			public void mousePressed(MouseEvent me) {
				// Save mouseclick position
				mXClick = me.getPoint().x;
				mYClick = me.getPoint().y;
			}

			@Override
			public void mouseReleased(MouseEvent me) {
			}
		});

	}

	// Moving a parent JFrame or JDialog
	private void winMove(MouseEvent me) {
		if (dialog != null) {
			dialog.setLocation(me.getXOnScreen() - mXClick, me.getYOnScreen()
					- mYClick);
		} else if (frame != null) {
			frame.setLocation(me.getXOnScreen() - mXClick, me.getYOnScreen()
					- mYClick);
		}
	}
}
