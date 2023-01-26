package com.sof8.faq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Faq;
import com.sof8.service.FaqService;

@SpringBootTest
class InsertTests {
	@Autowired
	FaqService service;

	@Test
	void contextLoads() {
		Faq faq = new Faq("jonghoon", "자주하는 질문 TEST"," TEST 자주하는 질문 내용");
		
		try {
			service.register(faq);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}
