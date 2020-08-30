package demo.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import demo.entity.User;
import demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
//@RequestMapping("/test")
public class UserController {

    @Autowired
    private UserService userService;

    //?id=xxxx传值方式
    @GetMapping("/user/getByParam")
    //当微服务出现异常时，启动熔断机制，则调用方法hystrixGetUser返回默认的信息
    @HystrixCommand(fallbackMethod = "hystrixGetUser")
    public User getUserByParam(@RequestParam int id) throws Exception {

        User user = userService.Sel(id);
        if(user==null){
            //抛出异常之后，就会启用熔断机制，返回备用方法信息hystrixGetUser
            throw new Exception("xxxxx");
        }
        return user;
    }

    //在访问路径中传值方式
    @GetMapping("/user/get/{id}")
    //当微服务出现异常时，启动熔断机制，则调用方法hystrixGetUser返回默认的信息
    @HystrixCommand(fallbackMethod = "hystrixGetUser")
    public User getUser(@PathVariable("id") int id) throws Exception {

        User user = userService.Sel(id);
        if(user==null){
            //抛出异常之后，就会启用熔断机制，返回备用方法信息hystrixGetUser
            throw new Exception("xxxxx");
        }
        return user;
    }

    //备选方法：服务熔断时调用
    //出现错误时，启动熔断机制，调用此方法，返回默认的提示信息
    public User hystrixGetUser(int id){
        User user=new User(id,"未找到用户名","未找到密码","未找到真实名称");
        return user;
    }




    @GetMapping("/user/list/{page}/{rows}")
    //当微服务出现异常时，启动熔断机制，则调用方法hystrixGetUser返回默认的信息
    @HystrixCommand(fallbackMethod = "hystrixGetUserList")
    public List<User> getUserList(@PathVariable("page") String page, @PathVariable("rows") String rows){

        Map<String,String> params=new HashMap<>();
        params.put("page",page);
        params.put("rows",rows);

        return  userService.selList(params);
    }

    //备选方法：服务熔断时调用
    //出现错误时，启动熔断机制，调用此方法，返回默认的提示信息
    public List<User> hystrixGetUserList(String page, String rows){
        User user=new User(-1,"未找到用户名","未找到密码","未找到真实名称");
        List<User> list=new ArrayList<>();
        list.add(user);
        return list;
    }




    //注意，如果post参数定义的是对象，需要加上@RequestBody，否则RestTemplate传递的对象此处接收不到
    @PostMapping("/user/add")
    //当微服务出现异常时，启动熔断机制，则调用方法hystrixGetUser返回默认的信息
    @HystrixCommand(fallbackMethod = "hystrixAddUser")
    public int addUser(@RequestBody User user) throws Exception {

        return userService.addUser(user);
    }

    //备选方法：服务熔断时调用
    //出现错误时，启动熔断机制，调用此方法，返回默认的提示信息
    public int hystrixAddUser(User user){
        return  -1;
    }

}
