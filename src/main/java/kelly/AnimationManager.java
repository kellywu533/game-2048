package kelly;

public class AnimationManager {
    public static void safeSleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            // ignore
        }
    }

}
