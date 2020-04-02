package kelly;

import javax.swing.*;

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
        jf.add(c);
        jf.getContentPane().setPreferredSize(c.getPreferredDimension());
        jf.pack();
        jf.setVisible(true);

        GameKeyListener gkl = new GameKeyListener(grid, c);
        jf.addKeyListener(gkl);
    }
}
