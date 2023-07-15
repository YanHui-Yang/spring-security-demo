package com.max.mem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 简单来说，内存认证就是将密码写死在代码，或者配置文件中
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 用户名: admin, 密码: 123456, 角色: ADMIN
        //auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("123456")).roles("ADMIN");
        // 用户名: user, 密码: 123123, 角色: USER
        //auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("123123")).roles("USER");
        // 如果不想加密，可以在密码前定义{noop}
        // 注意，密码不加密，不能够声明PasswordEncoder Bean，把下面的PasswordEncoder Bean注释掉才能使用
        auth.inMemoryAuthentication().withUser("testNoop").password("{noop}123").roles("USER");
    }

    /**
     * 指定加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用BCrypt加密密码，BCrypt使用的是随机盐加密，因此每次密码都不同
        return new BCryptPasswordEncoder();
    }

    // 以上是通过AuthenticationManagerBuilder实现的内存认证，也可以使用UserDetailsService实现内存认证
    // 这种方式要注释上面的PasswordEncoder Bean和configure，才生效
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("zhangsan")
                .password("123123")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }
}
