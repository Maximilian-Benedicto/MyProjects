import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

public class Visual extends JFrame {

    static final int frameHeight = 490;
    static final int frameWidth = 500;

    JLayeredPane gameLayeredPane = new JLayeredPane();
    ImageIcon backgroundImage = new ImageIcon("D:\\Documents\\Repositories\\MyProjects\\EpicFlappyBird\\png\\background.png");
    Background b1;
    Background b2;
    Timer timer;
    int delay = 16;

    private class Background extends JLabel {
        
        public Background(int x, int y) {
            setIcon(backgroundImage);
            setBounds(x, y, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
        }
    }

    
    public void drawPipe(int x, int y) {

    }

    public void drawBird(int x, int y) {

    }

    public void setScrollingSpeed(int speed) {

    }

    public Visual() {
        setTitle("Flappy Bird");
        setResizable(false);
        setSize(frameWidth,frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        b1 = new Background(0, 0);

        gameLayeredPane.add(b1, JLayeredPane.DEFAULT_LAYER);

        setContentPane(gameLayeredPane);

        
        
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        })

        setVisible(true);

    }

    

}
