package com.sof8.reivew;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Review;
import com.sof8.service.ReviewService;

@SpringBootTest
class SelectAllTests {
	 
	@Autowired
	ReviewService service;

	@Test
	void contextLoads() {
		List<Review> reviews = new ArrayList<Review>();
		try {
			reviews = service.get();
			for (Review review : reviews) {
				System.out.println(review);
			}
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}
