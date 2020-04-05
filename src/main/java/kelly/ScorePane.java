package kelly;

import javax.swing.*;
import java.awt.*;

public class ScorePane extends JPanel implements GridEventListener {
    private int totalScore = 0;
    private JLabel scoreLabel;
    private JLabel scoreText;

    public ScorePane() {
        scoreLabel = new JLabel();
        scoreText = new JLabel();
        this.setLayout(new FlowLayout());

        this.add(scoreLabel);
        this.add(scoreText);

        updateLabels();
    }

    private void updateLabels() {
        scoreLabel.setText("Score:");
        scoreText.setText(Integer.toString(totalScore));
        this.repaint();
    }

    @Override
    public void update(GridEvent event) {
        totalScore += event.getScore();
        updateLabels();
    }
}
