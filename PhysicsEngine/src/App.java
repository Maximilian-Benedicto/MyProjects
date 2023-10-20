import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import java.util.Random;

public class App {
    public static Frame frame;
    public static Physics physics;
    public static Visual visual;
    public static int simulationRate = 60;
    public static double deltaTime = (double)simulationRate/1000;
    public static double gravity = 9.82;
    public static double friction = 0.5;

    static class Loop extends TimerTask{
        @Override
        public void run(){
           visual.updateVisual();
           physics.updatePhysics(1);
        }
    }
    

    public static void main(String[] args) throws Exception {
        Random random = new Random();
        Timer timer = new Timer();
        physics = new Physics();
        visual = new Visual();
        frame = new Frame(500, 500);
        timer.scheduleAtFixedRate(new Loop(), 0, 1000/simulationRate);
        Thread.sleep(100); //is needed to keep ball from spawning in the corner
        for (int index = 0; index < 30; index++) {
            Color color = new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
            physics.newBall(color, random.nextDouble(1), random.nextDouble(5,30), random.nextDouble(frame.getFrameWidth()), random.nextDouble(frame.getFrameHeigth()), random.nextDouble(-300, 300),random.nextDouble(-300, 300));
        }
    }
}
