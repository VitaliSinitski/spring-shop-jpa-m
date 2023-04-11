package com.vitali;

import com.vitali.entity.Cart;
import com.vitali.entity.OrderItem;
import com.vitali.repository.OrderRepository;
import com.vitali.service.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SpringShopJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringShopJpaApplication.class, args);

//		final OrderService orderService = new OrderService();
//		List<Integer> ids = Arrays.asList(1, 2, 3);
//		String information = "Test information";
//		Long cartId = 1L;
//
//		orderService.createNewOrder(ids, information, cartId);

	}

}
