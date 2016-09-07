import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.Arrays;

public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 140;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;

    private Direction direction = Direction.RIGHT;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public Board() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon("/Users/hithacker/Downloads/images/snake/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("/Users/hithacker/Downloads/images/snake/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("/Users/hithacker/Downloads/images/snake/head.png");
        head = iih.getImage();
    }

    private void initGame() {

        dots = 3;

        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        
        if (inGame) {

            g.drawImage(apple, apple_x, apple_y, this);

            for(int z = 0; z < dots; z++) {
                if(z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }        
    }

    private void gameOver(Graphics g) {
        
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private void checkApple() {
        if((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            locateApple();
        }

    }

    private String firstFifteen(int[] arr) {
        int[] result = new int[15];
        for(int i = 0; i < 15; i++) {
            result[i] = arr[i];
        }
        return Arrays.toString(result);
    }

    private void move() {
        System.out.println("x before:" + firstFifteen(x));
        System.out.println("y before:" + firstFifteen(y));
        for(int i = dots - 1; i > 0; i--) {
            System.out.println("i: " + i);
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        

        System.out.println("B head-x: " + x[0] + ", head-y: " + y[0]
                           + ", d: " + direction);

        //Move the head
        if(direction == Direction.LEFT) {
            if(x[0] == 0) {
                x[0] = B_WIDTH - DOT_SIZE;
            } else {
                x[0] -= DOT_SIZE;
            }
        }
        if(direction == Direction.RIGHT) {
            if(x[0] == B_WIDTH - DOT_SIZE) {
                x[0] = 0;
            } else {
                x[0] += DOT_SIZE;
            }
        }
        if(direction == Direction.UP) {
            if(y[0] == 0) {
                y[0] = B_HEIGHT - DOT_SIZE;
            } else {
                y[0] -= DOT_SIZE;
            }
        }
        if(direction == Direction.DOWN) {
            if(y[0] == B_HEIGHT - DOT_SIZE) {
                y[0] = 0;
            } else {
                y[0] += DOT_SIZE;
            }
        }
        System.out.println("A head-x: "+x[0] + ", head-y: " + y[0]
                           + ", d: " + direction);
        System.out.println("x after:" + firstFifteen(x));
        System.out.println("y after:" + firstFifteen(y));
        System.out.print("\n");
    }

    private void checkCollision() {
        for(int z = 3; z < dots; z++) {
            if(x[0] == x[z] && y[0] == y[z]) {
                inGame = false;
                break;
            }
        }
    }

    private void locateApple() {
        apple_x = ((int)(Math.random() * RAND_POS)) * 10;
        apple_y = ((int)(Math.random() * RAND_POS)) * 10;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && direction != Direction.RIGHT) {
                direction = Direction.LEFT;
            }

            if ((key == KeyEvent.VK_RIGHT) && direction != Direction.LEFT) {
                direction = Direction.RIGHT;
            }

            if ((key == KeyEvent.VK_UP) && direction != Direction.DOWN) {
                direction = Direction.UP;
            }

            if ((key == KeyEvent.VK_DOWN) && direction != Direction.UP) {
                direction = Direction.DOWN;
            }
        }
    }
}
