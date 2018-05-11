package cooperation.waitandnotify;

import java.util.Random;

/**
 * @author Kyle
 * @create 2018/4/28 0:08
 */
public class TimeoutWaitExample {
    private static final Object lock = new Object();
    private static boolean ready = false;
    protected static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()-> {
            while (true) {
                synchronized (lock) {
                    ready = random.nextInt(100) < 20 ? true : false;
                    if(ready) {
                        lock.notify();
                    }
                }

                // 暂停一段时间
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.setDaemon(true);
        t.start();
        waiter(1000);
    }

    public static void waiter(final long timeOut) throws InterruptedException {
        if(timeOut < 0) {
            throw new IllegalArgumentException();
        }

        long start = System.currentTimeMillis();
        long waitTime;
        long now;
        synchronized (lock) {
            while (!ready) {
                now = System.currentTimeMillis();
                // 计算剩余等待时间
                waitTime = timeOut - (now - start);
                System.out.println(String.format("Remaining time to wait: %sms", waitTime));
                if(waitTime <= 0) {
                    // 等待超时退出
                    break;
                }
                lock.wait(waitTime);
            }

            if(ready) {
                // 执行目标操作
                guardedAction();
            } else {
                System.out.println("Wait timed out, unable to execution target action!");
            }
        }
    }

    private static void guardedAction() {
        System.out.println("Take some action.");
    }
}
