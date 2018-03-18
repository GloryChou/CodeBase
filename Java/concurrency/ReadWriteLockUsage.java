import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁的使用方法
 *
 * @author Kyle
 * @create 2018/3/17 17:10
 */
public class ReadWriteLockUsage {
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    /**
     * 读线程执行该方法
     */
    public void reader() {
        // 申请读锁
        readLock.lock();
        try {
            // 读取共享变量
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 写线程执行该方法
     */
    public void writer() {
        // 申请写锁
        writeLock.lock();
        try {
            // 写公共变量
        } finally {
            writeLock.unlock();
        }
    }
}
