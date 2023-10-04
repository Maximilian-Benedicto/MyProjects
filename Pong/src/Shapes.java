
import javax.swing.JComponent;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Shapes extends JComponent{
    public ArrayList<Paddle> paddles = new ArrayList<>();
    public ArrayList<Ball> balls = new ArrayList<>();

    double paddleIncrement;
    double[] ballSpeed = new double[2];
    int frameWidth;
    int frameHeight;
    
    public void setAccelerationPaddle(int index, double y){
        Paddle specificShape = paddles.get(index);
        specificShape.acceleration = y;
    }
    public void resetAccelerationPaddle(){
        for (Paddle paddle : paddles) {
            paddle.acceleration = 0;
        }
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
        double[] position = new double[2];
        double speed = 0;
        double acceleration = 0;
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
        double[] position = new double[2];
        double[] speed = ballSpeed;
        public Ball(int x, int y, int r){
            position[0] = x;
            position[1] = y;
            radius = r;
            balls.add(this);
        }
    }
    public void updateGraphics(){
        for (Paddle paddle : paddles) {
            paddle.speed += paddle.acceleration;
            paddle.position[1] += paddle.speed;
            paddle.position[1] = Math.max(0,Math.min(frameHeight-paddle.size[1],paddle.position[1]));
        }
        for (Ball ball : balls) {
            ball.position[0] += ball.speed[0];
            ball.position[1] += ball.speed[1];
            if (ball.position[0] <= 0 || ball.position[0] >= frameWidth) {
                ball.speed[0] = -ballSpeed[0];
            } 
            if (ball.position[1] <= 0 || ball.position[1] >= frameHeight) {
                ball.speed[1] = -ballSpeed[1];
            } 
            
        repaint();
        }
    }
    
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        for (Paddle paddle : paddles) {
            Rectangle2D.Double r = new Rectangle2D.Double(paddle.position[0], paddle.position[1], paddle.size[0], paddle.size[1]);
            g2d.fill(r);
        }
        for (Ball ball : balls) {
            Ellipse2D.Double e = new Ellipse2D.Double(ball.position[0]-(ball.radius/2), ball.position[1]-(ball.radius/2), ball.radius, ball.radius);
            g2d.fill(e);
        }    
    }

}
