package kelly;

/**
 * interface that should be implemented by classes that wish to recieve grid events
 */
public interface GridEventListener {
    void update(GridEvent event);
}
