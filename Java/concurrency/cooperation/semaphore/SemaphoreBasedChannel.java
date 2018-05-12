package cooperation.semaphore;

import cooperation.blockingqueue.Channel;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Semaphore;

/**
 * @author Kyle
 * @create 2018/5/11 22:24
 */
public class SemaphoreBasedChannel<P> implements Channel<P> {

    private final BlockingDeque<P> queue;
    private final Semaphore semaphore;

    /**
     *
     * @param queue
     * @param flowLimit traffic limit
     */
    public SemaphoreBasedChannel(BlockingDeque<P> queue, int flowLimit) {
        this(queue, flowLimit, false);
    }

    public SemaphoreBasedChannel(BlockingDeque<P> queue, int flowLimit, boolean isFair) {
        this.queue = queue;
        this.semaphore = new Semaphore(flowLimit, isFair);
    }

    @Override
    public void put(P product) throws InterruptedException {
        // get a signal
        semaphore.acquire();
        try {
            queue.put(product);
        } finally {
            // return a signal
            semaphore.release();
        }
    }

    @Override
    public P take() throws InterruptedException {
        return queue.take();
    }
}
