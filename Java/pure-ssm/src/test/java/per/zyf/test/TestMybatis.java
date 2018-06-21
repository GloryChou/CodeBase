package per.zyf.test;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import per.zyf.entity.Person;

public class TestMybatis {
    private SqlSession sqlSession;
    
    @Before
    public void before() {
        try {
            SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("myBatisConfig.xml"));  
            sqlSession = sqlSessionFactory.openSession();
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
    }
    
    @After  
    public void after(){  
        sqlSession.commit();  
        sqlSession.close();  
    }  
      
      
    @SuppressWarnings("deprecation")
    @Test  
    public void test(){  
        //PersonDao personDao=sqlSession.getMapper(PersonDao.class);  
        //System.out.println(personDao.getPersons().size());
        List<Person> pl = sqlSession.selectList("getPersons");
        Assert.assertEquals("小红", pl.get(0).getPname());
    }  
}
