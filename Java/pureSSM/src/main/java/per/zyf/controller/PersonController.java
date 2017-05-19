package per.zyf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import per.zyf.dao.PersonDao;
import per.zyf.entity.Person;

@Controller  
public class PersonController {  
      
    @Autowired  
    private PersonDao personDao;  
      
    @RequestMapping("/addPerson")  
    public ModelAndView addPerson(Person person){  
        System.out.println("页面数据："+person);  
          
        //加入数据  
        personDao.addPerson(person);  
          
        //查数据  
        List<Person> persons=personDao.getPersons();  
          
        //存起来  
        ModelAndView modelAndView=new ModelAndView();  
        modelAndView.setViewName("success");  
        modelAndView.addObject("persons", persons);  
          
        return modelAndView;  
    }  
      
}    