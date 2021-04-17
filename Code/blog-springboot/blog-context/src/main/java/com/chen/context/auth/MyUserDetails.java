package com.chen.context.auth;
import com.chen.context.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * 生成自定义UserDetails对象
 * @author: chen
 * @date: 2021/4/12 21:57
 * @description: 思路清晰
 */
@Data
@AllArgsConstructor    //全参构造函数
@NoArgsConstructor    //无参构造函数
public class MyUserDetails implements UserDetails {

    private User user;

    private String password;
    private String username;
    private Set<GrantedAuthority> authorities;
    private boolean accountNonExpired =true;
    private boolean accountNonLocked=true;
    private boolean credentialsNonExpired=true;
    private boolean enabled=true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
