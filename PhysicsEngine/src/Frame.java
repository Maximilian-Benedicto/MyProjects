import javax.swing.JFrame;
import java.awt.*;

public class Frame{
    public static int frameWidth;
    public static int frameHeigth;

    public Frame(int frameWidth, int frameHeigth){
        this.frameWidth = frameWidth;
        this.frameHeigth = frameHeigth; 
        JFrame window = new JFrame();
        window.setSize(frameWidth, frameHeigth);
        window.setTitle("PhysicsEngine");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setResizable(false);
        window.add(App.visual);
        window.getContentPane().setBackground(Color.BLUE);
    }
}