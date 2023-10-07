package tetris;

import java.io.Serializable;

/**
 * Score class is used to hold score and name in a high score list. Implements
 * Comparable for use in List for sorting functionality Implements Serializable
 * for use of saving object to file
 * 
 * @author Magnus Benedicto
 * @version 1.0 2011-01-23
 * 
 */
public class Player implements Comparable<Player>, Serializable {

	private static final long serialVersionUID = -4455798599386029092L;
	private int score;
	private String player;

	/**
	 * Creates a player with name and score
	 * 
	 * @param score
	 *            player score
	 * @param player
	 *            name of player
	 */
	public Player(int score, String player) {
		this.score = score;
		this.player = player;
	}

	/**
	 * Sets the player name
	 * 
	 * @param newplayer
	 *            player name
	 */
	public void setPlayer(String newplayer) {
		player = newplayer;
	}

	/**
	 * Sets the score
	 * 
	 * @param newscore
	 *            new score
	 */
	public void setScore(int newscore) {
		score = newscore;
	}

	/**
	 * Get player name
	 * 
	 * @return player name
	 */
	public String getPlayer() {
		return player;
	}

	/**
	 * Get score
	 * 
	 * @return score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Compare score to other player. Used in Collection.sort
	 * 
	 * @return diference in score
	 */
	@Override
	public int compareTo(Player o) {
		return this.score - o.getScore();
	}

}