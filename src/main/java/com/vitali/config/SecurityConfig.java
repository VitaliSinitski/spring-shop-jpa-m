package com.vitali.config;

import com.vitali.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http
                .csrf().disable()
//                .authorizeRequests().anyRequest().authenticated()
                .authorizeRequests()
//                .anyRequest().permitAll()
//                .mvcMatchers("/admin").hasRole("ADMIN")
                .mvcMatchers("/cart").hasRole("USER")
                .and()
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/products")
                        .permitAll());

//                .anyRequest().permitAll()
//                .and()
//                .formLogin().loginPage("/login").permitAll()
//                ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}