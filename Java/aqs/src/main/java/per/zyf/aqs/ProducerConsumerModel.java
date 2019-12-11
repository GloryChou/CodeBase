package per.zyf.aqs;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;

/**
 * 自定义锁实现生产-消费模型
 * @author: Kyle
 */
public class ProducerConsumerModel {
    final static NonReentrantExclusiveLock lock = new NonReentrantExclusiveLock();
    final static Condition notFull = lock.newCondition();
    final static Condition notEmpty = lock.newCondition();
    final static Queue<String> queue = new LinkedBlockingDeque<>();
    final static int queueSize = 10;

    public static void main(String[] args) {
        Thread producer = new Thread(() -> {
            // 获取独占锁
            lock.lock();
            try {
                // 队列满时，等待
                while (queue.size() == queueSize) {
                    System.out.println("队列满");
                    notEmpty.await();
                }
                // 添加队列元素
                System.out.println("生产数据");
                queue.add("ele");
                // 唤醒消费线程
                notFull.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 释放锁
                lock.unlock();
            }
        });

        Thread consumer = new Thread(() -> {
            lock.lock();
            try {
                // 队列空时，等待
                while (queue.size() == 0) {
                    System.out.println("队列空");
                    notFull.await();
                }
                // 消费一个元素
                System.out.println("消费数据");
                String ele = queue.poll();
                // 唤醒生产线程
                notEmpty.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        // 启动线程
        producer.start();
        consumer.start();
    }
}
