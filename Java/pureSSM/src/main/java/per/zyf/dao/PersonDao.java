package per.zyf.dao;

import java.util.List;

import per.zyf.entity.Person;    
  
public interface PersonDao {  
      
    public List<Person> getPersons();  
      
    public void addPerson(Person person);  
}  
