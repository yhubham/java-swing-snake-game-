package snake;

import java.util.Scanner;
import javax.swing.*;
public class main {
    public static void main(String[] args) {
        int lenght =600;
        int height = 600;

        JFrame frame = new JFrame("Snake Game");
        frame.setVisible(true);
        frame.setSize(lenght, height);
        frame.setLocationRelativeTo(null);//for opening in the center of screen
        frame.setResizable(false);// we can not resize the frame 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       
       Game game = new Game(lenght, height);
       frame.add(game);
    
        game.requestFocus();
    }
}