package com.vitali.config;

import com.vitali.security.CustomAuthenticationSuccessHandler;
import com.vitali.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
public class SecurityConfigNew/* extends WebSecurityConfigurerAdapter */{
//    private final UserService userService;
//    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userService)
//                .passwordEncoder(passwordEncoder());
//    }
//    @Bean
//    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
//        return new CustomAuthenticationSuccessHandler();
//    }
//    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .antMatchers("/cart/**").hasAuthority("USER")
                        .antMatchers("/admin/**", "/users", "/user").hasAuthority("ADMIN")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"))
                .formLogin(login -> login
                        .loginPage("/login")
//                        .successHandler(customAuthenticationSuccessHandler) // Добавляем обработчик успешной аутентификации
                        .defaultSuccessUrl("/products"));
    }

//    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //    @Bean
//    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
//        return new CustomAuthenticationSuccessHandler();
//    }

}
