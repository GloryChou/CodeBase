package per.zyf.springbootdubbo.service;

import org.springframework.stereotype.Service;
import per.zyf.springbootdubbo.api.UserService;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    @Override
    public int selectCount() throws Exception {
        return 0;
    }
}
