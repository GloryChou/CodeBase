package cooperation;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Kyle
 * @create 2018/4/28 22:59
 */
public class ConditionUsage {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void aGuaredMethod() throws InterruptedException {
        lock.lock();
        try {
            while (true) {
                condition.await();
            }

            // 执行目标动作
            // xxx
        } finally {
            lock.unlock();
        }
    }

    public void anNotificationMethod() throws InterruptedException {
        lock.lock();
        try {
            // 更新共享变量
            // xxx

            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}
