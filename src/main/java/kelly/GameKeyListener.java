package kelly;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {
    private Grid grid;
    private Canvas2048 canvas;
    private static final long MOVE_DELAY = 20;

    public GameKeyListener(Grid grid, Canvas2048 canvas) {
        this.grid = grid;
        this.canvas = canvas;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
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
                canvas.repaint();
                break;
        }

        if (verifyGameOver()) {
            return;
        }

        if (move != null) {
            Thread t = doMove(move);
            t.start();
        }
    }

    private Thread doMove(final Grid.Move move) {
        GameKeyListener self = this;
        return new Thread(() -> {
            synchronized (self) {
                SoundPlayer.playSound(SoundPlayer.Type.CLICK);
                if(grid.pack(move, MOVE_DELAY)) {
                    SoundPlayer.playSound(SoundPlayer.Type.MOVE);
                    AnimationManager.safeSleep(MOVE_DELAY);
                    grid.addRandomValue();
                } else {
                    SoundPlayer.playSound(SoundPlayer.Type.ILLEGAL);
                    System.out.println("Illegal action - " + move);
                    verifyGameOver();
                }
            }
        });
    }

    private boolean verifyGameOver() {
        if (grid.checkGameOver()) {
            System.out.println("Game Over!");
            SoundPlayer.playSound(SoundPlayer.Type.GAME_OVER);
            return true;
        }

        return false;

    }
}
