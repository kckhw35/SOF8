package com.sof8.reivew;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Review;
import com.sof8.service.ReviewService;

@SpringBootTest
class UpdateTests {
	 
	@Autowired
	ReviewService service;

	@Test
	void contextLoads() {
		Review review = new Review();
		
		review.setR_id(4);
		review.setRe_pwd("password-changed");
		review.setTitle("Change Review Title in Java");
		review.setContent("Change Review Content in Java");
		review.setR_img("img-location");
		review.setUp(3);
		try {
			service.modify(review);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}
