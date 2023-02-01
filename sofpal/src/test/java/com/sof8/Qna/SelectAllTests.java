package com.sof8.Qna;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Qna;
import com.sof8.service.QnaService;

@SpringBootTest
class SelectAllTests {
	 
	@Autowired
	QnaService service;

	@Test
	void contextLoads() {
		List<Qna> qnas = new ArrayList<Qna>();
		try {
			qnas = service.get();
			for (Qna qna : qnas) {
				System.out.println(qna);
			}
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}
