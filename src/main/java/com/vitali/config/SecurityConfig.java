package com.vitali.config;

import com.vitali.security.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/cart/**").hasAuthority("USER")
                .antMatchers("/admin/**", "/users", "/user").hasAuthority("ADMIN")
                .antMatchers("/login").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler) // Добавляем обработчик успешной аутентификации
                .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .csrf().disable();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        http.httpBasic();
//        http
//                .csrf().disable()
////                .authorizeRequests().anyRequest().authenticated()
//                .authorizeHttpRequests(urlConfig -> urlConfig
////                                .antMatchers("/login", "/registration", "/v3/api-docs/**", "/swagger-ui/**", "/products/**", "/").permitAll()
//                                .antMatchers("/cart/**").hasAuthority("USER")
//                                .antMatchers("/admin/**", "/users", "/user").hasAuthority("ADMIN")
////                                .antMatchers("/cart/**").hasAuthority("ADMIN")
////                        .mvcMatchers("/cart").hasRole("USER")
////                                .anyRequest().authenticated()
//
//                )
////                .anyRequest().permitAll()
////                .mvcMatchers("/admin").hasRole("ADMIN")
//
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login")
//                        .deleteCookies("JSESSIONID"))
//                .formLogin(login -> login
//                        .loginPage("/login")
//                        .successHandler(customAuthenticationSuccessHandler) // Добавляем обработчик успешной аутентификации
//                        .defaultSuccessUrl("/products"));
//
//
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}