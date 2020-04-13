package kelly;

import javax.swing.*;
import java.awt.*;

/**
 * main class to set up the board and display it on the screen
 */
public class App 
{
    private static final int HEIGHT = 4;
    private static final int WIDTH = 4;
    private static final int SCALE = 100;

    public static void main( String[] args )
    {
        JFrame jf = new JFrame("2048");
        Grid grid = new Grid(HEIGHT, WIDTH);

        Canvas2048 c = new Canvas2048(grid, SCALE);
        //registered with the grid to receive messages about movements to redraw the board
        grid.addListener(c);

        ScorePane sf = new ScorePane();
        //registered with the grid to receive messages to update the score
        grid.addListener(sf);

        GameKeyListener gkl = new GameKeyListener(grid);
        //added the game key listener to the canvas and jframe to manage the keyboard input
        c.addKeyListener(gkl);
        jf.addKeyListener(gkl);

        jf.getContentPane().setLayout(new BorderLayout());
        jf.add(c, BorderLayout.CENTER);
        jf.add(sf, BorderLayout.NORTH);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jf.pack();
        jf.setVisible(true);
    }
}
