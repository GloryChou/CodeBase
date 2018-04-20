package per.zyf.springbootdubbo.api;

import org.springframework.stereotype.Service;

@Service("UserService")
public interface UserService {
    int selectCount() throws Exception;
}
