package com.chen.context.auth;
import com.chen.context.aes.AesEncryptUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 编写自定义的基于用户名和密码的身份验证过滤器
 * @author: chen
 * @date: 2021/4/12 22:12
 * @description: 思路清晰
 */
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //携带头要是json格式得application/json;charset=UTF-8
        //前后端分离 这里是接收的是json串
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)){
            //如果不是POST请求就输出异常
            //因为签名概念说过 UsernamePasswordAuthenticationFilter 强制只对POST请求应用
            if(!request.getMethod().equals("POST")){
                throw new AuthenticationServiceException(
                        "Authentication method not supported" + request.getMethod()
                );
            }

            //将前端传来的json流封装成Map对象，因为前端 form表单传来的是 用户名和密码
            Map<String,String> map = new HashMap<>();
            //jackson
            ObjectMapper objectMapper = new ObjectMapper();

            try (InputStream is = request.getInputStream()) {
                //字符串转集合
                map = objectMapper.readValue(is, Map.class);

            } catch (IOException e){
                e.printStackTrace();
            }

            if(map!=null){
                String username = map.get("username");
                String password = map.get("password");
                try {
                    username = AesEncryptUtil.desEncrypt(username);
                    password = AesEncryptUtil.desEncrypt(password);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (username == null) {
                    username = "";
                }

                if (password == null) {
                    password = "";
                }

                username = username.trim();
                password = password.trim();

                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

                this.setDetails(request, authRequest);

                return this.getAuthenticationManager().authenticate(authRequest);
            }
            return null;

        }

        return null;
    }
}
