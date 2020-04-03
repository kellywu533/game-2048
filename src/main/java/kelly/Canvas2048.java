package kelly;

import java.awt.*;

public class Canvas2048 extends Canvas {
    private int scale;
    private Grid grid;

    public Canvas2048(Grid grid, int scale) {
        this.scale = scale;
        this.grid = grid;
    }

    public Dimension getPreferredDimension() {

        return new Dimension(grid.getColumns()*scale, grid.getRows()*scale);
    }

    private void drawTile(Graphics g, int row, int col, int val) {
        if(val == 0) {
            return;
        }
        int x = col * scale + scale/2;
        int y = row * scale + scale/2;
        g.drawString(Integer.toString(val), x, y);
    }

    @Override
    public void paint(Graphics g) {
        int columns = grid.getColumns();
        int rows = grid.getRows();

        setBackground(Color.WHITE);

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
