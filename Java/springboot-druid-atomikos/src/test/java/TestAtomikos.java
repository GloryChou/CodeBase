import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Kyle
 * @create 2018/5/5 21:53
 */
public class TestAtomikos {

    @Autowired
    private JdbcTemplate schema1JdbcTemplate;

    @Autowired
    private JdbcTemplate schema2JdbcTemplate;

    @Test
    public void test() {
        System.out.println("begin.....");
        schema1JdbcTemplate.execute("insert into person(id) values(1)");
        schema2JdbcTemplate.execute("insert into bus_b(id) values(2)");
        System.out.println("end.....");
    }
}
