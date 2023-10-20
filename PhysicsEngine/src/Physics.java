import java.util.ArrayList;
import java.awt.*;

public class Physics {
    private static ArrayList<Ball> balls = new ArrayList<>();

    public class Ball {
        double radius;
        double bounce;
        Color color;
        double[] position = new double[2];
        double[] speed = new double[2];
        double[] acceleration = new double[2];

        public String toString(){
            return ("X: " + position[0] + " Y: " + position[1] + "Color RGB: " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
        } 
        
        private Ball(Color color, double bounce, double radius, double positionX, double positionY, double accelerationX, double accelerationY){
            this.color = color;
            this.radius = radius;
            this.bounce = bounce;
            position[0] = positionX;
            position[1] = positionY;
            acceleration[0] = accelerationX;
            acceleration[1] = accelerationY;
        }
    }

    public void newBall(Color color, double bounce, double radius, double positionX, double positionY, double accelerationX, double accelerationY){
        balls.add(new Ball(color, bounce, radius, positionX, positionY, accelerationX, accelerationY));
    }

    public static ArrayList<Ball> getBalls() {
        return balls;
    }

    public void move(){
        for (Ball ball : balls) {
            ball.speed[0] += ball.acceleration[0];
            ball.speed[1] += ball.acceleration[1];
            ball.position[0] += ball.speed[0]*App.deltaTime;
            ball.position[1] += ball.speed[1]*App.deltaTime;
            ball.acceleration[0] = 0;
            ball.acceleration[1] = App.gravity;
        }
    }
    

    public void collision(){
        for (Ball ballA : balls) {
            for (Ball ballB : balls) {
                if (!(ballA == ballB)) {
                    double distanceBetweenBalls = Math.sqrt(Math.pow(ballB.position[0] - ballA.position[0],2) + Math.pow(ballB.position[1] - ballA.position[1],2)); //d=√((x2 – x1)^2 + (y2 – y1)^2)
                    double slopeBetweenBalls = (ballB.position[1] - ballA.position[1])/(ballB.position[0] - ballA.position[0]); //m=(y2 - y1)/(x2 - x1)
                    double newDistanceBetweenBalls = ballA.radius + ballB.radius;
                    
                    if (distanceBetweenBalls < ballA.radius + ballB.radius) {
                        double deltaX = (newDistanceBetweenBalls/2)/Math.sqrt(1+Math.pow(slopeBetweenBalls,2)); //Δx=d/√(1+m^2​)​
                        double deltaY = slopeBetweenBalls*deltaX; //Δy=m*Δx
                        ballA.position[0] += deltaX/2;
                        ballA.position[1] += deltaY/2;
                        ballB.position[0] -= deltaX/2;
                        ballB.position[1] -= deltaY/2;
                        /*ballA.acceleration[0] += deltaX/2;
                        ballA.acceleration[1] += deltaY/2;
                        ballB.acceleration[0] -= deltaX/2;
                        ballB.acceleration[1] -= deltaY/2;*/
                        /*System.out.println("BallA: " + ballA + " and ballB: " + ballB + " just collided!");
                        System.out.println("distanceBetweenBalls: " + distanceBetweenBalls);
                        System.out.println("slopeBetweenBalls: " + slopeBetweenBalls);
                        System.out.println("newDistanceBetweenBalls" + newDistanceBetweenBalls);
                        System.out.println("deltaX: " + deltaX);
                        System.out.println("deltaY: " + deltaY);*/
                    }  
                }  
            }
        }
        for (Ball ball : balls) {
            if (ball.position[0] <= 0 + ball.radius) {
                ball.speed[0] *= -ball.bounce;
                ball.position[0] = 0 + ball.radius;
            }
            if (ball.position[0] >= App.frame.getFrameWidth() - ball.radius) {
                ball.speed[0] *= -ball.bounce;
                ball.position[0] = App.frame.getFrameWidth() - ball.radius;
            }
            if (ball.position[1] <= 0) {
                ball.speed[0] *= -ball.bounce;
                ball.position[1] = 0;   
            }
            if (ball.position[1] >= App.frame.getFrameHeigth() - ball.radius) {
                ball.speed[1] *= -ball.bounce;
                ball.position[1] = App.frame.getFrameHeigth() - ball.radius;
                ball.speed[0] -= (ball.speed[0] * App.friction)*App.deltaTime;
            }
        }
        
    }

    public void updatePhysics(int iteration) {
        for (int index = 0; index < iteration; index++) {
                    collision();
        }
        move();
        
    }
}