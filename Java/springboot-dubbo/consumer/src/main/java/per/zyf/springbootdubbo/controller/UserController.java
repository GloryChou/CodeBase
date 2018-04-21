package per.zyf.springbootdubbo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import per.zyf.springbootdubbo.api.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/sum", method = RequestMethod.GET)
    public int selectCount() throws Exception {
        return userService.selectCount();
    }
}
