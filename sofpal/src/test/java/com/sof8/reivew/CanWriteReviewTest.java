package com.sof8.reivew;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.service.ReviewService;

@SpringBootTest
class CanWriteReviewTest {
	 
	@Autowired
	ReviewService service;

	@Test
	void contextLoads() {
		
		try {
			System.out.println(service.canWriteReview(6));
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}
