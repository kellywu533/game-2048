package kelly;

public class AnimationManager {
    /**
     * sleep method without any interrupted exceptions
     * @param delay in milliseconds
     */
    public static void safeSleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            // ignore
        }
    }

}
