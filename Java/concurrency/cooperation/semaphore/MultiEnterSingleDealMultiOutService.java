package concurrency.cooperation.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Kyle
 * @date: 2018/11/20 10:24
 */
public class MultiEnterSingleDealMultiOutService {
    private Semaphore semaphore = new Semaphore(3);

    private ReentrantLock lock = new ReentrantLock();

    public void sayHello() {
        try {
            semaphore.acquire();
            System.out.println("ThreadName=" + Thread.currentThread().getName() + "准备");
            lock.lock();
            System.out.println("Begin hello " + System.currentTimeMillis());
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "打印" + (i + 1));
            }
            System.out.println("End hello " + System.currentTimeMillis());
            lock.unlock();
            semaphore.release();
            System.out.println("ThreadName=" + Thread.currentThread().getName() + "结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
