package per.zyf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import per.zyf.dao.PersonDao;
import per.zyf.entity.Person;
import per.zyf.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    @Override
    public void addPerson(Person person) {
        // 加入数据
        personDao.addPerson(person);
        System.out.println("sd");
    }

    @Override
    public List<Person> getPersonList() {
        // 查数据
        List<Person> persons = personDao.getPersons();
        return persons;
    }

}
