package com.sof8.reivew;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.service.ReviewService;

@SpringBootTest
class GetTotalTest {
	 
	@Autowired
	ReviewService service;

	@Test
	void contextLoads() {
		int count = 0;
		try {
			count = service.getTotal(null, null);
			System.out.println(count);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}
