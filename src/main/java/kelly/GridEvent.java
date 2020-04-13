package kelly;

/**
 * object that holds the event information to be sent to the grid event listener
 */
public class GridEvent {
    private int score;

    /**
     * constructs an event with the given score
     * @param score
     */
    public GridEvent(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
