
import javax.swing.JComponent;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Shapes extends JComponent{
    public ArrayList<Paddle> paddles = new ArrayList<>();
    public ArrayList<Ball> balls = new ArrayList<>();

    


    public void movePaddle(int index, int x, int y){
        Paddle specificShape = paddles.get(index);
        specificShape.position[0] += x;
        specificShape.position[1] += y;
    }
    public void newPaddle(int x, int y, int w, int h){
        new Paddle(x,y,w,h);
    }
    public void setPositionPaddle(int index, int x, int y){
            Paddle specificShape = paddles.get(index);
            specificShape.position[0] = x;
            specificShape.position[1] = y;
    }  
    public void setSizePaddle(int index, int w, int h){
            Paddle specificShape = paddles.get(index);
            specificShape.size[0] = w;
            specificShape.size[1] = h;
    }        
    private class Paddle {
        int[] size = new int[2];
        int[] position = new int[2];
        public Paddle(int x, int y, int w, int h){
            position[0] = x;
            position[1] = y;
            size[0] = w;
            size[1] = h;
            paddles.add(this);
        }
        
    }
    public void newBall(int x, int y, int r){
        new Ball(x,y,r);
    }
    public void moveBall(int index, int x, int y){
        Ball specificShape = balls.get(index);
        specificShape.position[0] += x;
        specificShape.position[1] += y;
    }
    public void setPositionBall(int index, int x, int y){
            Ball specificShape = balls.get(index);
            specificShape.position[0] = x;
            specificShape.position[1] = y;
    }  
    public void setSizeBall(int index, int r){
            Ball specificShape = balls.get(index);
            specificShape.radius = r;
    }
    private class Ball {
        int radius;
        int[] position = new int[2];
        public Ball(int x, int y, int r){
            position[0] = x;
            position[1] = y;
            radius = r;
            balls.add(this);
        }
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
        for (Ball ball : balls) {
            Ellipse2D.Double e = new Ellipse2D.Double(ball.position[0], ball.position[1], ball.radius, ball.radius);
            g2d.setColor(Color.GREEN);
            g2d.fill(e);
        }    
    }

}
