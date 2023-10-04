import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class App {

    public static void main(String[] args) throws Exception {
        Shapes shapes = new Shapes();
        JFrame frame = new JFrame();

        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_UP) {
                    shapes.moveBall(0, 1, 1);
                    shapes.updateGraphics();
                } else {
                    shapes.moveBall(0, -1, -1);
                    shapes.updateGraphics();
                }
            }
        });

        frame.setSize(500, 500);
        frame.setTitle("Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(shapes);
        frame.setResizable(false);
        shapes.newPaddle(10, 10, 10, 10);
        shapes.newPaddle(10, 10, 10, 10);
        shapes.newBall(250, 250, 10);
    }
}
