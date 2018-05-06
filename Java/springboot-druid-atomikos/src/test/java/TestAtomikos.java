
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import per.zyf.springbootdruidatomikos.SpringBootDruidAtomikosApplication;

import javax.transaction.Transactional;

/**
 * @author Kyle
 * @create 2018/5/5 21:53
 */
@SpringBootTest(classes = SpringBootDruidAtomikosApplication.class)
@RunWith(SpringRunner.class)
/**
 * @Transactional
 * 加上此注解事务会回滚
  */
public class TestAtomikos {

    @Autowired
    private JdbcTemplate schema1JdbcTemplate;

    @Autowired
    private JdbcTemplate schema2JdbcTemplate;

    @Test
    public void test() {
        System.out.println("begin.....");
        schema1JdbcTemplate.execute("insert into user(name,age) values('bonne',18)");
        schema2JdbcTemplate.execute("insert into friend(user_id,friend_id) values(2,1)");
        System.out.println("end.....");
    }
}
