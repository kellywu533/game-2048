package kelly;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * triggers the board movement with each key input
 */
public class GameKeyListener implements KeyListener {
    private Grid grid;

    public GameKeyListener(Grid grid) {
        this.grid = grid;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * overrides the key released method to move the board according to the key released
     * @param e key event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        //if the grid is mid-movement, the event is ignored
        if (grid.isPacking()) {
            return;
        }

        Grid.Move move = null;
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP :
                move = Grid.Move.UP;
                break;
            case KeyEvent.VK_RIGHT :
                move = Grid.Move.RIGHT;
                break;
            case KeyEvent.VK_DOWN :
                move = Grid.Move.DOWN;
                break;
            case KeyEvent. VK_LEFT :
                move = Grid.Move.LEFT;
                break;
            case KeyEvent.VK_R:
                System.out.println("Restarting Game");
                grid.reset();
                return;
            case KeyEvent.VK_T:
                System.out.println("Test Tiles");
                grid.preFill1();
                break;
        }

        //if the game is over, the key input is ignored
        if (grid.verifyGameOver()) {
            return;
        }

        //if the move can be done, a thread is created to move the board
        if (move != null) {
            Thread t = grid.createMoveThread(move);
            t.start();
        }
    }
}
