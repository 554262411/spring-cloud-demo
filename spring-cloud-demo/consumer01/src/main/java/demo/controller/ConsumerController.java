package demo.controller;

import com.google.gson.Gson;
import demo.entity.User;
import demo.service.UserClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ConsumerController {

    //Ribbon方式使用，调用restful
    @Autowired
    private RestTemplate restTemplate;  //Bean实例打了Ribbon负载均衡@LoadBalanced注解，就可Eureka结合起来了

    //Feign方式注入，在api子项目中定义
    @Autowired
    private UserClientService userClientService;

    /*************************【1】Ribbon方式实现：使用微服务名称代替固定的地址和端口**************************/

    @GetMapping("/user/getByParamRibbon")
    public User getUserByParamRibbon(@RequestParam int id){

        //集成了Eureka，通过服务名去访问
        return restTemplate.getForObject("http://PROVIDER01/user/getByParam?id=" + id, User.class);
    }

    //Ribbon+Eureka方式，通过微服务名称
    //不配置具体的访问地址和端口，从Eureka里面获取应用的名称访问，结合Ribbon在RestTemplate上的负载均衡使用
    //请看：ConfigBean，否则Eureka没有相结合的点
    @GetMapping("/user/getRibbon/{id}")
    public User getUserRibbon(@PathVariable("id") int id){

        //集成了Eureka，通过服务名去访问
        return restTemplate.getForObject("http://PROVIDER01/user/get/" + id, User.class);
    }

    @GetMapping("/user/listRibbon/{page}/{rows}")
    public List<User> getUserListRibbon(@PathVariable("page") String page, @PathVariable("rows") String rows){

        return restTemplate.getForObject("http://PROVIDER01/user/list/" + page+"/"+rows, List.class);
    }

    @PostMapping("/user/addRibbon")
    public int addUserRibbon(User user) throws Exception {

        //Gson gson=new Gson();
        //String json = gson.toJson(user);
        return restTemplate.postForObject("http://PROVIDER01/user/add/", user, int.class);
    }



    /*************************【2】传统方式实现：写固定的服务地址和端口**************************/
    @GetMapping("/user/getByParam")
    public User getUserByParam(@RequestParam int id){

        return restTemplate.getForObject("http://localhost:8001/user/getByParam?id=" + id, User.class);
    }

    @GetMapping("/user/get/{id}")
    public User getUser(@PathVariable("id") int id){

        return restTemplate.getForObject("http://localhost:8001/user/get/" + id, User.class);
    }

    @GetMapping("/user/list/{page}/{rows}")
    public List<User> getUserList(@PathVariable("page") String page, @PathVariable("rows") String rows){

        return restTemplate.getForObject("http://localhost:8001/user/list/" + page+"/"+rows, List.class);
    }

    @PostMapping("/user/add")
    public int addUser(User user) throws Exception {

        //Gson gson=new Gson();
        //String json = gson.toJson(user);
        return restTemplate.postForObject("http://localhost:8001/user/add/", user, int.class);
    }




    /*************************【3】Feign方式实现：通过接口和注解的方式实现，更加方便，不在需要RestTemplate**************************/
    /*************************启用Feign，注解：@EnableFeignClients(basePackages = {"demo"})，同时需要在接口上加上注解@FeignClient(value = "PROVIDER01")，注解中执行微服务的名称*/

    @GetMapping("/user/getByParamFeign")
    public User getUserByParamFeign(@RequestParam int id){

        return userClientService.getUserByParam(id);
    }

    @GetMapping("/user/getFeign/{id}")
    public User getUserFeign(@PathVariable("id") int id){

        return userClientService.getUser(id);
    }

    @GetMapping("/user/listFeign/{page}/{rows}")
    public List<User> getUserListFeign(@PathVariable("page") String page, @PathVariable("rows") String rows){

        return userClientService.getUserList(page,rows);
    }

    @PostMapping("/user/addFeign")
    public int addUserFeign(User user) throws Exception {

        return userClientService.addUser(user);
    }


}
