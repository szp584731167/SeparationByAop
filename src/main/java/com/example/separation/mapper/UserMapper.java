package com.example.separation.mapper;

import com.example.separation.commen.MyMapper;
import com.example.separation.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Szp
 * @version 1.0
 * @date 2020/6/18 17:13
 */
@Mapper
public interface UserMapper extends MyMapper<User> {}
