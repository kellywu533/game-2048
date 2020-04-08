package kelly;

import java.awt.*;

public class Canvas2048 extends Canvas implements GridEventListener {
    private static final String FONT_NAME = "Arial";

    private int scale;
    private Grid grid;
    private GameTile[] gameTiles;

    public Canvas2048(Grid grid, int scale) {
        this.scale = scale;
        this.grid = grid;
        this.gameTiles = GameTile.gameTiles();
    }

    private boolean eraseBackground = false;

    @Override
    public void update(GridEvent event) {
        if(event.getScore() < 0) {
            eraseBackground = true;
        }
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
        g.drawImage(t.drawTile(scale, OFFSET, getBackground(), FONT_NAME), x, y,this);
    }

    private void drawGameOver(Graphics g) {
        Font font = new Font(FONT_NAME, Font.BOLD, 40);
        g.setFont(font);
        String text = "GAME OVER";
        g.setColor(Color.RED);
        FontMetrics fm = g.getFontMetrics(font);
        int fw = fm.stringWidth(text);
        int fh = fm.getHeight();

        g.drawString(text, (getWidth() - fw)/2, getHeight()/2 - fh/3);

        font = new Font(FONT_NAME, Font.PLAIN, 20);
        g.setFont(font);
        text = "Press 'r' to restart";
        fm = g.getFontMetrics(font);
        fw = fm.stringWidth(text);
        fh = fm.getHeight();
        g.drawString(text, (getWidth() - fw)/2, getHeight()/2 + fh/3);
    }

    @Override
    public void update(Graphics g) {
        if(eraseBackground) {
            eraseBackground = false;
            super.update(g);
        } else {
            paint(g);
        }
    }

    @Override
    public void paint(Graphics g) {
        int columns = grid.getColumns();
        int rows = grid.getRows();

        for(int c=0; c<columns; c++) {
            for(int r=0; r<rows; r++) {
                drawTile(g, r, c, grid.valueAt(r, c));
            }
        }

        if(grid.checkGameOver()) {
            drawGameOver(g);
        }
    }
}
