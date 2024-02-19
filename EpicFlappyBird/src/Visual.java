import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Visual extends JFrame {

    static final int frameHeight = 490;
    static final int frameWidth = 608;

    JLayeredPane gameLayeredPane = new JLayeredPane();
    ImageIcon backgroundImageIcon = new ImageIcon("png/background.png");
    ImageIcon pipeImageIcon = scaleImageIcon("png/pipe.png", 2);
    ImageIcon birdImageIcon = scaleImageIcon("png/bird.png", 2);
    Background b1;
    Background b2;
    int numberOfPipes = 3;
    Timer timer;
    int backgroundSpeed = -1;
    int pipeSpeed = -2;
    int gameSpeed = 16;
    Random random = new Random();

    private ImageIcon scaleImageIcon(String filePath, int scale) {
        ImageIcon imageIcon = new ImageIcon(filePath);
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(imageIcon.getIconWidth() * scale, imageIcon.getIconHeight() * scale,
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

    public void setGameSpeed(int gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public Visual() {
        setTitle("Flappy Bird");
        setResizable(true);
        setSize(frameWidth, frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        b1 = new Background(0, 0);
        b2 = new Background(frameWidth, 0);

        gameLayeredPane.add(b1, JLayeredPane.DEFAULT_LAYER);
        gameLayeredPane.add(b2, JLayeredPane.DEFAULT_LAYER);

        Pipe[] pipes = new Pipe[numberOfPipes];
        for (int index = 0; index < numberOfPipes; index++) {
            int xPosition = (((index) * frameWidth) / numberOfPipes);
            int yPosition = -250;
            pipes[index] = new Pipe(xPosition, yPosition);
            gameLayeredPane.add(pipes[index], JLayeredPane.PALETTE_LAYER);
        }

        setContentPane(gameLayeredPane);

        timer = new Timer(gameSpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                b1.moveX(backgroundSpeed);
                b2.moveX(backgroundSpeed);

                if (b1.getLocation().getX() <= -backgroundImageIcon.getIconWidth()) {
                    b1.setX(frameWidth);
                }
                if (b2.getLocation().getX() <= -backgroundImageIcon.getIconWidth()) {
                    b2.setX(frameWidth);
                }

                for (Pipe pipe : pipes) {
                    pipe.moveX(pipeSpeed);
                    if (pipe.getLocation().getX() <= -pipeImageIcon.getIconWidth()) {
                        pipe.setLocation(frameWidth, -random.nextInt(150,350));
                    }
                }

            }
        });

        timer.start();

        setVisible(true);

    }

}
