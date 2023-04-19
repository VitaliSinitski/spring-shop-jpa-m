package com.vitali.config;

import com.vitali.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private final UserService userService;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic();
//        http
//                .csrf().disable()
////                .authorizeRequests().anyRequest().authenticated()
//                .authorizeRequests()
////                .anyRequest().permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN")
////                .mvcMatchers("/admin").hasRole("ADMIN")
//                .anyRequest().authenticated()
//                .and()
////                .mvcMatchers("/cart").hasRole("USER")
////                .and()
//                .formLogin(login -> login
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/products")
//                        .permitAll());
//
////                .anyRequest().permitAll()
////                .and()
////                .formLogin().loginPage("/login").permitAll()
////                ;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic();
        http
                .csrf().disable()
//                .authorizeRequests().anyRequest().authenticated()
                .authorizeHttpRequests(urlConfig -> urlConfig
//                                .antMatchers("/login", "/registration", "/v3/api-docs/**", "/swagger-ui/**", "/products/**", "/").permitAll()
                                .antMatchers("/admin/**", "/cart/**", "/users", "/user").hasAuthority("ADMIN")
//                                .antMatchers("/cart/**").hasAuthority("ADMIN")
//                        .mvcMatchers("/cart").hasRole("USER")
//                                .anyRequest().authenticated()

                )
//                .anyRequest().permitAll()
//                .mvcMatchers("/admin").hasRole("ADMIN")

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"))
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/products"));

//                .anyRequest().permitAll()
//                .and()
//                .formLogin().loginPage("/login").permitAll()
//                ;
    }


//    103. Basic-Authentication
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic();
//        http
//                .csrf().disable()
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
//                .httpBasic(Customizer.withDefaults());
//
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}