package concurrency.cooperation.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Kyle
 * @date: 2018/11/20 14:04
 */
public class ProducerAndConsumer {
    private volatile Semaphore producerSemaphore = new Semaphore(10);

    private volatile Semaphore consumerSemaphore = new Semaphore(20);

    private volatile ReentrantLock lock = new ReentrantLock();

    private volatile Condition producerCondition = lock.newCondition();

    private volatile Condition consumerCondition = lock.newCondition();

    // Capacity: 4
    private volatile Object[] capacity = new Object[4];

    private boolean isEmpty() {
        boolean isEmpty = true;
        for (int i = 0; i < capacity.length; i++) {
            if (capacity[i] != null) {
                isEmpty = false;
                break;
            }
        }

        return isEmpty;
    }

    private boolean isFull() {
        boolean isFull = true;
        for (int i = 0; i < capacity.length; i++) {
            if (capacity[i] == null) {
                isFull = false;
                break;
            }
        }

        return isFull;
    }

    private void produce() {
        try {
            producerSemaphore.acquire();
            lock.lock();
            while (isFull()) {
                producerCondition.await();
            }
            for (int i = 0; i < capacity.length; i++) {
                if (capacity[i] == null) {
                    capacity[i] = "data";
                    System.out.println(Thread.currentThread().getName() + " produce " + capacity[i]);
                    break;
                }
            }
            consumerCondition.notifyAll();
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            producerSemaphore.release();
        }
    }

    private void consume() {
        try {
            consumerSemaphore.acquire();
            lock.lock();
            while (isEmpty()) {
                consumerCondition.await();
            }
            for (int i = 0; i < capacity.length; i++) {
                if (capacity[i] != null) {
                    System.out.println(Thread.currentThread().getName() + " consume " + capacity[i]);
                    capacity[i] = null;
                    break;
                }
            }
            consumerCondition.notifyAll();
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            consumerSemaphore.release();
        }

    }
}
