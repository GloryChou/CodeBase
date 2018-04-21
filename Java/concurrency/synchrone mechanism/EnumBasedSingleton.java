/**
 * 基于枚举类型的单例模式
 *
 * @author Kyle
 * @create 2018/3/18 14:48
 */
public class EnumBasedSingleton {
    public static enum Singleton {
        INSTANCE;

        Singleton() {

        }

        public void someService() {
            // 服务代码
        }
    }

    public static void main(String[] args) {
        Thread t = new Thread() {
            public void run() {
                Singleton.INSTANCE.someService();
            }
        };
        t.start();
    }
}