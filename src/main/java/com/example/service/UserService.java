package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.User;

public interface UserService extends IService<User> {
    
    /**
     * 用户注册
     */
    boolean register(User user);
    
    /**
     * 用户登录
     */
    String login(String username, String password);
    
    /**
     * 根据用户名查找用户
     */
    User findByUsername(String username);
    
    /**
     * 更新用户信息
     */
    boolean updateUserInfo(User user);
} 