import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Visual extends JFrame {

    static final int frameHeight = 490;
    static final int frameWidth = 608;

    JLayeredPane gameLayeredPane = new JLayeredPane();
    ImageIcon backgroundImageIcon = new ImageIcon("png/background.png");
    ImageIcon pipeImageIcon = scaleImageIcon(new ImageIcon("png/pipe.png"), 2);
    Background b1;
    Background b2;
    Pipe p1;
    Pipe p2;
    Pipe p3;
    Timer timer;
    int delay = 16;

    private ImageIcon scaleImageIcon(ImageIcon imageIcon, int scale) {
        Image image = imageIcon.getImage();
        image.getScaledInstance(imageIcon.getIconWidth() * scale, imageIcon.getIconHeight() * scale,
                Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    private class Pipe extends JLabel {
        public Pipe(int x, int y) {
            setIcon(pipeImageIcon);
            setBounds(x, y, pipeImageIcon.getIconWidth(), pipeImageIcon.getIconHeight());
        }

        public void moveX(int dx) {
            setLocation(getLocation().x + dx, getLocation().y);
        }

        public void setX(int x) {
            setLocation(x, getLocation().y);
        }
    }

    private class Background extends JLabel {
        public Background(int x, int y) {
            setIcon(backgroundImageIcon);
            setBounds(x, y, backgroundImageIcon.getIconWidth(), backgroundImageIcon.getIconHeight());
        }

        public void moveX(int dx) {
            setLocation(getLocation().x + dx, getLocation().y);
        }

        public void setX(int x) {
            setLocation(x, getLocation().y);
        }
    }

    public Hitbox[] getHitboxs() {
        return null;
    }

    public void setScrollingSpeed(int delay) {
        this.delay = delay;
    }

    public Visual() {
        setTitle("Flappy Bird");
        setResizable(false);
        setSize(frameWidth, frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        b1 = new Background(0, 0);
        b2 = new Background(frameWidth, 0);

        gameLayeredPane.add(b1, JLayeredPane.DEFAULT_LAYER);
        gameLayeredPane.add(b2, JLayeredPane.DEFAULT_LAYER);

        setContentPane(gameLayeredPane);

        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                b1.moveX(-1);
                b2.moveX(-1);

                if (b1.getLocation().getX() <= -backgroundImageIcon.getIconWidth()) {
                    b1.setLocation(frameWidth, 0);
                }
                if (b2.getLocation().getX() <= -backgroundImageIcon.getIconWidth()) {
                    b2.setLocation(frameWidth, 0);
                }
            }
        });

        timer.start();

        setVisible(true);

    }

}
