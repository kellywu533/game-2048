package kelly;

import java.awt.*;

/**
 * draw the game board
 */
public class Canvas2048 extends Canvas implements GridEventListener {
    private static final String FONT_NAME = "Arial";

    private int scale;
    private Grid grid;
    private GameTile[] gameTiles;

    /**
     * constructs the canvas for the game board
     * @param grid allotted space for each game tile
     * @param scale the size of each tile
     */
    public Canvas2048(Grid grid, int scale) {
        this.scale = scale;
        this.grid = grid;
        this.gameTiles = GameTile.gameTiles();
    }

    //keeps track whether or not the background needs to be erased
    private boolean eraseBackground = false;

    /**
     * recieves the game event from the grid to repaint and check if the background needs to be erased
     * if the score of the event is less than zero, the board is reset
     * @param event from the grid
     */
    @Override
    public void update(GridEvent event) {
        if(event.getScore() < 0) {
            eraseBackground = true;
        }
        // multiple repaint requests are ignored -> force update
//        this.repaint();
        this.update(this.getGraphics());
    }

    /**
     * overrides the get preferred size method to size the board properly
     * @return dimension of the board
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(grid.getColumns()*scale, grid.getRows()*scale);
    }

    private static final int OFFSET = 3;

    /**
     * draws the individual tiles for each given row and column
     * @param g graphics
     * @param row of the tile in the grid
     * @param col of the tile in the grid
     */
    private void drawTile(Graphics g, int row, int col) {
        int val = grid.valueAt(row, col);
        int x = col * scale + OFFSET;
        int y = row * scale + OFFSET;
        GameTile t = gameTiles[val];
        g.drawImage(t.drawTile(scale, OFFSET, FONT_NAME), x, y,this);
    }

    /**
     * draws 'game over' and 'press 'r' to reset' on the screen
     * @param g graphics
     */
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

    /**
     * overrides the parent update to conditionally erase the background
     * @param g graphics
     */
    @Override
    public void update(Graphics g) {
        if(eraseBackground) {
            eraseBackground = false;
            super.update(g);
        } else {
            paint(g);
        }
    }

    /**
     * overrides the main paint method to draw the board
     * @param g graphics
     */
    @Override
    public void paint(Graphics g) {
        int columns = grid.getColumns();
        int rows = grid.getRows();

        //loops through the rows and columns to draw each tile
        for(int c=0; c<columns; c++) {
            for(int r=0; r<rows; r++) {
                drawTile(g, r, c);
            }
        }

        //if the game is over, draw game over
        if(grid.checkGameOver()) {
            drawGameOver(g);
        }
    }
}
