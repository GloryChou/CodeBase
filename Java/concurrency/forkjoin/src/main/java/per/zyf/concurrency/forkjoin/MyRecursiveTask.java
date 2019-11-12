package per.zyf.concurrency.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * 使用RecursiveTask实现字符串累加
 *
 * @author Kyle
 * @create 2019/11/12 11:06
 */
public class MyRecursiveTask extends RecursiveTask<String> {

    private int beginValue;
    private int endValue;

    public MyRecursiveTask(int beginValue, int endValue) {
        this.beginValue = beginValue;
        this.endValue = endValue;
    }

    @Override
    protected String compute() {
        System.out.println(Thread.currentThread().getName() + "-------");
        if (endValue - beginValue > 2) {
            int midValue = (beginValue + endValue) / 2;
            MyRecursiveTask leftTask = new MyRecursiveTask(beginValue, midValue);
            MyRecursiveTask rightTask = new MyRecursiveTask(midValue + 1, endValue);
            this.invokeAll(leftTask, rightTask);
            return leftTask.join() + rightTask.join();
        } else {
            String returnString = "";
            for (int i = beginValue; i <= endValue; i++) {
                returnString = returnString + (i);
            }
            System.out.println("返回：" +returnString + " " + beginValue + " " + endValue);
            return  returnString;
        }
    }
}