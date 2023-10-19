import javax.swing.JComponent;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Visual extends JComponent {


    public void updateVisual(){
        repaint();
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        for (Physics.Ball ball : Physics.getBalls()) {
            Ellipse2D.Double e = new Ellipse2D.Double(ball.position[0]-ball.radius, ball.position[1]-ball.radius, 2*ball.radius, 2*ball.radius);
            g2d.fill(e);
        }   
        
    }
}