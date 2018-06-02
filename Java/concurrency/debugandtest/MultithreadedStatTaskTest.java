package debugandtest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Collection;

/**
 * @author Kyle
 * @create 2018/5/31 23:55
 */
public class MultithreadedStatTaskTest {
    private MultithreadedStatTask mst;
    private int recordCount = 0;
    private String[] records;

    @Before
    public void setUp() throws Exception {
        records = new String[4];
        records[0] = "";
        records[1] = "";
        records[2] = "";
        records[3] = "";
        mst = createTask(10, 3, "sendSms", "*");
    }

    @After
    public void tearDown() throws Exception {
        recordCount = 0;
    }

    @Test
    public void testRun() {
        mst.run();
        Assert.assertTrue(records.length == recordCount);
    }

    private MultithreadedStatTaskTest createTask(
        int sampleInterval,
        int traceIdDiff, String expectedOperationName,
        String expectedExternalDeviceList
    ) throws Exception {
        // Stub对象
        final AbstractLogReader logReader = new AbstractLogReader(
            new ByteArrayInputStream(new byte[] {}), 1024, 4
        ) {
            boolean eof = false;
            RecordSet consumedBatch = new RecordSet(super.batchSize);

            @Override
            protected RecordSet getNextToFill() {
                return null;
            }

            @Override
            protected RecordSet nextBatch() {
                if(eof) {
                    return null;
                }
                for(String r :records) {
                    consumedBatch.putRecord(r);
                }
                eof = true;
                return consumedBatch;
            }

            @Override
            protected void publish(RecordSet recordSet) {
                // nothing to do
            }

            @Override
            public void run() {
                // nothing to do
            }
        };

        // 返回MultithreadedStatTask的匿名子类
        // 不使用StatProcessor的真实实现类RecordProcessor，二是使用Stub类FakeProcessor
        return new MultithreadedStatTaskTest(sampleInterval, new FakeProcessor()) {
            @Override
            protected AbstractLogReader createLogReader() {
                // 不返回AbstractLogReader类的真实实现类LogReaderThread，二是一个Stub类实例
                return logReader;
            }
        };

        // Stub类
        class FakeProcessor implements StatProcess {
            @Override
            public void process(String record) {
                recordCount++;
            }

            @Override
            public Map<Long, DelayItem> getResult() {
                // 不关心该方法，故返回空Map
                return Collections.emptyMap();
            }
        }
    }
}
