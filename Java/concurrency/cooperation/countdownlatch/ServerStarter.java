package cooperation.countdownlatch;


/**
 * @author Kyle
 * @create 2018/4/30 2:07
 */
public class ServerStarter {

    public static void main(String[] args) {
        // 启动所有服务
        ServiceManager.startServices();

        // 检测服务启动状态
        boolean allIsOK;
        allIsOK = ServiceManager.checkServiceStatus();

        if(allIsOK) {
            System.out.println("All Services were sucessfully started!");
        } else {
            System.err.println("Some service(s) failed to start, exiting JVM...");
            System.exit(1);
        }
    }
}
