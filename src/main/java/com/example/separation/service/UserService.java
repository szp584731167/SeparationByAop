package com.example.separation.service;

import com.example.separation.domain.User;
import com.example.separation.dynamicdatasource.DataSourceSelector;
import com.example.separation.dynamicdatasource.DynamicDataSourceEnum;
import com.example.separation.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        return users;
    }

    @DataSourceSelector(value = DynamicDataSourceEnum.MASTER)
    public int update() {
        User user = new User();
        user.setUserId(Long.parseLong("1196978513958141953"));
        user.setUserName("修改后的名字3");
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @DataSourceSelector(value = DynamicDataSourceEnum.SLAVE)
    public User find() {
        User user = new User();
        user.setUserId(Long.parseLong("1196978513958141953"));
        return userMapper.selectByPrimaryKey(user);
    }
}
