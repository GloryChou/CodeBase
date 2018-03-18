/**
 * 加锁单例模式的简单实现
 *
 * @author Kyle
 * @create 2018/3/18 13:33
 */
public class SimpleMultithreadedSingleton {
    private static SimpleMultithreadedSingleton instance = null;

    // 省略实例变量声明
    /**
     * 私有构造器使其他类无法直接通过new创建该类的实例
     */
    private SimpleMultithreadedSingleton() {
        // 什么也不做
    }

    /**
     * 创建并返回该类的唯一实例<br />
     * 即只有该方法被调用时该类的唯一实例才会被创建
     * @return
     */
    public static SimpleMultithreadedSingleton getInstance() {
        synchronized (SimpleMultithreadedSingleton.class) {
            if(null == instance) {
                instance = new SimpleMultithreadedSingleton();
            }
        }
        return instance;
    }
}
