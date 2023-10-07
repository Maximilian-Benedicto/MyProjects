package tetris;

import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * HighScore for saving/loading and handling of highscore
 * 
 * @author Magnus Benedicto
 * @version 1.0 2011-01-23
 * 
 */
public class HighScore {

	private LinkedList<Player> list;
	private String fileName;
	private JFrame frame;
	private Font font;
	private Dimension dim;

	/**
	 * HighScore used for saving/loading and handling of highscore for Tetris
	 * 
	 * @param frame
	 *            JFrame used to connect dialog windows to
	 * @param fileName
	 *            name of HighScore data file. If a file not exist or are
	 *            corrupt a new default file will be created
	 * @param font
	 *            font for the dialog text
	 * @param dim
	 *            dimension of the TSqares in dialog windows
	 */
	public HighScore(JFrame frame, String fileName, Font font, Dimension dim) {
		this.fileName = fileName;
		this.frame = frame;
		this.font = font;
		this.dim = dim;
		list = new LinkedList<Player>();
		if (!loadAllScore()) {
			createDefault();
		}
	}

	// This is used when no highscore data file exists or is corrupt. A new
	// default list is created
	private void createDefault() {
		for (int i = 1; i <= 10; i++) {
			list.add(new Player(i * 200, "Player Name " + i));
		}
	}

	/**
	 * Shows a dialog with all highscores sorted with highest score in the top
	 */
	public void showAllScore() {
		JTextArea textArea = new JTextArea();
		int c = 10;
		Collections.sort(list);
		for (Iterator<Player> it = list.iterator(); it.hasNext();) {
			Player s = it.next();
			textArea.insert(
					"  " + c-- + "\t" + s.getScore() + "\t" + s.getPlayer()
							+ "  \n", 0);
		}
		textArea.insert("  Place:\tScore:\tPlayer:\n\n", 0);
		TOptionPane.showMessageDialog(frame, textArea, "High Score", font, dim,
				20);
	}

	// Loads a high score data file
	@SuppressWarnings("unchecked")
	private boolean loadAllScore() {
		try {
			// Deserialize from a file
			File file = new File(fileName);
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					file));
			// Deserialize the object
			list = (LinkedList<Player>) in.readObject();
			in.close();
			return true;
		} catch (ClassNotFoundException e) {
			// Ignore
		} catch (ClassCastException e) {
			// Ignore
		} catch (IOException e) {
			// Ignore
		}
		return false;
	}

	// Saves a high score list to a data file
	private boolean saveAllScore() {
		try {
			ObjectOutput out = new ObjectOutputStream(new FileOutputStream(
					fileName));
			out.writeObject(list);
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Checks if a players score should be in the highscore list
	 * 
	 * @param score
	 *            player score to check
	 * @return true if the player has a high score
	 */
	public boolean isHighScore(int score) {
		// check if score is higher than score in highscorelist
		for (Iterator<Player> it = list.iterator(); it.hasNext();) {
			if (score > it.next().getScore()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Show a game over dialog showing player score
	 * 
	 * @param score
	 *            player score to show
	 */
	public void showGameOver(int score) {
		TOptionPane.showMessageDialog(frame, new JTextArea("\n  Your score: "
				+ score + "\n"), "Game Over!", font, dim, 12);
	}

	/**
	 * Insert a new highscore to the list. Will always save complete high score
	 * list to data file. Always use isHighScore() first.
	 * 
	 * @param score
	 *            player score to insert in list
	 */
	public void newHighScore(int score) { // insert a new score in highscorelist
		String player = TOptionPane.showInputDialog(frame, new JTextArea(
				"You have new high score: " + score + "\nEnter your name: "),
				"Game Over!", font, dim, 16);
		Collections.sort(list); // Sort numerical order
		list.removeFirst();// remove lowest score
		list.add(new Player(score, player)); // put new score on list
		saveAllScore(); // Save score to file
	}
}
