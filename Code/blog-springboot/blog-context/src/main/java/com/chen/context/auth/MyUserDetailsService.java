package com.chen.context.auth;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.context.entity.User;
import com.chen.context.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 实现了用户信息的核心接口UserDetailsService
 * @author: chen
 * @date: 2021/4/12 21:57
 * @description: 思路清晰
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //username字段具有唯一性约束（Unique）
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);

        //查找对应的用户数据
        User user = userService.getOne(queryWrapper);

        if (user == null){
            throw new UsernameNotFoundException("没有该用户");
        }

        MyUserDetails userDetails = new MyUserDetails();
        userDetails.setUser(user);
        userDetails.setUsername(user.getUsername());
        //noop代表未加密
        userDetails.setPassword("{noop}" + user.getPassword());

        //获取到该用户的角色（权限） - root(管理员角色) / user(普通角色) / test(测试用户角色)
        List<String> roles = userService.getUserRoleByUsername(user.getUsername());
        if (roles.size()>0){
            Set<GrantedAuthority> authorities =new HashSet<>();
            SimpleGrantedAuthority authority = null;
            for (String role: roles) {
                authority = new SimpleGrantedAuthority(role);
                authorities.add(authority);
            }
            userDetails.setAuthorities(authorities);
        }

        return userDetails;
    }
}
