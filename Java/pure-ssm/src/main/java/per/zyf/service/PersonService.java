package per.zyf.service;

import java.util.List;

import per.zyf.entity.Person;

public interface PersonService {
    void addPerson(Person person);
    
    List<Person> getPersonList();
}
