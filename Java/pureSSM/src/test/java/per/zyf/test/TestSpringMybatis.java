package per.zyf.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.Assert;
import per.zyf.dao.PersonDao;

public class TestSpringMybatis {
    
    @SuppressWarnings("deprecation")
    @Test
    public void test() {
        // 加载Spring配置
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        PersonDao personDao = (PersonDao) ac.getBean("personDao");
        Assert.assertEquals(2, personDao.getPersons().size());
    }
}
