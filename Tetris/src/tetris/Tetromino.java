package tetris;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;
/**
 * Class Tetromino implements the Tetrominos object for a 
 * Tetris game implementation. 
 * @author Magnus Benedicto & Jonas Holmgren
 * @version 1.0 2011-01-23
 */
public class Tetromino {
   private final static int TETRO_SIZE = 4;
   private Point tetroPiece[] = new Point[TETRO_SIZE];
	private Point tetroCopy[] = new Point[TETRO_SIZE];
	private Color tetroColor;
	private Orientation tetroOrientation;
	private Point tetroPos;
   private TetroType tType;
	private static enum Orientation { NORTH, SOUTH, WEST, EAST }
	private static enum TetroType { I, J, L, O, S, T, Z }


	/**
	 * The directions in which a Tetromino can be moved.
	 * CW - ClockWise
	 * CCW - CounterClockWise
	 * FREEZE - No movement.
	 */
	public static enum Direction { DOWN, RIGHT, LEFT, CW, CCW, FREEZE }

	/**
	 * Constructs a Tetromino of the specified type.
	 * @param theType - use randomTetro() to always be in range.
	 */
	public Tetromino(TetroType theType) {
		tetroOrientation = Orientation.NORTH;
		tType = theType;
		getTetroPiece(tetroOrientation);
	}

   /**
    * Constructs a Tetromino of the specified type at the 
    * specified position.
    * @param theType - use randomTetro() to always be in range.
	 * @param p - Point, center of gravity of the Tetromino in
	 * relation to the GamePlan
	 */
	public Tetromino(TetroType theType, Point p) {
		this(theType);
		p.y = 1 + p.y - getYOffset(tetroPiece); // Adjust start of piece
		setStartPos(p);
	}
	
	/**
	 * Static method to randomize a Tetromino. For use primarily 
	 * with the constructor.
	 */
	public static TetroType randomTetro() {
		return TetroType.values()[new Random().nextInt(TetroType.values().length)];
	}

	/**
    * Gets the starting point of the Tetromino.
	 * @return - Point
	 */
	public Point getStartPos() {
		return tetroPos;
	}

	/**
    * Sets the starting point of the Tetromino.
	 * @param p - Point
	 */
	public void setStartPos(Point p) {
	   tetroPos = new Point(p.x, p.y);
	}

	/**
	 * Checks is an intended move of the the Tetromino is 
	 * possible to perform. The move is only possible to do 
	 * if the Tetromino is within the GamePlan and no other 
	 * Tetrominos blocks the way.  
	 * @param move, Direction - Type of move.
	 * @param plan, GamePlan 
	 * @return - boolean, true if the move can be done, 
	 * false otherwise 
	 */
	public boolean isMoveOK(Direction move, GamePlan plan) {
		Point tm[] = new Point[TETRO_SIZE];
		Point pm = new Point(0, 0);
		Point pMax = new Point(0, 0);
		Point pMin = new Point(plan.getMaxX(), plan.getMaxY());
		save();
		switch (move) {
		case DOWN:
			pm.y -= 1; // one step down
			break;
		case RIGHT:
			pm.x += 1; // one step right
			break;
		case LEFT:
			pm.x -= 1; // one step left
			break;
		case CCW:
			getTetroPiece(getNextOrientationCCW());
			break;
		case CW:
			getTetroPiece(getNextOrientationCW());
		default:
			break;
		}
		for (int i = 0; i < TETRO_SIZE; i++)
			tm[i] = new Point(tetroPos.x + tetroPiece[i].x + pm.x, tetroPos.y + tetroPiece[i].y + pm.y);
		restore();
		getSizes(tm, pMax, pMin);
		if ((pMax.x > plan.getMaxX()) || (pMax.y > plan.getMaxY()) || (pMin.x < 0) || (pMin.y < 0))
			return false; // out of bounds
		boolean retValue = true;
		for (int i = 0; i < TETRO_SIZE; i++)
			retValue &= (plan.isFree(new Point(tm[i].x, tm[i].y)));
		return retValue;
	}

	/**
    * Moves the Tetromino in the requested direction. 
    * @param move, Direction - Type of move.
    * @param plan, GamePlan 
	 */
	public void move(Direction move, GamePlan plan) {
		Point tm[] = new Point[TETRO_SIZE];
		for (int i = 0; i < TETRO_SIZE; i++)
			tm[i] = new Point(tetroPos.x + tetroPiece[i].x, tetroPos.y + tetroPiece[i].y);
		for (int i = 0; i < TETRO_SIZE; i++)
			plan.setColor(new Point(tm[i].x, tm[i].y), plan.getBgColor());
		switch (move) {
		case DOWN:
			tetroPos.y -= 1; // one step down
			break;
		case RIGHT:
			tetroPos.x += 1; // one step right
			break;
		case LEFT:
			tetroPos.x -= 1; // one step left
			break;
		case CCW:
			getTetroPiece(tetroOrientation = getNextOrientationCCW());
			break;
		case CW:
			getTetroPiece(tetroOrientation = getNextOrientationCW());
		case FREEZE:
			break;
		}
		for (int i = 0; i < TETRO_SIZE; i++)
			tm[i] = new Point(tetroPos.x + tetroPiece[i].x, tetroPos.y
					+ tetroPiece[i].y);
		for (int i = 0; i < TETRO_SIZE; i++)
			plan.setColor(new Point(tm[i].x, tm[i].y), tetroColor);
	}

	/**
	 * Sets all the positions of the current Tetromino to busy in the 
	 * specified GamePlan.
	 * @param plan, GamePlan
	 */
	public void setBusy(GamePlan plan) {
		Point tm[] = new Point[TETRO_SIZE];
		for (int i = 0; i < TETRO_SIZE; i++) {
			tm[i] = new Point(tetroPos.x + tetroPiece[i].x, tetroPos.y
					+ tetroPiece[i].y);
			plan.setBusy(new Point(tm[i].x, tm[i].y), tetroColor);
		}
	}

	/**
	 * Rotates the Tetromino CounterClockWise
	 */
	public void rotateCCW() {
		tetroOrientation = getNextOrientationCCW();
		getTetroPiece(tetroOrientation);
	}

	/**
    * Rotates the Tetromino ClockWise
	 */
	public void rotateCW() {
		tetroOrientation = getNextOrientationCW();
		getTetroPiece(tetroOrientation);
	}

	// Implementation of all seven different Tetrominos. 
	// All rotation is made around point (0, 0).
	private void getTetroPiece(Orientation o) {
		switch (tType) {
		case I:
			tetroColor = Color.cyan;
			switch (o) {
			case NORTH:
				tetroPiece[0] = new Point(0, 1);
				tetroPiece[1] = new Point(0, 0);
				tetroPiece[2] = new Point(0, -1);
				tetroPiece[3] = new Point(0, -2);
				break;
			case SOUTH:
				tetroPiece[0] = new Point(0, 2);
				tetroPiece[1] = new Point(0, 1);
				tetroPiece[2] = new Point(0, 0);
				tetroPiece[3] = new Point(0, -1);
				break;
			case WEST:
				tetroPiece[0] = new Point(-1, 0);
				tetroPiece[1] = new Point(0, 0);
				tetroPiece[2] = new Point(1, 0);
				tetroPiece[3] = new Point(2, 0);
				break;
			case EAST:
				tetroPiece[0] = new Point(-2, 0);
				tetroPiece[1] = new Point(-1, 0);
				tetroPiece[2] = new Point(0, 0);
				tetroPiece[3] = new Point(1, 0);
			default:
				break;
			}
			break;
		case S:
			tetroColor = Color.green;
			switch (o) {
			case NORTH:
				tetroPiece[0] = new Point(-1, 0);
				tetroPiece[1] = new Point(0, 0);
				tetroPiece[2] = new Point(0, 1);
				tetroPiece[3] = new Point(1, 1);
				break;
			case SOUTH:
				tetroPiece[0] = new Point(-1, -1);
				tetroPiece[1] = new Point(0, -1);
				tetroPiece[2] = new Point(0, 0);
				tetroPiece[3] = new Point(1, 0);
				break;
			case WEST:
				tetroPiece[0] = new Point(-1, 1);
				tetroPiece[1] = new Point(-1, 0);
				tetroPiece[2] = new Point(0, 0);
				tetroPiece[3] = new Point(0, -1);
				break;
			case EAST:
				tetroPiece[0] = new Point(0, 1);
				tetroPiece[1] = new Point(0, 0);
				tetroPiece[2] = new Point(1, 0);
				tetroPiece[3] = new Point(1, -1);
			default:
				break;
			}
			break;
		case Z:
			tetroColor = Color.red;
			switch (o) {
			case NORTH:
				tetroPiece[0] = new Point(-1, 0);
				tetroPiece[1] = new Point(0, 0);
				tetroPiece[2] = new Point(0, -1);
				tetroPiece[3] = new Point(1, -1);
				break;
			case SOUTH:
				tetroPiece[0] = new Point(-1, 1);
				tetroPiece[1] = new Point(0, 1);
				tetroPiece[2] = new Point(0, 0);
				tetroPiece[3] = new Point(1, 0);
				break;
			case WEST:
				tetroPiece[0] = new Point(0, -1);
				tetroPiece[1] = new Point(0, 0);
				tetroPiece[2] = new Point(1, 0);
				tetroPiece[3] = new Point(1, 1);
				break;
			case EAST:
				tetroPiece[0] = new Point(-1, -1);
				tetroPiece[1] = new Point(-1, 0);
				tetroPiece[2] = new Point(0, 0);
				tetroPiece[3] = new Point(0, 1);
			default:
				break;
			}
			break;
		case O:
			tetroColor = Color.yellow;
			switch (o) {
			case NORTH:
			case SOUTH:
			case WEST:
			case EAST:
				tetroPiece[0] = new Point(0, 0);
				tetroPiece[1] = new Point(1, 0);
				tetroPiece[2] = new Point(0, -1);
				tetroPiece[3] = new Point(1, -1);
			default:
				break;
			}
			break;
		case L:
//			tetroColor = Color.orange; 
		   tetroColor = new Color(255, 120, 0); // Color.orange (255, 200, 0) was almost yellow!
			switch (o) {
			case NORTH:
				tetroPiece[0] = new Point(-1, -1);
				tetroPiece[1] = new Point(-1, 0);
				tetroPiece[2] = new Point(0, 0);
				tetroPiece[3] = new Point(1, 0);
				break;
			case SOUTH:
				tetroPiece[0] = new Point(-1, 0);
				tetroPiece[1] = new Point(0, 0);
				tetroPiece[2] = new Point(1, 0);
				tetroPiece[3] = new Point(1, 1);
				break;
			case WEST:
				tetroPiece[0] = new Point(0, 1);
				tetroPiece[1] = new Point(0, 0);
				tetroPiece[2] = new Point(0, -1);
				tetroPiece[3] = new Point(1, -1);
				break;
			case EAST:
				tetroPiece[0] = new Point(-1, 1);
				tetroPiece[1] = new Point(0, 1);
				tetroPiece[2] = new Point(0, 0);
				tetroPiece[3] = new Point(0, -1);
			default:
				break;
			}
			break;
		case J:
			tetroColor = Color.blue;
			switch (o) {
			case NORTH:
				tetroPiece[0] = new Point(-1, 0);
				tetroPiece[1] = new Point(0, 0);
				tetroPiece[2] = new Point(1, 0);
				tetroPiece[3] = new Point(1, -1);
				break;
			case SOUTH:
				tetroPiece[0] = new Point(-1, 1);
				tetroPiece[1] = new Point(-1, 0);
				tetroPiece[2] = new Point(0, 0);
				tetroPiece[3] = new Point(1, 0);
				break;
			case WEST:
				tetroPiece[0] = new Point(1, 1);
				tetroPiece[1] = new Point(0, 1);
				tetroPiece[2] = new Point(0, 0);
				tetroPiece[3] = new Point(0, -1);
				break;
			case EAST:
				tetroPiece[0] = new Point(0, 1);
				tetroPiece[1] = new Point(0, 0);
				tetroPiece[2] = new Point(0, -1);
				tetroPiece[3] = new Point(-1, -1);
			default:
				break;
			}
			break;
		case T:
			tetroColor = Color.magenta;
			switch (o) {
			case NORTH:
				tetroPiece[0] = new Point(-1, 0);
				tetroPiece[1] = new Point(0, 0);
				tetroPiece[2] = new Point(1, 0);
				tetroPiece[3] = new Point(0, -1);
				break;
			case SOUTH:
				tetroPiece[0] = new Point(-1, 0);
				tetroPiece[1] = new Point(0, 0);
				tetroPiece[2] = new Point(1, 0);
				tetroPiece[3] = new Point(0, 1);
				break;
			case WEST:
				tetroPiece[0] = new Point(0, 1);
				tetroPiece[1] = new Point(0, 0);
				tetroPiece[2] = new Point(0, -1);
				tetroPiece[3] = new Point(1, 0);
				break;
			case EAST:
				tetroPiece[0] = new Point(0, 1);
				tetroPiece[1] = new Point(0, 0);
				tetroPiece[2] = new Point(0, -1);
				tetroPiece[3] = new Point(-1, 0);
			default:
				break;
			}
		default:
			break;
		}
	}

	// Getting the X/Y-size of the Tetromino
	private void getSizes(Point[] pa, Point max, Point min) {
		for (int i = 0; i < TETRO_SIZE; i++) {
			max.x = (pa[i].x > max.x) ? pa[i].x : max.x;
			max.y = (pa[i].y > max.y) ? pa[i].y : max.y;
			min.x = (pa[i].x < min.x) ? pa[i].x : min.x;
			min.y = (pa[i].y < min.y) ? pa[i].y : min.y;
		}
	}

	// Gets the individual distance from (0, 0) and down for the Tetromino.
	// Used to calculate startingPositions.
	private int getYOffset(Point[] pa) {
		int minY = 0;

		for (int i = 0; i < TETRO_SIZE; i++)
			minY = (pa[i].y < minY) ? pa[i].y : minY;
		return minY;
	}

	// Saves the values for the Tetromino
	private void save() {
		for (int i = 0; i < TETRO_SIZE; i++)
			tetroCopy[i] = new Point(tetroPiece[i].x, tetroPiece[i].y);
	}

	// Restore the values for the Tetromino.
	private void restore() {
		for (int i = 0; i < TETRO_SIZE; i++) {
			tetroPiece[i].x = tetroCopy[i].x;
			tetroPiece[i].y = tetroCopy[i].y;
		}
	}
	
	// Gets the next orientation of a Tetromino when performing a
   // CounterClockWise rotation.
   private Orientation getNextOrientationCCW() {
      switch (tetroOrientation) {
      case NORTH:
         return Orientation.WEST;
      case WEST:
         return Orientation.SOUTH;
      case SOUTH:
         return Orientation.EAST;
      default:
         return Orientation.NORTH;
      }
   }

   // Gets the next orientation of a Tetromino when performing a
   // ClockWise rotation.
   private Orientation getNextOrientationCW() {
      switch (tetroOrientation) {
      case NORTH:
         return Orientation.EAST;
      case EAST:
         return Orientation.SOUTH;
      case SOUTH:
         return Orientation.WEST;
      default:
         return Orientation.NORTH;
      }
   }
}
