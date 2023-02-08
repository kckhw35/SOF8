package com.sof8.reivew;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Review;
import com.sof8.service.ReviewService;

@SpringBootTest
class InsertTests {
	@Autowired
	ReviewService service;

	@Test
	void contextLoads() {
		LocalDateTime now = LocalDateTime.now();
		
		Review review = new Review();
		
		review.setUser_id("whdgnsdl35");
		review.setRe_pwd("6543");
		review.setTitle("Review title Test in java [insert Test]");
		review.setContent("Review content Test in java [insert Test]");
		review.setGrade(4);
		review.setDe_id(3);
		review.setP_id(1);
		review.setRdate(now);
		
		try {
			service.register(review);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}
