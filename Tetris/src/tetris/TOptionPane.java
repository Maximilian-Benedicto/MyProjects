package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.MaskFormatter;

/**
 * This class have two static methods to show to types of message dialogs, one
 * with input function and one without
 * 
 * @author Magnus Benedicto
 * @version 1.0 2011-01-23
 * 
 */
public class TOptionPane {
	private static JDialog dialog;
	private static JFormattedTextField jta;

	// A messagedialog with input function and without input function
	private static String messageDialog(boolean input,
			Component parentComponent, JTextArea message, String title,
			Font font, Dimension dim, int cols) {

		// Create a modal dialog
		dialog = new JDialog((Frame) parentComponent, true);

		message.setBackground(Color.black);
		message.setForeground(Color.white);
		message.setLineWrap(true);
		message.setEditable(false);
		message.setFont(font);
		TGridPanel nP = new TGridPanel(dialog, 1, cols, dim, title, font);
		TGridPanel wP = new TGridPanel(message.getLineCount() + 1, 1, dim);
		TGridPanel eP = new TGridPanel(message.getLineCount() + 1, 1, dim);
		TButton tb = new TButton("OK", font, dim);

		// This listener is used to close complete dialog when pressing ok
		// button
		tb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});

		dialog.setLayout(new BorderLayout());
		dialog.add(nP, BorderLayout.NORTH);
		dialog.add(wP, BorderLayout.WEST);
		dialog.add(eP, BorderLayout.EAST);
		if (input) {
			JPanel jp = new JPanel(new BorderLayout());
			jp.add(message, BorderLayout.NORTH);
			try {
				// 25 letters is allowed as Player name
				jta = new JFormattedTextField(new MaskFormatter(
						"*************************"));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			jta.setCaretColor(Color.white);
			jta.setForeground(Color.yellow);
			jta.setBackground(Color.black);
			jta.setBorder(BorderFactory.createLineBorder(Color.red, 5));
			jta.setSelectionColor(Color.red);
			jta.setFont(font);
			jp.add(jta, BorderLayout.CENTER);
			dialog.add(jp, BorderLayout.CENTER);
		} else {
			dialog.add(message, BorderLayout.CENTER);
		}
		dialog.add(tb, BorderLayout.SOUTH);
		dialog.setTitle(title);
		dialog.setUndecorated(true);
		dialog.setResizable(false);
		dialog.pack();
		dialog.setLocationRelativeTo(parentComponent);
		dialog.setVisible(true);
		if (input) {
			return jta.getText();
		} else {
			return "";
		}
	}

	/**
	 * Creates a modal message dialog using TGridpanels and TButton
	 * 
	 * @param parentComponent
	 *            the Frame from which the dialog is displayed
	 * @param message
	 *            a message in JTextArea type
	 * @param title
	 *            name of title
	 * @param font
	 *            font of the message
	 * @param dim
	 *            dimension of the TSqares
	 * @param cols
	 *            number of TSquare columns
	 */
	public static void showMessageDialog(Component parentComponent,
			JTextArea message, String title, Font font, Dimension dim, int cols) {
		messageDialog(false, parentComponent, message, title, font, dim, cols);
	}

	/**
	 * Creates a modal message dialog using TGridpanels and TButton and a
	 * editable JTeaxtArea
	 * 
	 * @param parentComponent
	 *            the Frame from which the dialog is displayed
	 * @param message
	 *            a message in JTextArea type
	 * @param title
	 *            name of title
	 * @param font
	 *            font of the message
	 * @param dim
	 *            dimension of the TSqares
	 * @param cols
	 *            number of TSquare columns
	 * @return user input
	 */
	public static String showInputDialog(Component parentComponent,
			JTextArea message, String title, Font font, Dimension dim, int cols) {
		return messageDialog(true, parentComponent, message, title, font, dim,
				cols);
	}

}
