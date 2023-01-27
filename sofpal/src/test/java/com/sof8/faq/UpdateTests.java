package com.sof8.faq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Faq;
import com.sof8.service.FaqService;

@SpringBootTest
class UpdateTests {
	 
	@Autowired
	FaqService service;

	@Test
	void contextLoads() {
		Faq faq = new Faq("jonghoon", "변경된 제목 faq Test","변경된 내용 faq Test");
		faq.setB_id(112);
		try {
			service.modify(faq);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}
