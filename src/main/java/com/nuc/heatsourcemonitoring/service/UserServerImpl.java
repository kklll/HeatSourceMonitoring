package com.nuc.heatsourcemonitoring.service;

import com.nuc.heatsourcemonitoring.mapper.UserMapper;
import com.nuc.heatsourcemonitoring.mapper.UsersMapper;
import com.nuc.heatsourcemonitoring.pojo.User;
import com.nuc.heatsourcemonitoring.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author DeepBlue
 * @Date 2020/11/12 16:18
 */
@Service
public class UserServerImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * Login 方法的实现，返回username=传进来的name and password=传进来的password的人，
     * 如果找到，证明有这个用户，否则没这个用户
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public User login(User user) {
        User login = userMapper.login(user);
        if (login != null) {
            return login;
        }
        return null;
//        throw new RuntimeException("登陆失败，用户名或密码错误！");
    }
}
