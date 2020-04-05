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

    private static final int OFFSET = 3;
    private void drawTile(Graphics g, int row, int col, int val) {
        int x = col * scale + OFFSET;
        int y = row * scale + OFFSET;
        GameTile t = gameTiles[val];
        g.drawImage(t.drawTile(scale, OFFSET, getBackground()), x, y,this);
    }

    @Override
    public void paint(Graphics g) {
        int columns = grid.getColumns();
        int rows = grid.getRows();

//        g.setColor(getBackground());
//        g.fillRect(0, 0, getWidth(), getHeight());

        for(int c=0; c<columns; c++) {
            for(int r=0; r<rows; r++) {
                drawTile(g, r, c, grid.valueAt(r, c));
            }
        }
    }
}
