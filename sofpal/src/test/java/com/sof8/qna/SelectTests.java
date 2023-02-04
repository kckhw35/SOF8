package com.sof8.qna;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Qna;
import com.sof8.service.QnaService;

@SpringBootTest
class SelectTests {
	 
	@Autowired
	QnaService service;

	@Test
	void contextLoads() {
		Qna qna = null;
		
		try {
			qna = service.get(38);
			System.out.println(qna);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}
