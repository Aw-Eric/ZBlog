package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.constants.SystemConstants;
import org.example.domain.entity.LoginUser;
import org.example.domain.entity.User;
import org.example.mapper.MenuMapper;
import org.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户信息
     * @throws UsernameNotFoundException 用户不存在异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);
        // 判断是否查到用户，如果没查到，抛出异常
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户不存在");
        }

        // 查询权限信息封装
        // 如果是后台用户 才需要查询权限封装
        if (user.getType().equals(SystemConstants.USER_TYPE_ADMIN)) {
            // 查询用户权限信息
            List<String> list = menuMapper.selectPermsByUserId(user.getId());
            // 返回用户信息
            return new LoginUser(user, list);
        }
        // 返回用户信息
        return new LoginUser(user, null);
    }
}
