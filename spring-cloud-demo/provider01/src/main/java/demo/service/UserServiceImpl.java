package demo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import demo.entity.User;
import demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
/*

    private static final Map<Integer, Item> map = new HashMap<>();
    //创建假数据
    static {
        map.put(1, new Item(1, "商品1", "描述1", "http://localhost/pic/pic1"));
        map.put(2, new Item(2, "商品2", "描述2", "http://localhost/pic/pic2"));
    }
*/

    @Autowired
    UserMapper userMapper;

    @Override
    public User Sel(int id){

        return userMapper.Sel(id);
    }

    @Override
    public List<User> selList(Map<String,String> params) {
        //return userMapper.selList();
        int page =Integer.parseInt(params.get("page").toString());
        int rows =Integer.parseInt(params.get("rows").toString());

        //带分页
        PageHelper.startPage(page, rows);
        List<User> list = userMapper.selList();
        PageInfo<User> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();  //获取的是总数量，不是分页数量
        return pageInfo.getList();
    }

    //Spring的默认的事务规则是遇到运行异常（RuntimeException及其子类）
    //@Transactional(rollbackFor = Exception.class)
    @Transactional
    @Override
    public int addUser(User user) throws Exception {
        //先增加
        return userMapper.addUser(user);
        //然后遇到故障，在控制中调用异常时，测试事务是否会回滚
        //throw new RuntimeException("发生异常了..");
    }

}
