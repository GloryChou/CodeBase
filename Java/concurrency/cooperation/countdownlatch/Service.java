package cooperation.countdownlatch;

/**
 * @author Kyle
 * @create 2018/4/30 2:13
 */
public interface Service {
    boolean isStarted();

    void start();

    void stop();
}
