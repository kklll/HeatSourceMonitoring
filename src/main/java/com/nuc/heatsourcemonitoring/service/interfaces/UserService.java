package com.nuc.heatsourcemonitoring.service.interfaces;


import com.nuc.heatsourcemonitoring.pojo.User;

/**
 * @Author DeepBlue
 * @Date 2020/11/12 16:19
 */
public interface UserService {
    /**
     * 登录接口
     * @param user
     * @return
     */
    User login(User user);
}
