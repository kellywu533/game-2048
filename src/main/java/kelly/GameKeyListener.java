package kelly;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {
    private Grid grid;
    private Canvas2048 canvas;

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

        if (move != null) {
            if(grid.doMove(move)) {
                SoundPlayer.playSound(SoundPlayer.Type.MOVE);
                if(grid.addRandomValue() < 0) {
                    System.out.println("Game Over!");
                } else {
                    System.out.println("Moving " + move);
                    canvas.repaint();
                }
            } else {
                SoundPlayer.playSound(SoundPlayer.Type.ILLEGAL);
                System.out.println("Illegal action - " + move);
            }
        }
    }
}
