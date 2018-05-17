package safe;

import java.util.HashMap;

/**
 * @author Kyle
 * @create 2018/5/17 23:45
 */
public abstract class XAbstractTask implements Runnable {
    static ThreadLocal<HashMap<String, String>> configHolder = new ThreadLocal<HashMap<String, String>>() {
        protected HashMap<String, String> initialValue() {
            return new HashMap<String, String>();
        }
    };

    // 任务逻辑开始前执行的方法
    protected void preRun() {
        // 清空线程特有对象实例
        configHolder.get().clear();
    }

    // 任务逻辑完毕后执行的方法
    protected void postRun() {
        // TODO:
    }

    // 任务逻辑所在的方法
    protected abstract void doRun();

    @Override
    public final void run(){
        try {
            preRun();
            doRun();
        } finally{
            postRun();
        }
    }
}
