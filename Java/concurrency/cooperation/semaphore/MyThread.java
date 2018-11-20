package concurrency.cooperation.semaphore;

/**
 * @Author: Finance Group
 * @Date: 2018/11/20 10:03
 */
public class MyThread extends Thread {
    private MoreToOneService moreToOneService;

    public MyThread(MoreToOneService moreToOneService) {
        this.moreToOneService = moreToOneService;
    }

    @Override
    public void run() {
        moreToOneService.sayHello();
    }
}
