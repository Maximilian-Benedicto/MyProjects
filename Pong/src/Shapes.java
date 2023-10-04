
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
    /**
     * 
     * @param index which paddle 0-1
     * @param y what acceleration
     */
    public void setAccelerationPaddle(int index, double y){
        Paddle specificShape = paddles.get(index);
        specificShape.acceleration = y;
    }
    /**
     * resets acceleration on all paddles
     */
    public void resetAccelerationPaddle(){
        for (Paddle paddle : paddles) {
            paddle.acceleration = 0;
        }
    }
    /**
     * public constructor for new paddle, coordinates are top-left corner
     * @param x position 
     * @param y position
     * @param w width
     * @param h heigth
     */
    public void newPaddle(int x, int y, int w, int h){
        new Paddle(x,y,w,h);
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
    /**
     * constructor for new ball, position is in the center of the ball
     * @param x position
     * @param y position
     * @param r radius
     */
    public void newBall(int x, int y, int r){
        new Ball(x,y,r);
    }
    /**
     * sets position of the ball
     * @param index which ball
     * @param x position
     * @param y position
     */
    public void setPositionBall(int index, int x, int y){
            Ball specificShape = balls.get(index);
            specificShape.position[0] = x;
            specificShape.position[1] = y;
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
