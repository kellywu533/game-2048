package kelly;

import java.util.Arrays;
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
    private static final Random random = new Random();

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

    public int[] consolidate(Move move) {
        int length = move.isVertical ? columns : rows;
        int[] result = new int[cellValues.length];
        for (int i = 0; i<length; i++) {
            int[] data = move.isVertical ?
                convertColumn(i, Move.UP.equals(move))
                : convertRow(i, Move.LEFT.equals(move))
                ;
            int[] packed = packX(0, data);
            if (move.isVertical()) {
                putBackColumn(i, packed, Move.UP.equals(move));
            } else {
                putBackRow(i, packed, Move.LEFT.equals(move));
            }
        }

        for(int i = 0; i< this.cellValues.length; i++) {
            result[i] = valueAt(i);
        }

        return result;
    }

    private void putBackColumn(int column, int[] data, boolean shiftUp) {
        for(int idx = 0; idx < data.length; idx++) {
            int tileRow = shiftUp ? idx : rows - idx - 1;
            putValue(tileRow, column, data[idx]);
        }
    }

    private void putBackRow(int row, int[] data, boolean shiftLeft) {
        for(int idx = 0; idx < data.length; idx++) {
            int tileColumn = shiftLeft ? idx : columns - idx - 1;
            putValue(row, tileColumn, data[idx]);
        }
    }

    private int[] convertRow(int row, boolean shiftLeft) {
        int[] result = new int[columns];
        for (int idx = 0; idx < columns; idx++) {
            int tileCol = shiftLeft ? idx : columns - idx - 1;
            result[idx] = valueAt(row, tileCol);
        }
        return result;
    }

    private int[] convertColumn(int col, boolean shiftUp) {
        int[] result = new int[rows];
        for (int idx = 0; idx < rows; idx++) {
            int tileRow = shiftUp ? idx : rows - idx - 1;
            result[idx] = valueAt(tileRow, col);
        }
        return result;
    }

    static int[] pack0(int start, int[] data) {
        for(int i=start; i<data.length - 1; i++) {
            if (data[i] == 0) {
                pack0(i+1, data);
                data[i] = data[i+1];
                data[i+1] = 0;
            }
        }
        return data;
    }

    static int[] packX(int start, int data[]) {
        for(int i=start; i<data.length - 1; i++) {
            pack0(i, data);
            if (data[i] == data[i+1]) {
                data[i] = data[i+1] * 2;
                data[i+1] = 0;
            }
        }
        return data;
    }

    public int[] cloneValues() {
        int[] result = new int[cellValues.length];
        for(int i = 0; i< cellValues.length; i++) {
            result[i] = valueAt(i);
        }
        return result;
    }

    /**
     * Returns if anything thing was moved at all
     * @param move
     * @return
     */
    public boolean doMove(Move move) {
        int[] original = cloneValues();
        int[] shifted = consolidate(move);
        return !Arrays.equals(original, shifted);
    }
}
