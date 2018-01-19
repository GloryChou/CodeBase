package per.zyf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import per.zyf.entity.Person;
import per.zyf.service.PersonService;


@Controller
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping("/addPerson")
    public ModelAndView addPerson(Person person) {
        System.out.println("页面数据：" + person);

        try {
            personService.addPerson(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        List<Person> persons = personService.getPersonList();

        // 存起来
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        modelAndView.addObject("persons", persons);

        return modelAndView;
    }

}