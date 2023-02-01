package com.sof8.Qna;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.service.QnaService;

@SpringBootTest
class GetTotalTest {
	 
	@Autowired
	QnaService service;

	@Test
	void contextLoads() {
		int count = 0;
		try {
			count = service.getTotal("테스트", "title");
			System.out.println(count);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}
