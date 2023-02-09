package com.sof8.qna;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Qna;
import com.sof8.service.QnaService;

@SpringBootTest
class UpdateTests {
	 
	@Autowired
	QnaService service;

	@Test
	void contextLoads() {
		try {
			Qna qna = service.get(2);
			
			qna.setRe_pwd("password-changed");
			qna.setTitle("Qna enable Change");
			qna.setContent("Qna enable change");
			qna.setMdate(LocalDateTime.now());
			qna.setEnable(true);
			
			service.modify(qna);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}
