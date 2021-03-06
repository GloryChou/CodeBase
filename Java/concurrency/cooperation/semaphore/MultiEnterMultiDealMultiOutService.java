package concurrency.cooperation.semaphore;

import java.util.concurrent.Semaphore;

/**
 * @author Kyle
 * @create 2018/11/20 09:51
 */
public class MultiEnterMultiDealMultiOutService {
    private Semaphore semaphore = new Semaphore(3);

    public void sayHello() {
        try {
            semaphore.acquire();
            System.out.println("ThreadName=" + Thread.currentThread().getName() + "准备");
            System.out.println("Begin hello " + System.currentTimeMillis());
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "打印" + (i + 1));
            }
            System.out.println("End hello " + System.currentTimeMillis());
            semaphore.release();
            System.out.println("ThreadName=" + Thread.currentThread().getName() + "结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
