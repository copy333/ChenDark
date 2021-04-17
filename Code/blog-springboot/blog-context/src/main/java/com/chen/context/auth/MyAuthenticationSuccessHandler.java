package com.chen.context.auth;

import com.chen.common.utils.Consts;
import com.chen.common.utils.R;
import com.chen.context.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 认证成功
 * @author: chen
 * @date: 2021/4/12 22:15
 * @description: 思路清晰
 */
@Component //把普通pojo实例化到spring容器中
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        //jackson
        ObjectMapper objectMapper = new ObjectMapper();

        //获取用户信息
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails){
            MyUserDetails myUserDetails = (MyUserDetails) principal;
            //获取用户基本信息
            User user = myUserDetails.getUser();

            //获取权限信息
            List<String> roles = new ArrayList<>();
            for (GrantedAuthority role: myUserDetails.getAuthorities()) {
                roles.add(role.getAuthority());
            }

            //设置header信息
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(
                    objectMapper.writeValueAsString(
                            R.ok()
                                    .data(Consts.CODE,1)
                                    .data("user",user)
                                    .data("roles",roles)
                    )
            );

        }

    }
}
