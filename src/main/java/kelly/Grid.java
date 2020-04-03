package kelly;

import java.util.Random;

public class Grid {
    public enum Move {
        UP(true)
        , DOWN(true)
        , LEFT(false)
        , RIGHT(false)
        ;

        boolean isVertical;
        Move(boolean isVertical) {
            this.isVertical = isVertical;
        }

        public boolean isVertical() {
            return isVertical;
        }
    }

    private int[] cellValues;
    private int rows;
    private int columns;
    private boolean gameOver = false;
    private boolean packing = false;
    private final Random random = new Random();

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        reset();
    }

    public void reset() {
        int cells = rows*columns;
        this.cellValues = new int[cells];

        addRandomValue();
        addRandomValue();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isPacking() {
        return packing;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int valueAt(int row, int column) {
        return valueAt(row * columns + column);
    }

    private int valueAt(int idx) {
        return cellValues[idx];
    }

    private void putValue(int idx, int val) {
        cellValues[idx] = val;
    }

    private void putValue(int row, int column, int value) {
        cellValues[row * columns + column] = value;
    }

    private int startingNumber() {
        float numb = random.nextFloat();
        if(numb < 0.5) {
            return 2;
        } else {
            return 4;
        }
    }

    public int addRandomValue() {
        int end = random.nextInt(cellValues.length);
        int curr = (end + 1)% cellValues.length;
        while(curr != end) {
            if(valueAt(curr) == 0) {
                putValue(curr, startingNumber());
                return valueAt(curr);
            }
            curr = (curr + 1)% cellValues.length;
        }
        System.out.println("Game over :(");
        gameOver = true;
        return -1;
    }

    /**
     * Packs the values with the given Move direction to eliminate spaces
     * @param move - the direction of movement
     * @return boolean - true if any cell value has changed
     */
    boolean pack0(Move move) {
        boolean changed = false;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                int v = valueAt(r, c);
                if (v != 0) {
                    continue;
                }
                int nx, nv;
                switch (move) {
                    case UP:
                        nx = r + 1;
                        if (nx < rows && (nv = valueAt(nx, c)) != 0) {
                            putValue(r, c, nv);
                            putValue(nx, c, 0);
                            changed = true;
                        }
                        break;
                    case DOWN:
                        nx = r - 1;
                        if (nx >= 0 && (nv = valueAt(nx, c)) != 0) {
                            putValue(r, c, nv);
                            putValue(nx , c, 0);
                            changed = true;
                        }
                        break;
                    case LEFT:
                        nx = c + 1;
                        if (nx < columns && (nv = valueAt(r, nx)) != 0) {
                            putValue(r, c, nv);
                            putValue(r, nx, 0);
                            changed = true;
                        }
                        break;
                    case RIGHT:
                        nx = c - 1;
                        if (nx >= 0 && (nv = valueAt(r, nx)) != 0) {
                            putValue(r, c, nv);
                            putValue(r, nx, 0);
                            changed = true;
                        }
                        break;
                }
            }
        }
        return changed;
    }

//    boolean packXDetail(int rStart, int rEnd, int rInc, int cStart, int cEnd, int cInc, Move move) {
//
//    }

    boolean packX(Move move) {
        boolean changed = false;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                int v = valueAt(r, c);
                int nx;
                switch (move) {
                    case UP:
                        nx = r + 1;
                        if (nx < rows && v == valueAt(nx, c)) {
                            putValue(r, c, v * 2);
                            putValue(nx, c, 0);
                            changed = true;
                        }
                        break;
                    case DOWN:
                        nx = r - 1;
                        if (nx >= 0 && v == valueAt(nx, c)) {
                            putValue(r, c, v * 2);
                            putValue(nx, c, 0);
                            changed = true;
                        }
                        break;
                    case LEFT:
                        nx = c + 1;
                        if (nx < columns && v == valueAt(r, nx)) {
                            putValue(r, c, v * 2);
                            putValue(r, nx, 0);
                            changed = true;
                        }
                        break;
                    case RIGHT:
                        nx = c - 1;
                        if (nx >= 0 && v == valueAt(r, nx)) {
                            putValue(r, c, v * 2);
                            putValue(r, nx, 0);
                            changed = true;
                        }
                        break;
                }
            }
        }

        return changed;
    }

    /**
     * Returns if anything thing was moved at all
     * @param move - direction of movement
     * @return boolean - true if the grid was changed
     */
    public boolean pack(Move move, long delay) {
        this.packing = true;
        boolean changed = false;

        while(pack0(move)) {
            changed = true;
            AnimationManager.safeSleep(delay);
        }

        if (packX(move)) {
            changed = true;
            AnimationManager.safeSleep(delay);
        }

        while(pack0(move)) {
            changed = true;
            AnimationManager.safeSleep(delay);
        }

        this.packing = false;
        return changed;
    }

}
