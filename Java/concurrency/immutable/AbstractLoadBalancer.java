package safe.immutable;

import java.util.Random;
import java.util.logging.Logger;

/**
 * @author Kyle
 * @create 2018/5/29 23:42
 */
public class AbstractLoadBalancer implements LoadBalancer {
    private final static Logger LOGGER = Logger.getAnonymousLogger();
    // 使用volatile变量替代锁（有条件替代）
    protected volatile Candidate candidate;
    protected final Random random;
    // 心跳线程
    private Thread heartbeatThread;

    public void updateCandidate(final Candidate candidate) {
        if(null == candidate || 0 == candidate.getEndpointCount()) {
            throw  new IllegalArgumentException("Invalid candidate " + candidate);
        }
        // 更新volatile变量candidate
        this.candidate = candidate;
    }
}
