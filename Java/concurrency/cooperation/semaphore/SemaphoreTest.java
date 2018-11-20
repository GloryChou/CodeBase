package concurrency.cooperation.semaphore;

import org.junit.Test;

import java.io.IOException;

/**
 * @author: Kyle
 * @date: 2018/11/20 10:05
 */
public class SemaphoreTest {

    @Test
    public void testMoreToOneService() {
        MultiEnterMultiDealMultiOutService multiEnterMultiDealMultiOutService = new MultiEnterMultiDealMultiOutService();
        MyThread[] threads = new MyThread[12];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MyThread(multiEnterMultiDealMultiOutService);
            threads[i].start();
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
