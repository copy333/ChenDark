package com.chen.context.auth;

import com.chen.common.utils.Consts;
import com.chen.common.utils.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败
 * @author: chen
 * @date: 2021/4/12 22:17
 * @description: 思路清晰
 */
@Component //把普通pojo实例化到spring容器中
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        //jackson
        ObjectMapper objectMapper = new ObjectMapper();

        //设置header信息
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(
                objectMapper.writeValueAsString(
                        R.error()
                                .data(Consts.CODE,"333")
                                .data("data","认证失败")
                )
        );
    }
}
