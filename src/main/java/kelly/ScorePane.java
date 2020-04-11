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
        Font font = new Font("Arial", Font.PLAIN, 24);
        scoreText.setFont(font);
        scoreLabel.setFont(font);
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
        if(event.getScore() < 0) {
            totalScore = 0;
        } else {
            totalScore += event.getScore();
        }
        updateLabels();
    }
}
