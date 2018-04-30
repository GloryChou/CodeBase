package cooperation.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author Kyle
 * @create 2018/4/30 3:08
 */
public class CountDownLatchExample {
    private static final CountDownLatch latch = new CountDownLatch(4);
    private static int data;

    public static void main(String[] args) throws InterruptedException {
        Thread workerThread = new Thread(() -> {
            for(int i = 0; i < 10; i++) {
                data = i;
                latch.countDown();
                // 暂停一段时间
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        });
        workerThread.start();
        latch.await();
        System.out.println(String.format("It's done. data=%d", data));
    }
}
