import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class App {
    public static void main(String[] args) throws Exception {
        Shapes shapes = new Shapes();
        JFrame frame = new JFrame();
        frame.setSize(500,500);
        frame.setTitle("Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(shapes);
        frame.setResizable(false); 
        shapes.newRectangle(30,10,10,10);
        shapes.newRectangle(10,10,10,10);
        int x = 0;
        int y = 0;
        for (int index = 0; index < 500; index++) {
            shapes.setPosition(0, x, y);
            Thread.sleep(10);
            x++;
            y++;
            shapes.updateGraphics();
        }
        shapes.setPosition(0, 50, 50);
        shapes.setSize(1, 40, 40);
        shapes.newRectangle(10,10,10,10);
        
    }
}
