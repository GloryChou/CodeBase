import per.zyf.concurrency.forkjoin.MyRecursiveTask;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinTest {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<String> forkJoinTask = forkJoinPool.submit(new MyRecursiveTask(1, 20));
        System.out.println(forkJoinTask.join());
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
