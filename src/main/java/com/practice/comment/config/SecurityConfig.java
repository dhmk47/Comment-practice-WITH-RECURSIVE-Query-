package com.practice.comment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .csrf().disable();

        http.authorizeRequests()
                .antMatchers("/admin")
                .access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .anyRequest()
                .permitAll()

                .and()

                .formLogin()
                .loginPage("/board/1")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/board/1")

                .and()

                .logout()
                .logoutSuccessUrl("/board/1");
    }
}
