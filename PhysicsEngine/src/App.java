import java.util.Timer;
import java.util.TimerTask;

public class App {
    public static Frame frame;
    public static Physics physics;
    public static Visual visual;

    static class Loop extends TimerTask{
        @Override
        public void run(){
           visual.updateVisual();
           physics.updatePhysics();
        }
    }

    public static void main(String[] args) throws Exception {
        Timer timer = new Timer();
        physics = new Physics();
        visual = new Visual();
        frame = new Frame(1000, 1000);
        physics.newBall(10, 500, 500);
        timer.scheduleAtFixedRate(new Loop(), 0, 1000);
    }
}
