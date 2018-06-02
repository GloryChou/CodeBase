package debugandtest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * LogReaderThread单元测试示例JUint代码
 *
 * @author Kyle
 * @create 2018/5/31 23:49
 */
public class LogReaderThreadTest {
    private LogReaderThread logReader;
    private StringBuilder sdb;

    @Before
    public void  setUp() throws Exception {
        sdb = new StringBuilder();
        // 拼接数据
        sdb.append("");

        InputStream in = new ByteArrayInputStream(sdb.toString().getBytes("UTF-8"));
        logReader = new LogReaderThread(in, 1024, 4);
        logReader.start();
    }

    @After
    public void tearDown() throws Exception {
        logReader.interrupt();
    }

    @Test
    public void testNextBatch() {
        try {
            RecordSet rs = logReader.nextBatch();
            StringBuilder contents = new StringBuilder();
            String record;
            while (null != (record = rs.nextRecord())) {
                contents.append(record).append("\n");
            }
            Assert.assertTrue(contents.toString().equals(sdb.toString()));
        } catch (InterruptedException ie) {

        }
    }
}
