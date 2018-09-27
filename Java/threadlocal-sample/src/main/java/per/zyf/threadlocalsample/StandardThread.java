package per.zyf.threadlocalsample;

/**
 * StandardThread
 */
public class StandardThread implements Runnable {
    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

    private String threadName;
    
    private Integer value;

    private void init() {
        Thread.currentThread().setName(this.threadName);
        threadLocal.set(this.value);
    }

    @Override
    public void run() {
        // init threadlocal variable
        this.init();

        for (int i = 0; i < 100; i++) {
            threadLocal.set(threadLocal.get() + 1);
            System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get()); 
        }        
    }

    public String getThreadName() {
        return this.threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}