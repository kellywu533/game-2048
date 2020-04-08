package kelly;

import javax.swing.*;
import java.awt.*;

/**
 * Hello world!
 *
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
        grid.addListener(c);

        ScorePane sf = new ScorePane();
        grid.addListener(sf);

        GameKeyListener gkl = new GameKeyListener(grid);
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
