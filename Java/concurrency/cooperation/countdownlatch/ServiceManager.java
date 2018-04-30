package cooperation.countdownlatch;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * @author Kyle
 * @create 2018/4/30 2:08
 */
public class ServiceManager {
    static volatile CountDownLatch latch;
    static Set<Service> services;

    public static void startServices() {
        services = getServices();
        for(Service service :services) {
            service.start();
        }
    }

    public static boolean checkServiceStatus() {
        boolean allIsOK = true;
        // 等待服务启动结束
        try {
            latch.await();
        } catch (InterruptedException e) {
            return false;
        }

        for(Service service : services) {
            if(!service.isStarted()) {
                allIsOK = false;
                break;
            }
        }

        return allIsOK;
    }

    static Set<Service> getServices() {
        // 模拟实际代码
        latch = new CountDownLatch(3);
        HashSet<Service> services = new HashSet<>();
        services.add(new SampleService(latch));
        return services;
    }
}
