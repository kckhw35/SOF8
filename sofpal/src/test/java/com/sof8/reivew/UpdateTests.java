package com.sof8.reivew;

import java.time.LocalDateTime;

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
		Review review = null;
		
		try {
			review = service.get(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		review.setRe_pwd("password-changed");
		review.setTitle("Change Review Title in Java");
		review.setContent("Change Review Content in Java");
		review.setR_img("img-location");
		review.setGrade(1);
		review.setUp(3);
		review.setMdate(LocalDateTime.now());
		
		
		try {
			service.modify(review);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}
