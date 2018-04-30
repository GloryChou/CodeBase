package cooperation.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author Kyle
 * @create 2018/4/30 2:19
 */
public abstract class AbstractService implements Service {
    /**
     *  CountDownLatch.countDown()本身是无法区分操作是成功的还是失败的，
     *  所以需要一个标识来区分。
     */
    protected boolean started = false;
    protected final CountDownLatch latch;

    public AbstractService(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    // 留给子类实现的抽象方法，用于实现服务器的启动逻辑
    protected abstract void doStart() throws Exception;

    @Override
    public void start() {
        new ServiceStarter().start();
    }

    @Override
    public void stop() {
        // 默认什么也不做
    }

    public class ServiceStarter extends Thread {
        @Override
        public void run() {
            final String serviceName = AbstractService.this.getClass().getSimpleName();
            System.out.println(String.format("Starting %s", serviceName));
            try {
                doStart();
                started = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
                System.out.println(String.format("Done Starting %s", serviceName));
            }
        }
    }
}
