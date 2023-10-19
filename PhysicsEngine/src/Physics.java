import java.util.ArrayList;

public class Physics {
    private static ArrayList<Ball> balls = new ArrayList<>();

    public class Ball {
        double radius;
        double[] position = new double[2];
        double[] speed = new double[2];
        double[] acceleration = new double[2];

        private Ball(double radius, double positionX, double positionY){
            this.radius = radius;
            position[0] = positionX;
            position[1] = positionY;
        }
    }

    public void newBall(double radius, double positionX, double positionY){
        balls.add(new Ball(radius, positionX, positionY));
    }

    public static ArrayList<Ball> getBalls() {
        return balls;
    }

    public void move(){
        for (Ball ball : balls) {
            ball.speed[0] += ball.acceleration[0];
            ball.speed[1] += ball.acceleration[1];
            ball.position[0] += ball.speed[0];
            ball.position[1] += ball.speed[1];
            ball.acceleration[0] = 0;
            ball.acceleration[1] = -9.82;
        }
    }

    public void collision(){
        for (Ball ball : balls) {
            if (ball.position[1] <= 0 || ball.position[1] >= App.frame.frameHeight) {
                ball.speed[1] = -ballSpeed[1];
        } 
        }
    }

    public void updatePhysics() {
        move();
        collision();
    }
}