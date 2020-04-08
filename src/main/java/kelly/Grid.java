package kelly;

import java.util.ArrayList;
import java.util.Random;

public class Grid {
    public enum Move {
        UP
        , DOWN
        , LEFT
        , RIGHT
    }

    private int[] cellValues;
    private int rows;
    private int columns;
    private boolean gameOver = false;
    private boolean packing = false;
    private final Random random = new Random();
    private ArrayList<GridEventListener> listeners;

    public Grid(int rows, int columns) {
        this.listeners = new ArrayList<>();
        this.rows = rows;
        this.columns = columns;
        reset();
    }

    public void addListener(GridEventListener listener) {
        this.listeners.add(listener);
    }

    private void publishEvent(int score) {
        GridEvent event = new GridEvent(score);
        for(GridEventListener l : listeners) {
            l.update(event);
        }
    }

    public void reset() {
        int cells = rows*columns;
        this.cellValues = new int[cells];

        addRandomValue();
        addRandomValue();

        gameOver = false;
        publishEvent(-1);
    }

    public void preFill1() {
        cellValues = new int[] {
            12, 13, 14, 15
            , 11, 10, 9, 8
            , 4, 5, 6, 7
            , 3, 2, 1, 1
        };
        gameOver = false;
        publishEvent(-1);
    }

    public synchronized boolean checkGameOver() {
        if (gameOver) {
            return true;
        }

        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < columns; c++) {
                int curr = valueAt(r, c);
                if(curr == 0) {
                    return false;
                }
                int np;

                np = r + 1;
                if (np < rows) {
                    if (valueAt(np, c) == curr) {
                        return false;
                    }
                }

                np = c + 1;
                if (np < columns) {
                    if (valueAt(r, np) == curr) {
                        return false;
                    }
                }
            }
        }

        gameOver = true;
        return true;
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

    void putValue(int idx, int val) {
        cellValues[idx] = val;
    }

    private void putValue(int row, int column, int value) {
        cellValues[row * columns + column] = value;
    }

    int[] getValues() {
        return cellValues;
    }

    private int startingNumber() {
        float numb = random.nextFloat();
        if(numb < 0.85) {
            return 1;
        } else {
            return 2;
        }
    }

    public synchronized void addRandomValue() {
        ArrayList<Integer> values = new ArrayList<>();
        for(int i = 0; i < cellValues.length; i++) {
            if(valueAt(i) == 0) {
                values.add(i);
            }
        }

        if(values.isEmpty()) {
            return;
        }

        int position = values.get(random.nextInt(values.size()));
        putValue(position, startingNumber());
        publishEvent(0);
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

    int packX2(Move move) {
        int score = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                int v;
                int p, nx;
                switch (move) {
                    case UP:
                        v = valueAt(r, c);
                        nx = r + 1;
                        if (nx < rows && v == valueAt(nx, c) && v != 0) {
                            putValue(r, c, ++v);
                            putValue(nx, c, 0);
                            score = score + (int)Math.pow(2,v);
                        }
                        break;
                    case LEFT:
                        v = valueAt(r, c);
                        nx = c + 1;
                        if (nx < columns && v == valueAt(r, nx) && v != 0) {
                            putValue(r, c, ++v);
                            putValue(r, nx, 0);
                            score = score + (int) Math.pow(2,v);
                        }
                        break;
                    case DOWN:
                        p = rows - 1 - r;
                        v = valueAt(p, c);
                        nx = p - 1;
                        if (nx >= 0 && v == valueAt(nx, c) && v != 0) {
                            putValue(p, c, ++v);
                            putValue(nx, c, 0);
                            score = score + (int) Math.pow(2,v);                        }
                        break;
                    case RIGHT:
                        p = columns - 1 - c;
                        v = valueAt(r, p);
                        nx = p - 1;
                        if (nx >= 0 && v == valueAt(r, nx) && v != 0) {
                            putValue(r, p, ++v);
                            putValue(r, nx, 0);
                            score = score + (int) Math.pow(2,v);                        }
                        break;
                }
            }
        }
        return score;
    }

    /**
     * Returns if anything thing was moved at all
     * @param move - direction of movement
     * @return boolean - true if the grid was changed
     */
    public synchronized boolean pack(Move move, long delay) {
        this.packing = true;
        boolean changed = false;

        while(pack0(move)) {
            changed = true;
            publishEvent(0);
            AnimationManager.safeSleep(delay);
        }

        int score;
        if ((score = packX2(move)) > 0) {
            changed = true;
            publishEvent(score);
            AnimationManager.safeSleep(delay);
        }

        while(pack0(move)) {
            changed = true;
            publishEvent(0);
            AnimationManager.safeSleep(delay);
        }

        this.packing = false;
        return changed;
    }

}
