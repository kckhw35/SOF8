package com.sof8.qna;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.service.QnaService;

@SpringBootTest
class DeleteTests {
	 
	@Autowired
	QnaService service;

	@Test
	void contextLoads() {
		
		try {
			service.remove(2);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}
