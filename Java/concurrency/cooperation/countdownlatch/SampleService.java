package cooperation.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author Kyle
 * @create 2018/4/30 2:50
 */
public class SampleService extends AbstractService {

    public SampleService(CountDownLatch latch) {
        super(latch);
    }

    @Override
    protected void doStart() throws Exception {

    }
}
