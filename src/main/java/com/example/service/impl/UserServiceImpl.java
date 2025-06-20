package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public boolean register(User user) {
        // 检查用户名是否已存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        if (this.count(queryWrapper) > 0) {
            return false;
        }
        
        // 密码加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        
        return this.save(user);
    }
    
    @Override
    public String login(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = this.getOne(queryWrapper);
        
        if (user != null && user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return jwtUtil.generateToken(user.getId().toString());
        }
        
        return null;
    }
    
    @Override
    public User findByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return this.getOne(queryWrapper);
    }
    
    @Override
    public boolean updateUserInfo(User user) {
        // 不允许更新密码
        user.setPassword(null);
        return this.updateById(user);
    }
} 