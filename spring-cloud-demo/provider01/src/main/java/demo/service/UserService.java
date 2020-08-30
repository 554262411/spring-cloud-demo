package demo.service;

import demo.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User Sel(int id);
    List<User> selList(Map<String, String> params);
    int addUser(User user) throws Exception;
}
