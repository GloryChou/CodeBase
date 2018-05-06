package per.zyf.springbootdruidatomikos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import per.zyf.springbootdruidatomikos.service.UserFriendService;

/**
 * @author Kyle
 * @create 2018/5/6 11:36
 */
public class UserFriendServiceImpl implements UserFriendService {

    @Autowired
    private JdbcTemplate schema1JdbcTemplate;
    @Autowired
    private JdbcTemplate schema2JdbcTemplate;

    @Override
    public void insertUserFriend() throws Exception {
        schema1JdbcTemplate.execute("insert into user(name,age) values('bonne',18)");
        schema2JdbcTemplate.execute("insert into friend(user_id,friend_id) values(2,1)");
    }
}
