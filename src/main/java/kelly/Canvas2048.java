package kelly;

import java.awt.*;

public class Canvas2048 extends Canvas implements GridEventListener {
    private int scale;
    private Grid grid;
    private GameTile[] gameTiles;

    public Canvas2048(Grid grid, int scale) {
        this.scale = scale;
        this.grid = grid;
        this.gameTiles = GameTile.gameTiles();
    }

    @Override
    public void update(GridEvent event) {
        this.repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(grid.getColumns()*scale, grid.getRows()*scale);
    }

    private void drawTile(Graphics g, int row, int col, int val) {
        if(val == 0) {
            return;
        }
        int x = col * scale;
        int y = row * scale;
        GameTile t = gameTiles[val];
        g.setColor(t.getColor());
        g.fillRect(x + 2, y + 2, scale - 4, scale - 4);
        g.setColor(Color.BLACK);
        g.drawString(t.getDisplayText() , x + scale/2, y + scale/2);
    }

    @Override
    public void paint(Graphics g) {
        int columns = grid.getColumns();
        int rows = grid.getRows();

//        setBackground(Color.WHITE);

        g.setColor(Color.BLACK);
        int length = columns * scale;
        int width = rows * scale;
        for(int y=0; y<=rows; y++) {
            g.drawLine(0, y*scale, length, y*scale);
        }
        for(int x=0; x<=columns; x++) {
            g.drawLine(x*scale, 0, x*scale, width);
        }

        for(int c=0; c<columns; c++) {
            for(int r=0; r<rows; r++) {
                drawTile(g, r, c, grid.valueAt(r, c));
            }
        }
    }
}
