package debugandtest;

import org.openjdk.jcstress.annotations.*;

/**
 * 计数器Counter的JCStress测试用例
 *
 * @author Kyle
 * @create 2018/6/2 23:08
 */
@JCStressTest
@Description("测试Counter的线程安全性")
@Outcome(id = "[2]", expect = Expect.ACCEPTABLE, desc = "OK")
@Outcome(id = "[1]", expect = Expect.FORBIDDEN, desc = "丢失更新或者读脏数据")
public class CounterTest {
    @State
    public static class StateObject {
        final Counter counter = new Counter();
    }

    @Actor
    public void actor1(StateObject sh) {
        sh.counter.increment();
    }

    @Actor
    public void actor2(StateObject sh) {
        sh.counter.increment();
    }

    @Arbiter
    public void actor3(LongResult r, StateObject sh) {
        r.r1 = sh.counter.value();
    }
}
