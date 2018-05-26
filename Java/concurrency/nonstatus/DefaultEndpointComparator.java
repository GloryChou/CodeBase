package safe.nonstatus;

import synchrone.Endpoint;

import java.util.Comparator;

/**
 * 无状态对象实例
 *
 * @author Kyle
 * @create 2018/5/26 20:46
 */
public class DefaultEndpointComparator implements Comparator<Endpoint> {

    @Override
    public int compare(Endpoint server1, Endpoint server2) {
        int result = 0;
        boolean isOnline1 = server1.isOnline();
        boolean isOnline2 = server2.isOnline();
        // 优先按照服务器是否在线排序
        if(isOnline1 == isOnline2) {
            // 被比较的两台服务器都在线（或不在线）的情况下进一步比较服务器权重
            result = compareWeight(server1.weight, server2.weight);
        } else {
            // 在线服务器排序靠前
            if(isOnline1) {
                return -1;
            } else {
                return 1;
            }
        }
        return result;
    }

    private int compareWeight(int weight1, int weight2) {
        if(weight1 == weight2) return 0;
        return weight1 < weight1 ? -1 : 1;
    }
}
