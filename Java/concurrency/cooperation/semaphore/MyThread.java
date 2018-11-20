package concurrency.cooperation.semaphore;

/**
 * @author: Kyle
 * @date: 2018/11/20 10:03
 */
public class MyThread extends Thread {
    private MultiEnterMultiDealMultiOutService multiEnterMultiDealMultiOutService;

    public MyThread(MultiEnterMultiDealMultiOutService multiEnterMultiDealMultiOutService) {
        this.multiEnterMultiDealMultiOutService = multiEnterMultiDealMultiOutService;
    }

    @Override
    public void run() {
        multiEnterMultiDealMultiOutService.sayHello();
    }
}
