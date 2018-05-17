/**
 * 基于双重检查锁定的错误单例模式实现
 *
 * @author Kyle
 * @create 2018/3/18 13:49
 */
public class DCLSingleton {
    private static volatile DCLSingleton instance = null;

    // 省略实例变量声明
    /**
     * 私有构造器使其他类无法直接通过new创建该类的实例
     */
    private DCLSingleton() {
        // 什么也不做
    }

    /**
     * 创建并返回该类的唯一实例<br />
     * 即只有该方法被调用时该类的唯一实例才会被创建<br />
     * 操作3可以分解为三条指令：① 分配对象所需的存储空间（objRef = allocate(class)）
     *                          ② 初始化引用对象 (invokeConstructor(objRef))
     *                          ③ 将对象引用写入instance （instance = objRef）
     * 其中②③可以重排序，当执行顺序为①③②，某个线程正执行完步骤③，
     * 而又有另外一个线程执行操作1的时候发现instance不为null,
     * 但是这个instance所引用的实例可能还未初始化完毕，这就将导致程序错误。
     * 所以instance必须加上volatile修饰，以禁止②③步骤的重排序。
     * @return
     */
    public static DCLSingleton getInstance() {
        if(null == instance) { // 操作1
            synchronized (DCLSingleton.class) {
                if(null == instance) { // 操作2
                    instance = new DCLSingleton(); // 操作3
                }
            }
        }
        return instance;
    }
}