import com.qianbo.demo5.entity.User;
import com.qianbo.demo5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShowUsers {
    @Autowired
    private UserService userService;

    @PostMapping("showemployee/{usercode}")
    public User ShowEmployeeByUsercode(@PathVariable("usercode") Integer usercode){
        System.out.println(usercode);
        User user = userService.GetUserByUsercode(usercode);
        return user;
    }


    @PostMapping("getdata")
    public List<User> ShowUserByDate(@RequestParam("startTime") String startTime, @RequestParam("lastTime") String lastTime){
        List<User> employees = userService.findUserByDate()(startTime,lastTime);
        return  employees;
    }
}
