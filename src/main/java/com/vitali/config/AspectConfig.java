package com.vitali.config;

import com.vitali.aop.ShoppingCartAspect;
import com.vitali.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class AspectConfig {
    private final ShoppingCartAspect shoppingCartAspect;

}
