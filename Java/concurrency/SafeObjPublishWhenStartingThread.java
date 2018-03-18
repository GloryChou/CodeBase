import java.util.Map;

/**
 * 在启动工作者线程时实现对象安全发布范例
 *
 * @author Kyle
 * @create 2018/3/18 16:37
 */
public class SafeObjPublishWhenStartingThread {
    private final Map<String,String> objectState;

    private SafeObjPublishWhenStartingThread(Map<String,String> objectState) {
        this.objectState = objectState;
        // 不在构造器中启动工作者线程，以免this逸出
    }

    private void init() {
        // 创建并启动工作者线程
        new Thread() {
            @Override
            public void run() {
                // 访问外层类实例的状态变量
                String value = objectState.get("someKey");
                // 其他代码
            }
        }.start();
    }

    // 工厂方法
    public static SafeObjPublishWhenStartingThread newInstance(Map<String,String> objectState) {
        SafeObjPublishWhenStartingThread instance = new SafeObjPublishWhenStartingThread(objectState);
        instance.init();
        return instance;
    }
}
