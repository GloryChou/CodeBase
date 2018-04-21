import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用显示锁实现循环递增序列号生成器
 *
 * @author Kyle
 * @create 2018/3/17 15:56
 */
public class LockbasedCircularSeqGenerator {
    private short sequence = -1;
    private final Lock lock = new ReentrantLock();

    public short nextSequence() {
        lock.lock();
        try {
            if(sequence >= 999) {
                sequence = -1;
            } else {
                sequence++;
            }
            return sequence;
        } finally {
            lock.unlock();
        }
    }
}
