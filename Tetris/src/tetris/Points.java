package tetris;

/**
 * This class counts and calculates points/score for tetris. The score formula
 * is taken from http://sv.wikipedia.org/wiki/Tetris
 * 
 * @author Magnus Benedicto
 * @version 1.0 2011-01-23
 * 
 */
public class Points {

	// Normal max score in Tetris
	// http://en.allexperts.com/q/Console-Games-Nintendo-1242/Tetris-Game-Boy.htm
	public final static int MAX = 9999999;
	private int score;
	private int lines;
	private int level;

	/**
	 * This class counts and calculates points/score for tetris. The score
	 * formula is taken from http://sv.wikipedia.org/wiki/Tetris
	 */
	public Points() {
		score = 0;
		lines = 0;
		level = 0;
	}

	/**
	 * Add and save score for corresponding number of lines deleted
	 * 
	 * @param rows
	 *            number of deleted rows
	 */
	public void linesDeleted(int rows) {
		switch (rows) {
		case 1:
			single();
			break;
		case 2:
			duple();
			break;
		case 3:
			tripple();
			break;
		case 4:
			tetris();
			break;
		}
	}

	/**
	 * Add and save score for a deleted single line
	 */
	public void single() {
		lines++;
		score += calc(40); // One row worth 40 points
	}

	/**
	 * Add and save score for a deleted duple line
	 */
	public void duple() {
		lines += 2;
		score += calc(100); // Two row worth 100 points
	}

	/**
	 * Add and save score for a deleted tripple line
	 */
	public void tripple() {
		lines += 3;
		score += calc(300); // Three row worth 300 points
	}

	/**
	 * Add and save score for a deleted tetris line (four rows)
	 */
	public void tetris() {
		lines += 4;
		score += calc(1200); // Four row worth 1200 points
	}

	/**
	 * Get current score
	 * 
	 * @return current score
	 */
	public int getPoints() {
		return score;
	}

	/**
	 * Get current total deleted lines
	 * 
	 * @return number of deleted lines
	 */
	public int getLines() {
		return lines;
	}

	/**
	 * Go to next game level
	 */
	public void nextLevel() {
		level++;
	}

	/**
	 * Get current game level
	 * 
	 * @return current game level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Reset all score data, set all to zero (score, level, lines)
	 */
	public void reset() {
		score = 0;
		lines = 0;
		level = 0;
	}

	// Calculates score
	private int calc(int points) {
		if (lines / ((level + 1) * 10) > 0) {
			level++;
		}
		return ((level + 1) * points > MAX) ? MAX : (level + 1) * points;
	}
}
