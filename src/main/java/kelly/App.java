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

        ScoreField sf = new ScoreField();
        grid.addListener(sf);

        GameKeyListener gkl = new GameKeyListener(grid, c);
        c.addKeyListener(gkl);

//        jf.addKeyListener(gkl);

//        JPanel jp = new JPanel();
//        jf.add(jp);

//        BorderLayout layout = new BorderLayout();
//        layout.addLayoutComponent(c,BorderLayout.CENTER);
//        layout.addLayoutComponent(sf, BorderLayout.NORTH);

//        jp.setLayout(layout);

//        jf.getContentPane().setLayout(layout);
        jf.add(c);
//        jf.getContentPane().setPreferredSize(c.getPreferredDimension());

        jf.pack();
        jf.setVisible(true);
    }
}
