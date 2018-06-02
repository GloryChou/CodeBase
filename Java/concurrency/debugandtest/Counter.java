package debugandtest;

/**
 * 一个非线程安全的计数器
 *
 * @author Kyle
 * @create 2018/6/2 23:06
 */
public class Counter {
    private volatile long count;

    public long value() {
        return count;
    }

    public void increment() {
        // 此处特意不加锁，用以测试代码相应错误
        count++;
    }
}
