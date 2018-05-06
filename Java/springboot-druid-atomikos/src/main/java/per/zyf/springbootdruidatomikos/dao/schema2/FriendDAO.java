package per.zyf.springbootdruidatomikos.dao.schema2;

import org.springframework.stereotype.Repository;
import per.zyf.springbootdruidatomikos.entity.FriendEntity;

/**
 * @author glorychou
 * @create 2018/5/6 14:21
 */
@Repository
public interface FriendDAO {
    void insert(FriendEntity friendEntity);
}
