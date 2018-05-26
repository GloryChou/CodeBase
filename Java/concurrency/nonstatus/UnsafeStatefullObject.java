package safe.nonstatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kyle
 * @create 2018/5/26 21:38
 */
public class UnsafeStatefullObject {
    static Map<String, String> cache = new HashMap<>();

    public String doSomething(String s, int len) {
        String result = cache.get(s);
        if(null == result) {
            result = md5sum(result, len);
            cache.put(s, result);
        }

        return result;
    }

    public String md5sum(String s, int len) {
        // TODO:生成md5摘要
        return s;
    }
}
