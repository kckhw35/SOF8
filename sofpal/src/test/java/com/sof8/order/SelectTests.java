package com.sof8.order;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Cart;
import com.sof8.service.CartService;

@SpringBootTest
class SelectTests {
	 
	@Autowired
	CartService service;

	@Test
	void contextLoads() {
		List<Integer> clist = new ArrayList<>();
		clist.add(5);
		clist.add(7);
		
		Cart cart = null;
		
		for(int i : clist) {
			try {
				cart = service.selectedproduct(i);
				System.out.println(cart);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
