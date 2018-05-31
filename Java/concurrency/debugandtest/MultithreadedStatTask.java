package debugandtest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kyle
 * @create 2018/5/31 23:55
 */
public class MultithreadedStatTask {
    private MultithreadedStatTask mst;
    private int recordCount = 0;
    private String[] records;
    
    @Before
    public void setUp() throws Exception {
        
    }
    
    @After
    public void tearDown() throws Exception {
        recordCount = 0;
    }
    
    @Test
    public void testRun() {
        
    }
    
    private MultithreadedStatTask createTask(
            int sampleInterval,
            int traceIdDiff, String expectedOperationName,
            String expectedExternalDeviceList
    ) throws Exception {
        return null;
    }
}
