package com.example.separation.service;

import com.example.separation.domain.User;
import com.example.separation.dynamicdatasource.DataSourceSelector;
import com.example.separation.dynamicdatasource.DynamicDataSourceEnum;
import com.example.separation.event.UpdateEvent;
import com.example.separation.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Szp
 * @version 1.0
 * @date 2020/6/18 17:06
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @DataSourceSelector(value = DynamicDataSourceEnum.SLAVE)
    public List<User> listUser() {
        List<User> users = userMapper.selectAll();
        System.out.println(userMapper);
        return users;
    }


    @Autowired
    ApplicationContext applicationContext;
    /**
     * 扩展。修改后需要短信通知、推送消息等复杂业务逻辑。为了解耦，此时使用观察者模式
     * @return
     */
    @DataSourceSelector(value = DynamicDataSourceEnum.MASTER)
    public int update() {
        User user = new User();
        user.setUserId(Long.parseLong("1196978513958141953"));
        user.setUserName("修改后的名字3");
        applicationContext.publishEvent(new UpdateEvent("名字已修改"));
        //这样写违反单一开闭原则，所以采用观察者模式,以下操作不再做
        //System.out.println("发送短信通知");
        //System.out.println("推送消息");

        return userMapper.updateByPrimaryKeySelective(user);
    }

    @DataSourceSelector(value = DynamicDataSourceEnum.SLAVE)
    public User find() {
        User user = new User();
        user.setUserId(Long.parseLong("1196978513958141953"));
        return userMapper.selectByPrimaryKey(user);
    }
}
