package kelly;

import javax.swing.*;

public class ScoreField extends JLabel implements GridEventListener {
    private int totalScore = 0;

    @Override
    public void update(GridEvent event) {
        totalScore += event.getScore();
        String txt = "score: " + totalScore;
        System.out.println(txt);
        this.setText(txt);
        this.repaint();
    }
}
