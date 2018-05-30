package safe.nonstatus;

import synchrone.Endpoint;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 对服务节点进行排序
 *
 * @author Kyle
 * @create 2018/5/26 21:02
 */
public class EndpointView {
    static final Comparator<Endpoint> DEFAULT_COMPARATOR;
    static {
        DEFAULT_COMPARATOR = new DefaultEndpointComparator();
    }

    public Endpoint[] retrieveServerList(Comparator<Endpoint> comparator) {
        Endpoint[] serverList = doRetrieveServerList();
        Arrays.sort(serverList, comparator);
        return serverList;
    }

    public Endpoint[] retrieveServerList() {
        return retrieveServerList(DEFAULT_COMPARATOR);
    }

    private Endpoint[] doRetrieveServerList() {
        // TODO:
        return null;
    }

    public static void main(String[] args) {
        EndpointView endpointView = new EndpointView();
        Endpoint[] serverList = endpointView.retrieveServerList();
        System.out.println(Arrays.toString(serverList));
    }
}
