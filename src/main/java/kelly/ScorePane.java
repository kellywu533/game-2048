package kelly;

import javax.swing.*;
import java.awt.*;

/**
 * the score that is to be drawn
 */
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

    /**
     * recieves the message from the grid and updates the score
     * @param event
     */
    @Override
    public void update(GridEvent event) {
        //if the score is negative, the score is reset
        if(event.getScore() < 0) {
            totalScore = 0;
        } else {
            totalScore += event.getScore();
        }
        updateLabels();
    }
}
