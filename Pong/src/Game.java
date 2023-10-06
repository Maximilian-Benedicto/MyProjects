
import javax.swing.JComponent;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Game extends JComponent{
    public ArrayList<Paddle> paddles = new ArrayList<>();
    public ArrayList<Ball> balls = new ArrayList<>();

    double paddleIncrement;
    double ballVerticalAcceleration;
    double paddleBounce;
    double[] ballSpeed = new double[2];
    int frameWidth;
    int frameHeight;
    int paddleSize;
    int paddleWidth;
    int[] score = new int[2];
    /**
     * 
     * @param index which paddle 0-1
     * @param y what acceleration
     */
    public void setAccelerationPaddle(int index, double y){
        paddles.get(index).acceleration = y;
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
    public void newPaddle(int x, int y, int w, int h, int index){
        new Paddle(x,y,w,h,index);
    } 
    private class Paddle {
        int[] size = new int[2];
        double[] position = new double[2];
        double speed = 0;
        double acceleration = 0;
        public Paddle(int x, int y, int w, int h, int index){
            position[0] = x;
            position[1] = y;
            size[0] = w;
            size[1] = h;
            paddles.add(index, this);
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
    public void removeBalls(){
        balls.removeAll(balls);
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
    /**
     * Checks collisions and detects if player has scored
     * @param ball to check and move
     * @return which player has scored (0 or 1) or 3 if no one has scored
     */
    public Integer ballCollision(Ball ball){
        if (ball.position[1] <= 0 || ball.position[1] >= frameHeight) {
                ball.speed[1] = -ballSpeed[1];
        } 
        if (ball.position[0] < 10 || ball.position[0] > frameWidth - 10) {
            if (ball.position[0] <= 0 || ball.position[0] >= frameWidth) {
                if (ball.position[0] <= 0) {
                    ball.position[0] = frameWidth/2;
                    ball.position[1] = frameHeight/2;
                    return(1);
                } else if (ball.position[0] >= frameWidth) {
                    ball.position[0] = frameWidth/2;
                    ball.position[1] = frameHeight/2;
                    return(0);
                }
            } else {
                if (ball.position[0] < paddleWidth && (ball.position[1] > paddles.get(0).position[1] && ball.position[1] < paddles.get(0).position[1] + paddleSize )) {
                    ball.speed[1] += paddles.get(0).speed * ballVerticalAcceleration;
                    ball.speed[0] = -ball.speed[0];
                } else if (ball.position[0] > frameWidth - paddleWidth && (ball.position[1] > paddles.get(1).position[1] && ball.position[1] < paddles.get(1).position[1] + paddleSize )) {
                    ball.speed[1] += paddles.get(1).speed * ballVerticalAcceleration;
                    ball.speed[0] = -ball.speed[0];
                }
            }       
        }
        return(3);
    } 
    public void updateGraphics(){
        for (Paddle paddle : paddles) {
            paddle.speed += paddle.acceleration;
            paddle.position[1] += paddle.speed;
            paddle.position[1] = Math.max(0,Math.min(frameHeight-paddle.size[1],paddle.position[1]));
            if (paddle.position[1]==0 || paddle.position[1]==frameHeight-paddle.size[1]) {
                paddle.speed = -paddleBounce*paddle.speed;
                paddle.acceleration = 0;
            }
        }
        for (Ball ball : balls) {
            ball.position[0] += ball.speed[0];
            ball.position[1] += ball.speed[1];
            int ballCollision = ballCollision(ball);
            if (!(ballCollision==3)) {
                score[ballCollision]++;
                System.out.println("Score -->   player 0: "+score[0]+"player 1: "+score[1]);
            }
        }
        repaint();
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
