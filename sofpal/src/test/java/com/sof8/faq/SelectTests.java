package com.sof8.faq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Faq;
import com.sof8.service.FaqService;

@SpringBootTest
class SelectTests {
	 
	@Autowired
	FaqService service;

	@Test
	void contextLoads() {
		Faq faq = null;
		
		try {
			faq = service.get(112);
			System.out.println(faq);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}
