/**
 * 基于静态内部类的单例模式实现
 *
 * @author Kyle
 * @create 2018/3/18 14:39
 */
public class StaticHolderSingleton {
    // 私有构造器
    private StaticHolderSingleton() {

    }

    private static class InstanceHolder {
        // 保存外部类的唯一实例
        final static StaticHolderSingleton INSTANCE = new StaticHolderSingleton();
    }

    public static StaticHolderSingleton getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
