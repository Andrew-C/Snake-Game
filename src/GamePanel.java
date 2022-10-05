import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int BLOCK_SIZE = 50;
    static final int NUM_BLOCKS = (SCREEN_WIDTH * SCREEN_HEIGHT) / BLOCK_SIZE;
    static final int DELAY = 100;
    final private int[] snakeX = new int[NUM_BLOCKS];
    final private int[] snakeY = new int[NUM_BLOCKS];
    private int bodySize = 6;
    private int pointX;
    private int pointY;
    private char direction = 'R';
    boolean gameOver = false;
    Random rand = new Random();
    Timer timer;

    public GamePanel() {
        super();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        startGame();
    }

    public void startGame() {
        makePoint();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        for (int i = 0; i < SCREEN_HEIGHT / BLOCK_SIZE; i++) {
            // vertical lines
            g.drawLine(i * BLOCK_SIZE, 0, i * BLOCK_SIZE, SCREEN_HEIGHT);
            // horizontal lines
            g.drawLine(0, i * BLOCK_SIZE, SCREEN_WIDTH, i * BLOCK_SIZE);
        }
        // draw snake
        for (int i = 0; i < bodySize; i++) {
            if (i == 0) {
                g.setColor(new Color(17, 121, 44));
                g.fillOval(snakeX[i], snakeY[i], BLOCK_SIZE, BLOCK_SIZE);
            } else {
                g.setColor(new Color(35, 218, 91));
                g.fillOval(snakeX[i], snakeY[i], BLOCK_SIZE, BLOCK_SIZE);
            }
        }
        // draw point
        g.setColor(Color.yellow);
        g.fillOval(pointX, pointY, BLOCK_SIZE, BLOCK_SIZE);

    }

    public void makePoint() {
        pointX = (rand.nextInt(SCREEN_WIDTH / BLOCK_SIZE)) * BLOCK_SIZE;
        pointY = (rand.nextInt(SCREEN_HEIGHT / BLOCK_SIZE)) * BLOCK_SIZE;

    }

    public void move() {
        for (int i = bodySize; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        switch (direction) {
            case 'U':
                snakeY[0] = snakeY[0] - BLOCK_SIZE;
                break;
            case 'D':
                snakeY[0] = snakeY[0] + BLOCK_SIZE;
                break;
            case 'L':
                snakeX[0] = snakeX[0] - BLOCK_SIZE;
                break;
            case 'R':
                snakeX[0] = snakeX[0] + BLOCK_SIZE;
                break;
        }
    }

    public void checkPoint() {
        if (snakeX[0] == pointX && snakeY[0] == pointY) {
            makePoint();
            bodySize++;
        }
    }

    public void checkCollision() {
        // checking if head overlaps with any other body part
        for (int i = bodySize; i > 0; i--) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                gameOver = true;
                return;
            }
        }
        // out of bounds
        if (snakeX[0] >= SCREEN_WIDTH || snakeX[0] < 0 || snakeY[0] >= SCREEN_HEIGHT || snakeY[0] < 0) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            checkPoint();
            checkCollision();
        }
        repaint();
    }

    private class myKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                /* Keycodes
                Up = 38     Down = 40
                Left = 37   Right = 39
                 */
                case 38:
                    direction = (direction == 'D') ? 'D' : 'U';
                    break;
                case 40:
                    direction = (direction == 'U') ? 'U' : 'D';
                    break;
                case 37:
                    direction = (direction == 'R') ? 'R' : 'L';
                    break;
                case 39:
                    direction = (direction == 'L') ? 'L' : 'R';
                    break;
            }
        }
    }
}
