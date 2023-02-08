package com.sof8.reivew;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Review;
import com.sof8.service.ReviewService;

@SpringBootTest
class SelectTests {
	 
	@Autowired
	ReviewService service;

	@Test
	void contextLoads() {
		Review review = null;
		
		try {
			review = service.get(4);
			System.out.println(review);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}
