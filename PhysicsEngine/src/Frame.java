import javax.swing.JFrame;
import java.awt.Color;

public class Frame{
    public int getFrameWidth(){
        return App.visual.getWidth();
    }

    public int getFrameHeigth(){
        return App.visual.getHeight();
    }



    public Frame(int frameWidth, int frameHeigth){
        JFrame window = new JFrame();
        window.setSize(frameWidth, frameHeigth);
        window.setTitle("PhysicsEngine");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setResizable(true);
        window.add(App.visual);
        window.getContentPane().setBackground(Color.BLUE);
    }
}