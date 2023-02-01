package com.sof8.Qna;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Qna;
import com.sof8.service.QnaService;

@SpringBootTest
class InsertTests {
	@Autowired
	QnaService service;

	@Test
	void contextLoads() {
		Qna qna = new Qna();
		qna.setUser_id("whdgnsdl35");
		qna.setRe_pwd("6543");
		qna.setTitle("1:1 문의내역 java에서 insert Test");
		qna.setContent("문의 내용 입니다.");
		
		try {
			service.register(qna);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}
