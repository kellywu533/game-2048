package kelly;

import java.util.ArrayList;
import java.util.Random;

/**
 * holds the value of each tile and moves the tiles
 */
public class Grid {
    /**
     * represents the direction of movement
     */
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
    private long moveDelay = 60;

    /**
     * constructs a grid with the given value of rows and columns
     * @param rows in the grid
     * @param columns in the grid
     */
    public Grid(int rows, int columns) {
        this.listeners = new ArrayList<>();
        this.rows = rows;
        this.columns = columns;
        reset();
    }

    public void setMoveDelay(long moveDelay) {
        this.moveDelay = moveDelay;
    }

    /**
     * registers the grid event listener to receive any grid events
     * @param listener grid event listener
     */
    public void addListener(GridEventListener listener) {
        this.listeners.add(listener);
    }

    /**
     * publishes the event to the listeners
     * @param score result of this movement event
     */
    private void publishEvent(int score) {
        GridEvent event = new GridEvent(score);
        for(GridEventListener l : listeners) {
            l.update(event);
        }
    }

    /**
     * resets the board for a new game
     */
    public void reset() {
        int cells = rows*columns;
        this.cellValues = new int[cells];

        addRandomTile();
        addRandomTile();

        gameOver = false;
        //publishes -1 to tell the listener to reset the game
        publishEvent(-1);
    }

    //hack
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

    /**
     * checks if the game is over
     * @return boolean if the game is over
     */
    public synchronized boolean checkGameOver() {
        if (gameOver) {
            return true;
        }

        //loops through the rows and columns to check if adjacent values can be combined
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

    /**
     * returns if the grid is moving
     * @return boolean if movement is happening
     */
    public boolean isPacking() {
        return packing;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    /**
     * returns the value of a tile at the given row and column
     * @param row of the tile
     * @param column of the tile
     * @return the integer value of the tile
     */
    public int valueAt(int row, int column) {
        return valueAt(row * columns + column);
    }

    /**
     * returns the translated tile at the index
     */
    private int valueAt(int idx) {
        return cellValues[idx];
    }

    /**
     * puts a tile value into the grid
     * @param idx index position
     * @param val value of the tile
     */
    void putValue(int idx, int val) {
        cellValues[idx] = val;
    }

    /**
     * puts the desired value of a tile at the row and column provided
     * @param row of the tile
     * @param column of the tile
     * @param value of the tile
     */
    private void putValue(int row, int column, int value) {
        cellValues[row * columns + column] = value;
    }

    /**
     * returns the internal representation of all tiles for test purposes only
     * @return the values of the tiles
     */
    int[] getValues() {
        return cellValues;
    }

    /**
     * returns a random generated number for the new tiles
     * @return randomly generated number, either one or two
     */
    private int startingNumber() {
        float numb = random.nextFloat();
        // percentage of numbers generated
        if(numb < 0.85) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * adds a random new tile to the grid at an open space
     */
    public synchronized void addRandomTile() {
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
        // send a message to redraw the board
        publishEvent(0);
    }

    /**
     * packs the values with the given Move direction to eliminate spaces
     * @param move the direction of movement
     * @return boolean true if any cell value has changed
     */
    boolean pack0(Move move) {
        boolean changed = false;

        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < columns; c++) {
                int nx, vx;

                switch(move) {
                    case UP:
                        if(valueAt(r, c) == 0) {
                            nx = r + 1;
                            if(nx < rows && (vx = valueAt(nx, c)) > 0) {
                                putValue(r, c, vx);
                                putValue(nx, c, 0);
                                changed = true;
                            }
                        }
                        break;
                    case LEFT:
                        if(valueAt(r, c) == 0) {
                            nx = c + 1;
                            if(nx < columns && (vx = valueAt(r, nx)) > 0) {
                                putValue(r, c, vx);
                                putValue(r, nx, 0);
                                changed = true;
                            }
                        }
                        break;
                    case DOWN:
                        if(valueAt(rows - 1 - r, c) == 0) {
                            nx = rows - 2 - r;
                            if(nx >= 0 && (vx = valueAt(nx, c)) > 0) {
                                putValue(rows - 1 - r, c, vx);
                                putValue(nx, c, 0);
                                changed = true;
                            }
                        }
                        break;
                    case RIGHT:
                        if(valueAt(r, columns - 1 - c) == 0) {
                            nx = columns - 2 - c;
                            if(nx >= 0 && (vx = valueAt(r, nx)) > 0) {
                                putValue(r, columns - 1 - c, vx);
                                putValue(r, nx, 0);
                                changed = true;
                            }
                        }
                }
            }
        }
        return changed;
    }

    /**
     * merges the adjacent tiles with the same value
     * @param move
     * @return
     */
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
                        if (v != 0 && nx < rows && v == valueAt(nx, c)) {
                            putValue(r, c, ++v);
                            putValue(nx, c, 0);
                            score = score + (int)Math.pow(2,v);
                        }
                        break;
                    case LEFT:
                        v = valueAt(r, c);
                        nx = c + 1;
                        if (v != 0 && nx < columns && v == valueAt(r, nx)) {
                            putValue(r, c, ++v);
                            putValue(r, nx, 0);
                            score = score + (int) Math.pow(2,v);
                        }
                        break;
                    case DOWN:
                        p = rows - 1 - r;
                        v = valueAt(p, c);
                        nx = p - 1;
                        if (v != 0 && nx >= 0 && v == valueAt(nx, c)) {
                            putValue(p, c, ++v);
                            putValue(nx, c, 0);
                            score = score + (int) Math.pow(2,v);                        }
                        break;
                    case RIGHT:
                        p = columns - 1 - c;
                        v = valueAt(r, p);
                        nx = p - 1;
                        if (v != 0 && nx >= 0 && v == valueAt(r, nx)) {
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
     * moves the tiles and returns if anything thing is moved at all
     * @param move direction of movement
     * @return boolean true if the grid was changed
     */
    public synchronized boolean pack(Move move) {
        this.packing = true;
        boolean changed = false;

        //keeps packing the empty spaces until there are no more gaps
        while(pack0(move)) {
            changed = true;
            publishEvent(0);
            AnimationManager.safeSleep(moveDelay);
        }

        //merges the same, adjacent tiles and publishes the score
        int score;
        if ((score = packX2(move)) > 0) {
            changed = true;
            publishEvent(score);
            AnimationManager.safeSleep(moveDelay);
        }

        //packs again with the new spaces generated after merging has happened
        while(pack0(move)) {
            changed = true;
            publishEvent(0);
            AnimationManager.safeSleep(moveDelay);
        }

        this.packing = false;
        return changed;
    }

    /**
     * checks if the boolean game over is true and plays the game over sound accordingly
     * @return if the game is over
     */
    public boolean verifyGameOver() {
        if (gameOver) {
            System.out.println("Game Over!");
            SoundPlayer.playSound(SoundPlayer.Type.GAME_OVER);
            return true;
        }

        return false;
    }

    private Object lock = new Object();

    /**
     * returns a thread that moves the board according to the move value
     * the thread will pack, play the corresponding sound effect, then sleep before adding a new tile if there
     * is space for a new tile
     * @param move direction of movement
     * @return thread to do the move action
     */
    public Thread createMoveThread(final Grid.Move move) {
        return new Thread(() -> {
            synchronized (lock) {
                SoundPlayer.playSound(SoundPlayer.Type.CLICK);
                if(pack(move)) {
                    SoundPlayer.playSound(SoundPlayer.Type.MOVE);
                    AnimationManager.safeSleep(moveDelay);
                    addRandomTile();
                } else {
                    SoundPlayer.playSound(SoundPlayer.Type.ILLEGAL);
                    System.out.println("Illegal action - " + move);
                    verifyGameOver();
                }
            }
        });
    }
}
