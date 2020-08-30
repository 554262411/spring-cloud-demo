package demo.service;

import demo.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 用于服务熔断
 */
//value中设置的是微服务的名称，也就是提供者的应用程序名称
@Component
@FeignClient(value = "PROVIDER01", fallbackFactory = UserClientServiceFallbackFactory.class)
public interface UserClientService {

    //接口的定义需要和注册到Eureka提供者的Controller控制器中的微服务的定义结构保持一致
    //restTemplate.getForObject("http://PROVIDER01/user/get/" + id, User.class);

    @GetMapping("/user/getByParam")
    User getUserByParam(@RequestParam int id);

    @GetMapping("/user/get/{id}")
    User getUser(@PathVariable("id") int id);

    @GetMapping("/user/list/{page}/{rows}")
    List<User> getUserList(@PathVariable("page") String page, @PathVariable("rows") String rows);

    @PostMapping("/user/add")
    int addUser(User user) throws Exception;
}
