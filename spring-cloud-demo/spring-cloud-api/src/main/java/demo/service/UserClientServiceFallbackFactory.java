package demo.service;

import demo.entity.User;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用于服务降级
 */
@Component
public class UserClientServiceFallbackFactory implements FallbackFactory {

    @Override
    public UserClientService create(Throwable throwable) {
        return new UserClientService() {
            @Override
            public User getUserByParam(int id) {
                User user=new User(id,"服务被做了降级：未找到用户名","服务被做了降级：未找到密码","服务被做了降级：未找到真实名称");
                return user;
            }

            @Override
            public User getUser(int id) {
                User user=new User(id,"服务被做了降级：未找到用户名","服务被做了降级：未找到密码","服务被做了降级：未找到真实名称");
                return user;
            }

            @Override
            public List<User> getUserList(String page, String rows) {
                return null;
            }

            @Override
            public int addUser(User user) throws Exception {
                return 0;
            }
        };
    }
}
