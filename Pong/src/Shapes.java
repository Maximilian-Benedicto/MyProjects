
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Shapes extends JComponent{
    public ArrayList<Paddle> paddles = new ArrayList<>();

    public void newPaddle(int x, int y, int w, int h){
        new Paddle(x,y,w,h);
    }

    public void setPosition(int index, int x, int y){
            Paddle specificShape = paddles.get(index);
            specificShape.position[0] = x;
            specificShape.position[1] = y;
    }  

    public void setSize(int index, int x, int y){
            Paddle specificShape = paddles.get(index);
            specificShape.size[0] = x;
            specificShape.size[1] = y;
    }  
            
    private class Paddle {
        int[] size = new int[2];
        int[] position = new int[2];
        Color color = new Color(123);
        public Paddle(int x, int y, int w, int h){
            position[0] = x;
            position[1] = y;
            size[0] = w;
            size[1] = h;
            paddles.add(this);
        }
        
    }

    public class Ball {

    }

    public void updateGraphics(){
        repaint();
    }
    
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (Paddle paddle : paddles) {
            Rectangle2D.Double r = new Rectangle2D.Double(paddle.position[0], paddle.position[1], paddle.size[0], paddle.size[1]);
            g2d.setColor(Color.RED);
            g2d.fill(r);
        }    
    }

}
