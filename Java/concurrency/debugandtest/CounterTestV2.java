package debugandtest;

import org.openjdk.jcstress.annotations.*;

/**
 *
 *
 * @author Kyle
 * @create 2018/6/2 23:19
 */
@JCStressTest
@State
@Description("测试Counter的线程安全性")
@Outcome(id = "[2]", expect = Expect.ACCEPTABLE, desc = "OK")
@Outcome(id = "[1]", expect = Expect.FORBIDDEN, desc = "丢失更新或者读脏数据")
public class CounterTestV2 {
    final Counter counter = new Counter();

    @Actor
    public void actor1() {
        counter.increment();
    }

    @Actor
    public void actor2() {
        counter.increment();
    }

    @Arbiter
    public void actor3(LongResult1 r) {
        r.r1 = counter.value();
    }
}
