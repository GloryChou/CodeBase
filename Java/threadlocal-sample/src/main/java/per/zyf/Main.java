package per.zyf;

import per.zyf.threadlocalsample.StandardThread;

public class Main
{
    public static void main( String[] args )
    {
        StandardThread standardThread1 = new StandardThread();
        standardThread1.setThreadName("Thread 1");
        standardThread1.setValue(0);

        StandardThread standardThread2 = new StandardThread();
        standardThread2.setThreadName("Thread 2");
        standardThread2.setValue(1000);

        new Thread(standardThread1).start();
        new Thread(standardThread2).start();
    }
}