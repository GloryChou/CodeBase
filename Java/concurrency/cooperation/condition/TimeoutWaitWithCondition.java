package cooperation.condition;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Kyle
 * @create 2018/4/28 23:45
 */
public class TimeoutWaitWithCondition {
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    private static boolean ready = false;
    protected static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    ready = random.nextInt(100) < 5 ? true : false;
                    if(ready) {
                        condition.signal();
                    }
                } finally {
                    lock.unlock();
                }

                // 暂停一段时间
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public static void waiter(final long timeOut) throws InterruptedException {
        if (timeOut < 0) {
            throw new IllegalArgumentException();
        }

        // 计算等待的最后期限
        final Date deadline = new Date(System.currentTimeMillis() + timeOut);
        // 是否继续等待
        boolean continueToWait = true;
        lock.lock();
        try {
            while (!ready) {
                System.out.println(String.format("still not ready, continue to wait: %s", continueToWait));

                if (!continueToWait) {
                    System.out.println("Wait timed out, unable to execution target action!");
                    return;
                }
                continueToWait = condition.awaitUntil(deadline);
            }

            // 执行目标行为
            // xxx
        } finally {
            lock.unlock();
        }
    }
}
