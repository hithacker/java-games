import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel
    implements ActionListener {

    private final int b_width = 350;
    private final int b_height = 350;
    private final int init_x_s1 = -40;
    private final int init_y_s1 = -40;
    private final int init_x_s2 = 0;
    private final int init_y_s2 = 150;
    private final int delay = 25;

    private Image s1, s2;
    private Timer timer;
    private int s1x, s1y, s2x, s2y;

    public Board() {
        initBoard();
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon("/Users/hithacker/Downloads/images/star.png");
        s1 = ii.getImage();
        s2 = ii.getImage();
    }

    private void initBoard() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(b_width, b_height));
        setDoubleBuffered(true);

        loadImage();

        s1x = init_x_s1;
        s1y = init_y_s1;
        s2x = init_x_s2;
        s2y = init_y_s2;

        timer = new Timer(delay, this);
        timer.start();
    }

    private void drawStar(Graphics g) {
        g.drawImage(s1, s1x, s1y, this);
        g.drawImage(s2, s2x, s2y, this);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawStar(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        s1x += 5;
        s1y += 5;
        s2x += 5;
        s2y += 0;

        if(s1y > b_height) {
            s1x = init_x_s1;
            s1y = init_y_s1;
        }

        if(s2x > b_width) {
            s2x = init_x_s2;
            s2y = init_y_s2;
        }

        repaint();
    }
}
