package cooperation.blockingqueue;

/**
 * @author glorychou
 * @create 2018/5/11 22:25
 */
public interface Channel<P> {
    /**
     * store a product
     * @param product
     * @throws InterruptedException
     */
    void put(P product) throws InterruptedException;

    /**
     * take a product
     * @return
     * @throws InterruptedException
     */
    P take() throws InterruptedException;
}
