package com.chen.context.config;

import com.chen.context.auth.MyUserDetailsService;
import com.chen.context.auth.MyUsernamePasswordAuthenticationFilter;
import com.chen.context.auth.MyAuthenticationFailureHandler;
import com.chen.context.auth.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 自定义登录页面和登录逻辑
 * 设置授权
 * @author: chen
 * @date: 2021/4/12 22:13
 * @description: 思路清晰
 */
@Configuration
@EnableWebSecurity
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    //认证成功
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    //认证失败
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    //设置拦截
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/msg.html") //自定义登录页面修改为信息页面
                .loginProcessingUrl("/login");  //自定义的登录接口，http://xxx:端口号/login

        //授权控制
        http.authorizeRequests()
                .antMatchers("/blog/**").permitAll() // /blog/**接口全部放行
                .antMatchers("/root/status").hasAnyAuthority("admin","root")  //给admin只放行/root/status接口，让他能进入后台
                .antMatchers("/main/**").hasAuthority("root")   //给root用户放行
                .antMatchers("/root/**").hasAuthority("root");
                //.anyRequest().authenticated(); //所有请求都必须认证才能访问，即必须登录

        //退出登录
        http.logout()
                .logoutUrl("/logout")  //自定义退出接口
                .logoutSuccessUrl("/blog/333");    //退出成功后跳转的页面

        http.csrf().disable().exceptionHandling();  //关闭csrf防护

        //替换过滤器为自定义的 myUsernamePasswordAuthenticationFilter
        http.addFilterAt(myUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter() throws Exception {
        MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter = new MyUsernamePasswordAuthenticationFilter();

        //authenticationmanager – 用于自定义筛选器
        myUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());

        //设置认证成功执行的操作
        myUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        //设置认证失败执行的操作
        myUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);

        return myUsernamePasswordAuthenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //使用自定义的UserDetailsService
        auth.userDetailsService(myUserDetailsService);
    }
}