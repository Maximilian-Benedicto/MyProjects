package tetris;

import java.awt.Color;
import java.awt.Point;

/**
 * Class GamePlan. Implements a GamePlan possible to use when 
 * designing arcade-like games.
 * @author Jonas Holmgren
 * @version 1.0 2011-01-07
 */
public class GamePlan {
	private boolean freeMap[][];
	private Color colorMap[][];
	private Color bgColor;
	private int x_max;
	private int y_max;

	/**
	 * Constructor of the GamePlan. Takes the width and height, as well 
	 * as the background color, as parameters. 
	 * @param maxX - int, width of the GamePlan
	 * @param maxY - int, height of the GamePlan
	 * @param c - Background color of the GamePlan.
	 */
	public GamePlan(int maxX, int maxY, Color c) {
		bgColor = c;
		freeMap = new boolean[maxX][maxY];
		colorMap = new Color[maxX][maxY];
		for (int x = 0; x < maxX; x++)
			for (int y = 0; y < maxY; y++) {
				freeMap[x][y] = true;
				colorMap[x][y] = bgColor;
			}
		x_max = maxX;
		y_max = maxY;
	}

	/**
	 * Indicates that the specified position is busy and what color
	 * that Tetromino has.
    * @param p - Point, position within the GamePlan.
    * @param c - Color, the color to be used for the specified point. 
	 */
	public void setBusy(Point p, Color c) {
		if ((p.x >= x_max) | (p.x < 0) | (p.y >= y_max) | (p.y < 0))
			return; // throw exception?
		freeMap[p.x][p.y] = false;
		colorMap[p.x][p.y] = c;
	}

	/**
	 * Used to check if a specified point within the GamePlan is free or not.
    * @param p - Point, position within the GamePlan.
	 * @return boolean - true if the position is free, false otherwise
	 */
	public boolean isFree(Point p) {
		// if ((p.x >= x_max) | (p.x < 0) | (p.y >= y_max) | (p.y < 0)) return;
		// // throw exception?
		return ((p.x >= x_max) | (p.x < 0) | (p.y >= y_max) | (p.y < 0)) ? false
				: freeMap[p.x][p.y];
	}

   /**
    * Returns the maximum x-coordinate of the GamePlan. The index of the 
    * leftmost column is 0 and the index of the rightmost is MaxX-1. 
    * @return int 
    */
	public int getMaxX() {
		return x_max;
	}

   /**
    * Returns the maximum y-coordinate of the GamePlan. The index of the 
    * bottom row is 0 and the index of the top row is MaxY-1. 
    * @return int 
    */
	public int getMaxY() {
		return y_max;
	}

	/**
	 * Used for returning the background color of the GamePlan.
	 * @return - Color
	 */
	public Color getBgColor() {
		return bgColor;
	}

	/**
	 * Used for getting the color of a specified point within the
	 * GamePlan. If a point outside the GamePlan is given as 
	 * parameter the  background color is returned.
	 * @param p - Point, position within the GamePlan.
	 * @return Color - the color of the specified point. 
	 */
	public Color getColor(Point p) {
		return ((p.x >= x_max) | (p.x < 0) | (p.y >= y_max) | (p.y < 0)) ? bgColor
				: colorMap[p.x][p.y];
	}

	/**
	 * Used to set the color of a specific part of the GamePlan.
	 * @param p - Point, position within the GamePlan.
	 * @param c - Color, color to be used.
	 */
	public void setColor(Point p, Color c) {
		if ((p.x >= x_max) | (p.x < 0) | (p.y >= y_max) | (p.y < 0))
			return; 
		colorMap[p.x][p.y] = c;
	}

	/**
	 * Used to colorize soon to be deleted rows with selected color.
    * @return int - The number of full rows
	 */
	public int colorizeFullRows(Color c) {
		int lines = 0;
		for (int y = 0; y < y_max; y++) {
			boolean full = true;
			for (int x = 0; x < x_max; x++)
				full &= !freeMap[x][y];
			if (full) {
				for (int x = 0; x < x_max; x++) {
					colorMap[x][y] = c;
				}				
				lines++;
			}
		}
		return lines;
	}

	/**
	 * Delete all the rows that are completely full by moving all 
	 * subsequent one step. 
	 * @return int - The number of deleted rows
	 */
	public int deleteFullRows() {
		int deleted = 0;
		for (int y = 0; y < y_max; y++) {
			boolean full = true;
			for (int x = 0; x < x_max; x++)
				full &= !freeMap[x][y];
			if (full) {
				for (int yn = y; yn < y_max; yn++)
					for (int x = 0; x < x_max; x++) {
						freeMap[x][yn] = (yn == y_max - 1) ? true
								: freeMap[x][yn + 1];
						colorMap[x][yn] = (yn == y_max - 1) ? bgColor
								: colorMap[x][yn + 1];
					}
				y--;
				deleted++;
			}
		}
		return deleted;
	}
}
