package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Main class for the Tetris game, inherits from JFrame
 * 
 * @author Magnus Benedicto & Jonas Holmgren
 * @version 1.0 2011-01-23
 * 
 */

@SuppressWarnings("serial")
public class Tetris extends JFrame {

	private final static int Y_MAX = 20;
	private final static int X_MAX = 10;
	private final static int Y_OFFSCREEN = 5;
	private final static int TOTAL_Y = 18;
	private final static int SQUARE_SIZE = 35;
	private final static int EAST_PANEL_WIDTH = 5;
	private final static int SPEED_DELAY_INITIAL = 500;
	private final static int SPEED_DELAY_STEP = 20; // approx 20 ms per level
	// http://tet-ris.blogspot.com/2008/11/variations.html
	private final static int LEVEL_MAX = 20;
	private final static int Y_START = Y_MAX - 1;
	private final static int X_START = X_MAX / 2;
	private final static Color COLOR_BACKGROUND = Color.black;

	// Name of file to save highscore
	private final String fileName = "Tetris.dat";

	private Font font = new Font("SansSerif", Font.BOLD, 16);
	private Dimension stdSquare = new Dimension(SQUARE_SIZE, SQUARE_SIZE);
	private TButton startButton = new TButton("START", font, new Dimension(
			EAST_PANEL_WIDTH * SQUARE_SIZE, SQUARE_SIZE));
	private TButton exitButton = new TButton("EXIT", font, new Dimension(
			EAST_PANEL_WIDTH * SQUARE_SIZE, SQUARE_SIZE));
	private TButton scoreButton = new TButton("SCORE", font, new Dimension(
			EAST_PANEL_WIDTH * SQUARE_SIZE, SQUARE_SIZE));
	private TButton helpButton = new TButton("HELP", font, new Dimension(
			EAST_PANEL_WIDTH * SQUARE_SIZE, SQUARE_SIZE));
	private TGridPanel topPanel = new TGridPanel(this, 1, TOTAL_Y, stdSquare,
			"TETRIS      ", font);
	private TGridPanel bottomPanel = new TGridPanel(1, TOTAL_Y, stdSquare);
	private TGridPanel verticalBar1 = new TGridPanel(Y_MAX, 1, stdSquare);
	private TGridPanel verticalBar2 = new TGridPanel(Y_MAX, 1, stdSquare);
	private TGridPanel verticalBar3 = new TGridPanel(Y_MAX, 1, stdSquare);
	private TLabel scoreLabel = new TLabel("0", font);
	private TLabel levelLabel = new TLabel("0", font);
	private TLabel linesLabel = new TLabel("0", font);
	private TSquare gameArea[][] = new TSquare[X_MAX][Y_MAX];
	private TSquare pieceArea[][] = new TSquare[EAST_PANEL_WIDTH][5];
	private JPanel centerPanel = new JPanel(new BorderLayout());
	private JPanel scorePanel = new JPanel(new GridLayout(12, 1));
	private JPanel gamePanel = new JPanel(new GridLayout(Y_MAX, X_MAX));
	private JPanel nextPanel = new JPanel(new GridLayout(4, EAST_PANEL_WIDTH));
	private JPanel eastPanel = new JPanel(new BorderLayout());
	private JPanel buttonsPanel = new JPanel(new GridLayout(4, 4));
	private JPanel statusPanel = new JPanel(new BorderLayout());
	private HighScore highScore = new HighScore(this, fileName, font, stdSquare);
	private Timer gameTimer, colorizeTimer;
	private Points points;
	private Tetromino piece, nextPiece = null;
	private GamePlan gp;
	private boolean pieceStuck, showNextPiece = true;
	private int colorizeCount = 0;

	private enum Action {
		LEFT, RIGHT, CW, CCW, DROP, DOWN
	}

	public Tetris() {
		// Constructor run of inherited class
		super();

		// Set-up of JPanel layout (one JPanel per Tetris square)
		for (int y = Y_MAX - 1; y >= 0; y--)
			for (int x = 0; x < X_MAX; x++) {
				gameArea[x][y] = new TSquare(stdSquare);
				gameArea[x][y].setBackground(COLOR_BACKGROUND);
				gamePanel.add(gameArea[x][y]);
			}

		for (int y = 4 - 1; y >= 0; y--)
			for (int x = 0; x < EAST_PANEL_WIDTH; x++) {
				pieceArea[x][y] = new TSquare(stdSquare);
				pieceArea[x][y].setBackground(COLOR_BACKGROUND);
				nextPanel.add(pieceArea[x][y]);
			}

		points = new Points();

		// Setup layout
		scorePanel.setBackground(COLOR_BACKGROUND);
		scorePanel.add(new TGridPanel(1, EAST_PANEL_WIDTH, stdSquare));
		scorePanel.add(new TLabel("SCORE", font));
		scorePanel.add(scoreLabel);
		scorePanel.add(new TGridPanel(1, EAST_PANEL_WIDTH, stdSquare));
		scorePanel.add(new TLabel("LINES", font));
		scorePanel.add(linesLabel);
		scorePanel.add(new TGridPanel(1, EAST_PANEL_WIDTH, stdSquare));
		scorePanel.add(new TLabel("LEVEL", font));
		scorePanel.add(levelLabel);
		scorePanel.add(new TGridPanel(1, EAST_PANEL_WIDTH, stdSquare));
		scorePanel.add(new TLabel("NEXT", font));
		statusPanel.add(scorePanel, BorderLayout.CENTER);

		statusPanel.add(nextPanel, BorderLayout.SOUTH);

		buttonsPanel.add(startButton, BorderLayout.NORTH);
		buttonsPanel.add(helpButton, BorderLayout.NORTH);
		buttonsPanel.add(scoreButton, BorderLayout.CENTER);
		buttonsPanel.add(exitButton, BorderLayout.SOUTH);
		statusPanel.add(buttonsPanel, BorderLayout.NORTH);

		eastPanel.add(statusPanel, BorderLayout.WEST);
		eastPanel.add(verticalBar3, BorderLayout.EAST);

		centerPanel.add(gamePanel, BorderLayout.CENTER);
		centerPanel.add(verticalBar1, BorderLayout.WEST);
		centerPanel.add(verticalBar2, BorderLayout.EAST);

		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);
		add(centerPanel, BorderLayout.CENTER);
		add(eastPanel, BorderLayout.EAST);
		statusUpdate();
		setUndecorated(true);
		setResizable(false);
		pack();

		// Setup keylistener
		addKeyListener(new gameKeyListener());

		// Startbutton for the game
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!gameTimer.isRunning()) {
					gp = new GamePlan(X_MAX, Y_MAX + Y_OFFSCREEN,
							COLOR_BACKGROUND);
					points.reset();
					gameTimer.setDelay(SPEED_DELAY_INITIAL);
					handlePieces();
					enableButtons(false);
					gameTimer.start();
				}
			}
		});

		// Exitbutton for the game
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameTimer.stop();
				System.exit(0);
			}
		});

		// Show HighScore for the game
		scoreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				highScore.showAllScore();
			}
		});

		// Show help window for the game
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				help();
			}
		});

		// Timer for graphics automatic movement
		gameTimer = new Timer(SPEED_DELAY_INITIAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameTimer.setDelay(SPEED_DELAY_INITIAL
						- (points.getLevel() * SPEED_DELAY_STEP));
				tetroMover(Action.DOWN);
				statusUpdate();
			}
		});

		// Timer for colorizing of deleted row
		colorizeTimer = new Timer(50, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reDrawGamePanel();
				if (colorizeCount > 3) {
					gp.deleteFullRows();
					reDrawGamePanel();
					gameTimer.start();
					colorizeTimer.stop();
				} else {
					gp.colorizeFullRows((colorizeCount % 2 == 1) ? Color.white
							: Color.black);
				}
				colorizeCount++;
			}
		});

		// Setup frame
		setTitle("Tetris");
		setLocationRelativeTo(null); // center on screen
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Close game if exit
		setVisible(true);
	}

	/**
	 * All keys for the game is handled here
	 */
	public class gameKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent keyEvent) {
			switch (keyEvent.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				tetroMover(Action.LEFT); // Left arrow: Move left
				break;
			case KeyEvent.VK_RIGHT: // Right arrow: Move right
				tetroMover(Action.RIGHT);
				break;
			case KeyEvent.VK_DOWN:
				tetroMover(Action.DOWN); // Down arrow: Soft drop
				break;
			case KeyEvent.VK_SPACE: // Space: Hard drop
				tetroMover(Action.DROP);
				break;
			case KeyEvent.VK_UP: // Up arrow: Rotate counterclockwise
				tetroMover(Action.CCW);
				break;
			case KeyEvent.VK_Z: // Z/z: Rotate clockwise
				tetroMover(Action.CW);
				break;
			case KeyEvent.VK_W: // W/w: Manually increase level
				if (points.getLevel() < LEVEL_MAX)
					points.nextLevel();
				break;
			case KeyEvent.VK_N: // N/n: Turn display of next piece on/off
				showNextPiece = !showNextPiece;
				reDrawNextPiecePanel();
				break;
			case KeyEvent.VK_ESCAPE: // Escape: Quits the game
				gameTimer.stop();
				System.exit(0);
				break;
			}
		}
	}

	// Handles all actions to move tetraminos
	private void tetroMover(Action act) {
		if (gameTimer.isRunning()) { // Movement only possible when game is
										// started
			switch (act) {
			case DOWN:
				if (piece.isMoveOK(Tetromino.Direction.DOWN, gp))
					piece.move(Tetromino.Direction.DOWN, gp);
				else {
					piece.setBusy(gp);
					pieceStuck = true;
					if (!nextPiece.isMoveOK(Tetromino.Direction.DOWN, gp)) {
						gameEnded();
					}
					int lines = gp.colorizeFullRows(Color.white);
					points.linesDeleted(lines);
					if (lines > 0) {
						gameTimer.stop();
						colorizeCount = 0;
						colorizeTimer.start();
					}
					handlePieces();
				}
				break;
			case DROP:
				pieceStuck = false;
				do {
					tetroMover(Action.DOWN);
				} while (!pieceStuck);
				gameTimer.setInitialDelay(gameTimer.getDelay());
				if (gameTimer.isRunning())
					gameTimer.restart();
				break;
			case LEFT:
				if (piece.isMoveOK(Tetromino.Direction.LEFT, gp))
					piece.move(Tetromino.Direction.LEFT, gp);
				break;
			case RIGHT:
				if (piece.isMoveOK(Tetromino.Direction.RIGHT, gp))
					piece.move(Tetromino.Direction.RIGHT, gp);
				break;
			case CW:
				if (piece.isMoveOK(Tetromino.Direction.CW, gp))
					piece.move(Tetromino.Direction.CW, gp);
				break;
			case CCW:
				if (piece.isMoveOK(Tetromino.Direction.CCW, gp))
					piece.move(Tetromino.Direction.CCW, gp);
			default:
				break;
			}
			reDrawGamePanel();
		}
	}

	// Handles creation of active and next Tetromino pieces including the panel
	private void handlePieces() {
		if (nextPiece == null)
			piece = new Tetromino(Tetromino.randomTetro(), new Point(X_START,
					Y_START));
		else
			piece = nextPiece;
		nextPiece = new Tetromino(Tetromino.randomTetro(), new Point(X_START,
				Y_START));
		reDrawNextPiecePanel();
	}

	// ends the game, checking highscore etc
	private void gameEnded() {
		gameTimer.stop();
		if (highScore.isHighScore(points.getPoints())) {
			highScore.newHighScore(points.getPoints());
			highScore.showAllScore();
		} else {
			highScore.showGameOver(points.getPoints());
		}
		enableButtons(true);
	}

	// Enables or disenable buttons
	private void enableButtons(boolean b) {
		startButton.setEnabled(b);
		scoreButton.setEnabled(b);
		helpButton.setEnabled(b);
	}

	// Uppdates status for score etc
	private void statusUpdate() {
		scoreLabel.setText(String.valueOf(points.getPoints()));
		linesLabel.setText(String.valueOf(points.getLines()));
		levelLabel.setText(String.valueOf(points.getLevel()));
	}

	// Redraws gamepanel
	private void reDrawGamePanel() {
		for (int x = 0; x < X_MAX; x++) {
			for (int y = 0; y < Y_MAX; y++) {
				gameArea[x][y].setBackground(gp.getColor(new Point(x, y)));
			}
		}
	}

	// Redraws next piece panel
	private void reDrawNextPiecePanel() {
		GamePlan ngp = new GamePlan(4, 4, COLOR_BACKGROUND);
		if (showNextPiece) {
			Point startPos = nextPiece.getStartPos();
			nextPiece.setStartPos(new Point(1, 2)); // Position in next-area
			nextPiece.move(Tetromino.Direction.FREEZE, ngp);
			nextPiece.setBusy(ngp);
			nextPiece.setStartPos(startPos);
		}
		for (int x = 0; x < 4; x++)
			for (int y = 0; y < 4; y++)
				pieceArea[x + 1][y]
						.setBackground(ngp.getColor(new Point(x, y)));
	}

	// Shows a help dialog on screen
	private void help() {
		JTextArea message = new JTextArea();
		message.append("\n The following keys control the game:\n\n");
		message.append(" Left \t- Move tetromino to the left\n");
		message.append(" Right \t- Move tetromino to the right\n");
		message.append(" Up \t- Rotates the tetromino counterclockwise\n");
		message.append(" Z \t- Rotates the tetromino clockwise\n");
		message.append(" Down \t- Soft drops the tetromino\n");
		message.append(" Space \t- Drops the tetromino\n");
		message.append(" N \t- Show/Hide next tetromino\n");
		message.append(" W \t- Go to next level\n");
		message.append(" Esc \t- Quit Tetris\n\n");
		message.append(" You get score for every cleared line.\n");
		message.append(" Combo points for clearing multiple lines!");
		TOptionPane.showMessageDialog(this, message, "Tetris Help ", font,
				stdSquare, 20);
	}

	/**
	 * Starts the game in a event dispatch thread
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Tetris();
			}
		});
	}
}
