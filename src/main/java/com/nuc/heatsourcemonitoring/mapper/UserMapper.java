package com.nuc.heatsourcemonitoring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nuc.heatsourcemonitoring.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName UserMapper
 * @Deacription
 * @Author DeepBlue
 * @Date 2020/11/26 18:46
 * @Version 1.0
 **/

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select username,password from user where username=#{username} and password=#{password} ")
    User login(User user);
}