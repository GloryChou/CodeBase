package safe.nonstatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 多个线程访问本身不包含状态的对象也可能存在共享状态的示例
 *
 * @author Kyle
 * @create 2018/5/26 21:11
 */
public class BrokenStatelessObject {
    public String doSomething(String s) {
        UnsafeSingleton us = UnsafeSingleton.INSTANCE;
        int i = us.doSomething(s);
        UnsafeStatefullObject sfo = new UnsafeStatefullObject();
        String str = sfo.doSomething(s, i);
        return str;
    }

    public String doSomethin1(String s) {
        UnsafeSingleton us = UnsafeSingleton.INSTANCE;
        UnsafeStatefullObject sfo = new UnsafeStatefullObject();
        String str;
        synchronized (this) {
            str = sfo.doSomething(s, us.doSomething(s));
        }
        return str;
    }

    enum UnsafeSingleton {
        INSTANCE;

        public int state1;

        public int doSomething(String s) {
            // TODO:

            // 访问state1
            return 0;
        }
    }
}
