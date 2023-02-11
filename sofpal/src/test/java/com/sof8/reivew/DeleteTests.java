package com.sof8.reivew;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.service.ReviewService;

@SpringBootTest
class DeleteTests {
	 
	@Autowired
	ReviewService service;

	@Test
	void contextLoads() {
		
		try {
			service.remove(4);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}
