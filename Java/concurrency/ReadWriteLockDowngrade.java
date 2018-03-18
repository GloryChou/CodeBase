import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁的降级示例
 *
 * @author Kyle
 * @create 2018/3/17 17:19
 */
public class ReadWriteLockDowngrade {
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public void operationWithLockDowngrade() {
        boolean readLockAcquired = false;
        // 申请写锁
        writeLock.lock();
        try {
            // 写共享数据
            // 在持有写锁的情况下申请读锁
            readLock.lock();
            readLockAcquired = true;
        } finally {
            writeLock.unlock();
        }

        if(readLockAcquired) {
            try {
                // 读取共享数据并执行其他操作
            } finally {
                readLock.unlock();
            }
        }
    }
}
