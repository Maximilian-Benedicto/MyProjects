import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class Pipe extends JLabel {
    private int x;
    private int y;

    public Pipe(ImageIcon pipeIcon, int x, int y) {
        super(pipeIcon);
        this.x = x;
        this.y = y;
        setBounds(x, y, pipeIcon.getIconWidth(), pipeIcon.getIconHeight());
    }

    public void move(int dx) {
        x -= dx;
        setLocation(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

public class FlappyBird extends JFrame {
    private ImageIcon backgroundImage = new ImageIcon("png/background.png");
    private ImageIcon pipeIcon = getScaledImage(new ImageIcon("png/pipe.png"), 2);
    private JLayeredPane gameLayeredPane = new JLayeredPane();
    private JLabel background1 = new JLabel(backgroundImage);
    private JLabel background2 = new JLabel(backgroundImage);
    private Pipe pipe1 = new Pipe(pipeIcon, 0, -300);
    private Pipe pipe2 = new Pipe(pipeIcon, 250, -200);
    private int background1X;
    private int background2X;
    private Timer timer;
    private Random random = new Random();

    public ImageIcon getScaledImage(ImageIcon icon, int scale) {
        Image image = icon.getImage().getScaledInstance(icon.getIconWidth() * scale, icon.getIconHeight() * scale, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    public FlappyBird() {
        setTitle("Flappy Bird");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 490);

        int frameHeight = backgroundImage.getIconHeight();
        setContentPane(gameLayeredPane);

        background1X = 0;
        background1.setBounds(background1X, 0, backgroundImage.getIconWidth(), frameHeight);
        gameLayeredPane.add(background1, JLayeredPane.DEFAULT_LAYER);

        background2X = backgroundImage.getIconWidth();
        background2.setBounds(background2X, 0, backgroundImage.getIconWidth(), frameHeight);
        gameLayeredPane.add(background2, JLayeredPane.DEFAULT_LAYER);

        gameLayeredPane.add(pipe1, JLayeredPane.PALETTE_LAYER);
        gameLayeredPane.add(pipe2, JLayeredPane.PALETTE_LAYER);

        int delay = 10;
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                background1X -= 1;
                background2X -= 1;

                if (background1X <= -backgroundImage.getIconWidth()) {
                    background1X = getWidth();
                }
                if (background2X <= -backgroundImage.getIconWidth()) {
                    background2X = getWidth();
                }

                pipe1.move(1);
                pipe2.move(1);

                if (pipe1.getX() <= -pipeIcon.getIconWidth()) {
                    pipe1.setLocation(getWidth(), -random.nextInt(200, 300));
                }
                if (pipe2.getX() <= -pipeIcon.getIconWidth()) {
                    pipe2.setLocation(getWidth(), -random.nextInt(200, 300));
                }

                background1.setLocation(background1X, 0);
                background2.setLocation(background2X, 0);
            }
        });

        timer.start();

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FlappyBird());
    }
}
