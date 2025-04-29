package snake;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Game extends JPanel implements ActionListener, KeyListener {
    private class Tile {
        int x, y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }


    
    Image backgroundImg;
    int lenght;
    int height;
    int tilesize = 25;
    Tile snakeStart;
    Tile food;
    Random random;
    Timer gameloop;
    int velocityX, velocityY;
    ArrayList<Tile> snakebody;
    boolean gameover = false;
    JButton restartButton;

    Game(int lenght, int height) {
        this.lenght = lenght;
        this.height = height;
        setPreferredSize(new Dimension(this.lenght, this.height));
        // setBackground(Color.black);
        backgroundImg = new ImageIcon(getClass().getResource("/resources/images.jpeg")).getImage();

        // backgroundImg = new ImageIcon("snake/images.jpeg").getImage(); // Load the background image
        setLayout(null); // Use null layout to position the button manually
        addKeyListener(this);
        setFocusable(true);

        snakeStart = new Tile(5, 5);
        snakebody = new ArrayList<>();
        food = new Tile(10, 10);
        random = new Random();
        placeFood();
        velocityX = 0;
        velocityY = 0;

        gameloop = new Timer(100, this);
        gameloop.start();

        // Add restart button
        restartButton = new JButton("Restart");
        restartButton.setBounds(lenght / 2 - 50, height / 2 + 30, 100, 30); // Position the button
        restartButton.setVisible(false); // Initially hidden
        restartButton.addActionListener(e -> restartGame());
        add(restartButton);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(food.x * tilesize, food.y * tilesize, tilesize, tilesize);
        g.setColor(Color.green);
        g.fillOval(snakeStart.x * tilesize, snakeStart.y * tilesize, tilesize, tilesize);

        for (int i = 0; i < snakebody.size(); i++) {
            Tile tile = snakebody.get(i);
            g.fillOval(tile.x * tilesize, tile.y * tilesize, tilesize, tilesize);
        }

        g.setFont(new Font("Arial", Font.BOLD, 20));
        if (gameover) {
            g.setColor(Color.red);
            g.drawString("Game Over", lenght / 2 - 50, height / 2);
            restartButton.setVisible(true); // Show the restart button
        } else {
            g.setColor(Color.white);
            g.drawString("Score: " + snakebody.size(), 10, 20);
        }
    }

    public void placeFood() {
        food.x = random.nextInt(lenght / tilesize);
        food.y = random.nextInt(height / tilesize);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        if (collision(snakeStart, food)) {
            placeFood();
            snakebody.add(new Tile(snakeStart.x, snakeStart.y));
        }

        for (int i = snakebody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakebody.get(i);
            if (i == 0) {
                snakePart.x = snakeStart.x;
                snakePart.y = snakeStart.y;
            } else {
                Tile prevSnakePart = snakebody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        snakeStart.x += velocityX;
        snakeStart.y += velocityY;

        for (int i = 0; i < snakebody.size(); i++) {
            Tile snakepart = snakebody.get(i);
            if (collision(snakeStart, snakepart)) {
                gameover = true;
            }
        }
        if (snakeStart.x < 0 || snakeStart.x >= lenght / tilesize || snakeStart.y < 0 || snakeStart.y >= height / tilesize) {
            gameover = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameover) {
            gameloop.stop();
        }
    }

    public void restartGame() {
        gameover = false;
        snakeStart = new Tile(5, 5);
        snakebody.clear();
        placeFood();
        velocityX = 0;
        velocityY = 0;
        gameloop.start();
        restartButton.setVisible(false); // Hide the restart button
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
