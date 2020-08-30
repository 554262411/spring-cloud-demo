package demo.mapper;

import demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@Repository
@Mapper
public interface UserMapper {

    User Sel(int id);

    List<User> selList();

    int addUser(User user);

    //通过注解sql语句
    //@Select("select * from account where account_id=1")
    //User getAccount();

    //@Update("update account set balance = balance+100 where account_id=1")
    //void addMoney();


    /*@Mapper和@Repository的区别
    相同点
            两个都是注解在Dao上

    回到顶部
            不同
    @Repository需要在Spring中配置扫描地址，然后生成Dao层的Bean才能被注入到Service层中。

    @Mapper不需要配置扫描地址，通过xml里面的namespace里面的接口地址，生成了Bean后注入到Service层中。*/
}
