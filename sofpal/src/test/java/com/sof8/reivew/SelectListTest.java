package com.sof8.reivew;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Paging;
import com.sof8.dto.Qna;
import com.sof8.dto.Review;
import com.sof8.service.ReviewService;

@SpringBootTest
class SelectListTest {
	 
	@Autowired
	ReviewService service;

	@Test
	void contextLoads() {
		List<Review> reviews = new ArrayList<Review>();
		try {
			int totalRow = service.getTotal(null, null);
			Paging paging = new Paging(10, 5, totalRow, 1, null, null);

			reviews = service.getList(paging);
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
