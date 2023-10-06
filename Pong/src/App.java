import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class App {
    private static Set<Integer> pressedKeys = new HashSet<>();
    private static Game game; //creates a class field that can be modified outside of main method

    static class GameLoop extends TimerTask{
        @Override
        public void run(){
            game.updateGraphics();
            if (game.score[0]==5) {
                game.removeBalls();
                System.out.println("Player 0 wins!!!");
            }
            if (game.score[1]==5) {
                game.removeBalls();
                System.out.println("Player 1 wins!!!");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Timer timer = new Timer();
        game = new Game();
        JFrame frame = new JFrame();
        final int frameWidth = 500;;
        final int frameHeight = 500;
        final double paddleIncrement = 0.5;
        final double[] ballSpeed = {3,4};
        final int paddleSize = 100;
        final int paddleWidth = 10;
        final double ballVerticalAcceleration = 0.1;
        final double paddleBounce = 0.3;
        frame.setSize(frameWidth, frameHeight);
        frame.setTitle("Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.add(game);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.addKeyListener(new KeyAdapter() {
            @Override //overrides method defined in KeyListener
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                pressedKeys.add(keyCode);
                handleSimultaneousInput();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                pressedKeys.remove(keyCode);
                handleSimultaneousInput();
                game.resetAccelerationPaddle();
            }
        });

        game.frameWidth = frame.getContentPane().getWidth();
        game.frameHeight = frame.getContentPane().getHeight();
        game.ballSpeed = ballSpeed;
        game.paddleIncrement = paddleIncrement;
        game.paddleSize = paddleSize;
        game.paddleWidth = paddleWidth;
        game.ballVerticalAcceleration = ballVerticalAcceleration;
        game.paddleBounce = paddleBounce;
        game.newPaddle(0, 0, paddleWidth, paddleSize,0);
        game.newPaddle(game.frameWidth - 10, 0, paddleWidth, paddleSize,1);
        game.newBall((frameHeight / 2), (frameWidth / 2), 10);

        timer.scheduleAtFixedRate(new GameLoop(), 0, 1000/60);
    }

    private static void handleSimultaneousInput() {
        if (pressedKeys.contains(KeyEvent.VK_UP)) {
            game.setAccelerationPaddle(1, -game.paddleIncrement);
        }
        if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
            game.setAccelerationPaddle(1, game.paddleIncrement);
        }
        if (pressedKeys.contains(KeyEvent.VK_W)) {
            game.setAccelerationPaddle(0, -game.paddleIncrement);
        }
        if (pressedKeys.contains(KeyEvent.VK_S)) {
            game.setAccelerationPaddle(0, game.paddleIncrement);
        }
    }
}
