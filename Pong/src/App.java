import javax.swing.*;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class App {
    private static Set<Integer> pressedKeys = new HashSet<>();
    private static Shapes shapes; //creates a class field that can be modified outside of main method

    public static void main(String[] args) throws Exception {
        shapes = new Shapes();
        JFrame frame = new JFrame();
        final int frameWidth = 500;
        final int frameHeight = 500;
        final double paddleIncrement = 0.5;
        final double[] ballSpeed = {2.3,4.1};
        frame.setSize(frameWidth, frameHeight);
        frame.setTitle("Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.add(shapes);
        frame.getContentPane().setBackground(Color.BLACK);
        shapes.frameWidth = frame.getContentPane().getWidth();
        shapes.frameHeight = frame.getContentPane().getWidth();
        shapes.paddleIncrement = paddleIncrement;
        shapes.ballSpeed = ballSpeed;
        shapes.newPaddle(0, 0, 10, 100);
        shapes.newPaddle(shapes.frameWidth - 10, 0, 10, 100);
        shapes.newBall((frameHeight / 2) - 5, (frameWidth / 2) - 5, 10);

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
                shapes.resetAccelerationPaddle();
            }
        });

        while (true) {
            Thread.sleep(10);
            shapes.updateGraphics();
        }
    }

    private static void handleSimultaneousInput() {
        if (pressedKeys.contains(KeyEvent.VK_UP)) {
            shapes.setAccelerationPaddle(1, -shapes.paddleIncrement);
        }
        if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
            shapes.setAccelerationPaddle(1, shapes.paddleIncrement);
        }
        if (pressedKeys.contains(KeyEvent.VK_W)) {
            shapes.setAccelerationPaddle(0, -shapes.paddleIncrement);
        }
        if (pressedKeys.contains(KeyEvent.VK_S)) {
            shapes.setAccelerationPaddle(0, shapes.paddleIncrement);
        }
    }
}
